package com.rongji.rjsoft.search.mina;

import com.yskj.minaclient.IClientListener;
import org.apache.mina.core.session.IoSession;

/**
 * Created by Administrator on 2015/10/9.
 */
public interface IDemoClient {
    public void linktoMinaS();

    public Object sendMessage(String servicename, String methodname, Object[] parameter, Long timeout, IoSession s);

    public void addListener(IClientListener tl);

    public void removeListener();

    public String getLinkState();

    public void setListenerState(boolean flag0);

    public Object sendMessagetoServer(String servicename, String methodname, Object[] parameter, Long timeout, String serverid);
}
