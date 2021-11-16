package com.rongji.rjsoft.search.mina.impl;

import com.rongji.rjsoft.search.mina.IDemoClient;
import com.yskj.minaclient.BaseClientImpl;
import com.yskj.minaclient.IClientListener;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2015/10/9.
 */
@Service
public class DemoClientImpl extends BaseClientImpl implements IDemoClient {

    private DemoClientImpl() {
        //要链接的服务名称
        service_name = "search_sever";
        //自身客户端名称
        client_name = "search_sever_client";
    }

    /**
     * 监听器引用
     */
    IClientListener listener = null;

    @Override
    public void setListenerState(boolean flag0) {
        super.setListenerState(flag0);
    }

    @Override
    public void addListener(IClientListener tl) {//注册
        super.addListener(tl);
    }

    @Override
    public void removeListener() {
        super.removeListener();
    }

    @Override
    public void linktoMinaS() {
        super.linktoMinaS();
    }

    /**
     * 发送数据到业务服务器
     * @param servicename
     * @param methodname
     * @param parameter
     * @param timeout
     * @param s
     * @return
     */
    @Override
    public Object sendMessage(String servicename, String methodname, Object[] parameter, Long timeout, IoSession s) {
        return super.sendMessage(servicename, methodname, parameter, timeout, s);
    }

    /**
     * 返回当前链接状态
     * @return
     */
    @Override
    public String getLinkState() {
        return super.getLinkState();
    }

    @Override
    public Object sendMessagetoServer(String servicename, String methodname, Object[] parameter, Long timeout, String serverid) {
        IoSession s = super.getserversessionbyserverid(serverid);
        if (s == null) {
            return null;
        } else {
            return super.sendMessage(servicename, methodname, parameter, timeout, s);
        }
    }
}

