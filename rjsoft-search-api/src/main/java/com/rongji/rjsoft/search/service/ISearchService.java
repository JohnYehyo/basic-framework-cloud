package com.rongji.rjsoft.search.service;

import com.rongji.rjsoft.search.core.query.SearchPageQuery;
import com.rongji.rjsoft.search.core.query.SearchQuery;
import com.rongji.rjsoft.search.core.search.DocAo;
import com.rongji.rjsoft.search.core.search.DocDeleteAo;
import com.rongji.rjsoft.search.mina.IDemoServiceListener;

import java.io.IOException;

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

    /**
     * 创建索引
     *
     * @param indexName
     * @param settings
     * @throws IOException
     */
    void createIndex(String indexName, String settings) throws IOException;

    /**
     * 删除索引
     *
     * @param indexName
     * @throws IOException
     */
    void deleteIndex(String indexName) throws IOException;

    /**
     * 增加/更新文档
     * @param docAo 文档参数
     */
    void addDoc(DocAo docAo) throws IOException;

    /**
     * 根据id删除文档
     * @param docDeleteAo 文档参数
     */
    void deleteDoc(DocDeleteAo docDeleteAo) throws IOException;

    /**
     * 综合查询
     *
     * @param searchPageQuery 查询条件
     * @return 分页结果
     * @throws IOException
     */
    Object queryForlist(SearchPageQuery searchPageQuery);


    /**
     * 单实例查询
     *
     * @param searchQuery 查询条件
     * @return 实例结果
     * @throws IOException
     */
    Object queryForEntity(SearchQuery searchQuery);
}
