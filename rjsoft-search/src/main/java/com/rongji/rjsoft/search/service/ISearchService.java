package com.rongji.rjsoft.search.service;

import com.rongji.core.vo.ResponseVo;
import com.rongji.rjsoft.search.core.query.SearchMultiPageQuery;
import com.rongji.rjsoft.search.core.query.SearchPageQuery;
import com.rongji.rjsoft.search.core.query.SearchQuery;
import com.rongji.rjsoft.search.core.search.DocAo;
import com.rongji.rjsoft.search.core.search.DocDeleteAo;
import org.elasticsearch.action.get.GetResponse;

import java.io.IOException;

/**
 * @description: 搜索服务
 * @author: JohnYehyo
 * @create: 2021-11-16 16:13:24
 */
public interface ISearchService {

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
     * 通过id获取文档
     *
     * @param indexName
     * @param type
     * @param id
     * @return
     * @throws IOException
     */
    GetResponse findDocById(String indexName, String type, String id) throws IOException;

    /**
     * 综合查询
     *
     * @param searchPageQuery 查询条件
     * @param <T>             泛型
     * @return 分页结果
     * @throws IOException
     */
    <T> ResponseVo<T> queryForlist(SearchPageQuery searchPageQuery);


    /**
     * 单实例查询
     *
     * @param searchQuery 查询条件
     * @param <T>         泛型
     * @return 实例结果
     * @throws IOException
     */
    <T> ResponseVo<T> queryForEntity(SearchQuery searchQuery);

    /**
     * 多字段包含关键字搜索
     * @param searchMultiPageQuery 条件对象
     * @return 分页结果
     */
    <T> ResponseVo<T> multiSelect(SearchMultiPageQuery searchMultiPageQuery);
}
