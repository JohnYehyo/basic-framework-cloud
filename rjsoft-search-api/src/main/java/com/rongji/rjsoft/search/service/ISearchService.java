package com.rongji.rjsoft.search.service;

import com.rongji.rjsoft.search.mina.IDemoServiceListener;

/**
 * @description: 搜索服务
 * @author: JohnYehyo
 * @create: 2021-11-16 16:13:24
 */
public interface ISearchService {

    /**
     * 获取当前的流程应用和流程服务的链接状态信息.
     *
     * @param
     * @return Html格式的服务链接状态信息.
     */
    public String getLinkState();

    /**
     * 链接到服务
     *
     * @return
     */
    public void linktoService();

    /**
     * 添加对服务端下发消息的监听.
     *
     * @param tl
     */
    public void addListener(IDemoServiceListener tl);

    /**
     * 移除对服务端下发消息的监听
     *
     * @param tl
     */
    public void removeListener(IDemoServiceListener tl);

    Object addIndex();
}
