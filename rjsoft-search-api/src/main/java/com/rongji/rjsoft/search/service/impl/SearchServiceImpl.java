package com.rongji.rjsoft.search.service.impl;

import com.rongji.rjsoft.search.mina.IDemoClient;
import com.rongji.rjsoft.search.mina.IDemoServiceListener;
import com.rongji.rjsoft.search.service.ISearchService;
import com.yskj.minaclient.IClientListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Vector;

/**
 * @description: 搜索服务
 * @author: JohnYehyo
 * @create: 2021-11-16 16:14:08
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private IDemoClient minaCService;


    @PostConstruct
    private void PostConstruct() {
        minaCService.addListener(new IClientListener() {
            @Override
            public void ReceiveMessage(Object obj) {
                for (int i = 0; i < listeners.size(); i++) {
                    listeners.elementAt(i).ReceiveMessage(obj);
                }
            }
        });
    }

    @PreDestroy
    private void PreDestroy() {
        minaCService.removeListener();
    }

    /**
     * 监听器引用
     */
    Vector<IDemoServiceListener> listeners = new Vector<IDemoServiceListener>();

    @Override
    public void addListener(IDemoServiceListener tl) {
        listeners.add(tl);
        minaCService.setListenerState(true);
    }

    @Override
    public void removeListener(IDemoServiceListener tl) {
        for (int i = 0; i < listeners.size(); i++) {
            if (tl.equals(listeners.elementAt(i))) {
                listeners.remove(i);
                i = i - 1;
            }
        }
        if (listeners.size() == 0) {
            minaCService.setListenerState(false);
        } else {
            minaCService.setListenerState(true);
        }
    }

    @Override
    public String getLinkState() {
        return minaCService.getLinkState();
    }

    @Override
    @Scheduled(cron = "0/10 * * * * ?")
    public void linktoService(){
        minaCService.linktoMinaS();
    }


    @Override
    public Object addIndex(){
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        return minaCService.sendMessage(className, methodName, new Object[]{}, null, null);
    }
}
