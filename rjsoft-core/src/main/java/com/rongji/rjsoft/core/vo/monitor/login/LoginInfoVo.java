package com.rongji.rjsoft.core.vo.monitor.login;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: 登录信息详情视图
 * @author: JohnYehyo
 * @create: 2021-09-09 15:00:52
 */
@Data
public class LoginInfoVo implements Serializable {

    private static final long serialVersionUID = 2588277567721435699L;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 登录IP地址
     */
    private String ip;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录状态（0成功 1失败）
     */
    private int status;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 访问时间
     */
    private LocalDateTime loginTime;
}
