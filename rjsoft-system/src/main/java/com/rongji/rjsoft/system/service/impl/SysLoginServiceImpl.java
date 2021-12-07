package com.rongji.rjsoft.system.service.impl;

import com.rongji.core.ao.system.LoginAo;
import com.rongji.rjsoft.common.security.entity.LoginUser;
import com.rongji.rjsoft.common.security.util.AESUtils;
import com.rongji.rjsoft.common.security.util.TokenUtils;
import com.rongji.rjsoft.common.util.LogUtils;
import com.rongji.rjsoft.common.util.RedisCache;
import com.rongji.core.constants.Constants;
import com.rongji.core.enums.LogStatusEnum;
import com.rongji.core.enums.ResponseEnum;
import com.rongji.core.exception.BusinessException;
import com.rongji.rjsoft.common.util.ServletUtils;
import com.rongji.rjsoft.system.service.ISysLoginInfoService;
import com.rongji.rjsoft.system.service.ISysLoginService;
import com.yskj.dao.dto.org.LoginedUser;
import com.yskj.service.org.IOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

/**
 * @description: 登录
 * @author: JohnYehyo
 * @create: 2021-04-26 17:28:59
 */
@Service
public class SysLoginServiceImpl implements ISysLoginService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ISysLoginInfoService sysLoginInfoService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private IOrgService orgService;

    @Value("${JohnYehyo.key}")
    private String KEY;

    @Value("${spring.profiles.active}")
    private String VERSION;

    @Value("${yskj.app.id}")
    private String APPID;

    @Value("${yskj.server.id}")
    private String SERVERID;

    @Value("${yskj.domain.id}")
    private String DOMAINID;


    /**
     * 登录
     *
     * @param loginAo 请求参数体
     * @return
     */
    @Override
    public String login(LoginAo loginAo) {
        checkCaptcha(loginAo);

        String username = loginAo.getUserName();
        String password = loginAo.getPassword();
        try {
            byte[] key = KEY.getBytes("utf-8");
            username = new String(AESUtils.decrypt(username, key), "utf-8");
            password = new String(AESUtils.decrypt(password, key), "utf-8");
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.ENCRYPTION_TO_DECRYPT);
        }

        Authentication authentication = null;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            sysLoginInfoService.saveLoginInfo(username, LogStatusEnum.FAIL.getCode(), e.getMessage());
            if (e.getCause() instanceof BusinessException) {
                throw (BusinessException) e.getCause();
            }
            throw new BusinessException(ResponseEnum.LOGIN_ERROR);
        }


        sysLoginInfoService.saveLoginInfo(username, LogStatusEnum.LOGIN_SUCCESS.getCode(),
                LogStatusEnum.LOGIN_SUCCESS.getValue());

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return tokenUtils.createToken(loginUser);
    }

    /**
     * 校验验证码
     * @param loginAo
     */
    private void checkCaptcha(LoginAo loginAo) {
        if (VERSION.equals(Constants.PROD_VERSION)) {
            String verifyKey = Constants.CAPTCHA_CODE_KEY + loginAo.getUuid();
            String captcha = redisCache.getCacheObject(verifyKey);
            redisCache.deleteObject(verifyKey);

            if (null == captcha) {
                sysLoginInfoService.saveLoginInfo(loginAo.getUserName(), LogStatusEnum.FAIL.getCode(), ResponseEnum.CAPTCHA_EXPIRED.getValue());
                throw new BusinessException(ResponseEnum.CAPTCHA_EXPIRED);
            }

            if (!captcha.equals(loginAo.getCaptcha())) {
                LogUtils.warn("用户：{}验证码输入错误", loginAo.getUserName());
                sysLoginInfoService.saveLoginInfo(loginAo.getUserName(), LogStatusEnum.FAIL.getCode(), ResponseEnum.CAPTCHA_ERROR.getValue());
                throw new BusinessException(ResponseEnum.CAPTCHA_ERROR);
            }
        }
    }


    /**
     * 登录
     *
     * @param loginAo 请求参数体
     * @return
     */
    @Override
    public String centerLogin(LoginAo loginAo) {
        checkCaptcha(loginAo);

        String username = loginAo.getUserName();
        String password = loginAo.getPassword();
        try {
            byte[] key = KEY.getBytes("utf-8");
            username = new String(AESUtils.decrypt(username, key), "utf-8");
            password = new String(AESUtils.decrypt(password, key), "utf-8");
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.ENCRYPTION_TO_DECRYPT);
        }

        LoginedUser loginedUser = orgService.login(APPID, DOMAINID, username, password);

        if (0 != loginedUser.getStateCode().intValue()) {
            throw new BusinessException(ResponseEnum.FAIL.getCode(), loginedUser.getStateMessage());
        }

        sysLoginInfoService.saveLoginInfo(username, LogStatusEnum.LOGIN_SUCCESS.getCode(),
                LogStatusEnum.LOGIN_SUCCESS.getValue());

        LoginUser loginUser = refreshAuth(loginedUser);
        return tokenUtils.createToken(loginUser);
    }

    private LoginUser refreshAuth(LoginedUser loginedUser) {
        LoginUser loginUser = (LoginUser) userDetailsService.loadUserByUsername(loginedUser.getShortName());
        loginUser.setLoginId(loginedUser.getLoginid());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(ServletUtils.getRequest()));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return loginUser;
    }
}
