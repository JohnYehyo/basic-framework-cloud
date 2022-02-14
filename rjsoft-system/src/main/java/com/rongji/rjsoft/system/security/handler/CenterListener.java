package com.rongji.rjsoft.system.security.handler;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rongji.core.constants.Constants;
import com.rongji.rjsoft.common.security.entity.LoginUser;
import com.rongji.rjsoft.common.util.RedisCache;
import com.yskj.minaclient.IClientListener;
import com.yskj.service.org.IOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * @description:
 * @author: JohnYehyo
 * @create: 2021-12-13 15:24:53
 */
@Component
public class CenterListener {

    private IClientListener iClientListener = null;

    @Autowired
    private IOrgService orgService;

    @Autowired
    private RedisCache redisCache;

    @PostConstruct
    private void PostConstruct() {
        iClientListener = obj -> {
            String str0 = (String) obj;
            JSONObject message = JSON.parseObject(str0);
            ThreadUtil.execute(() -> offline(message));
        };
        orgService.addListener(iClientListener);
    }

    private void offline(JSONObject message) {
        if (message.containsKey("type") && message.getString("type").equals("login")) {
            JSONObject parameter = message.getJSONObject("parameter");
            if (parameter.containsKey("method") && parameter.getString("method").equals("logout")) {
                String loginid = parameter.getString("loginid");
                deleteToken(loginid);
            }
        }
    }

    private void deleteToken(String loginid) {
        Collection<String> keys = redisCache.keys(Constants.LOGIN_TOKEN_KEY + "*");
        LoginUser loginUser;
        for (String key : keys) {
            loginUser = redisCache.getCacheObject(key);
            if (null == loginUser || null == loginUser.getUser()) {
                continue;
            }
            if (loginid.equals(loginUser.getLoginId())) {
                redisCache.deleteObject(Constants.LOGIN_TOKEN_KEY + loginUser.getToken());
            }
        }
    }
}
