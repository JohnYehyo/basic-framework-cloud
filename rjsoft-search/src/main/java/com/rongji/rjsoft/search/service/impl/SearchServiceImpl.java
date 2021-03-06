package com.rongji.rjsoft.search.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.rongji.core.enums.ResponseEnum;
import com.rongji.core.exception.BusinessException;
import com.rongji.core.vo.CommonPage;
import com.rongji.core.vo.ResponseVo;
import com.rongji.rjsoft.common.util.LogUtils;
import com.rongji.rjsoft.search.core.query.SearchBaseQuery;
import com.rongji.rjsoft.search.core.query.SearchMultiPageQuery;
import com.rongji.rjsoft.search.core.query.SearchPageQuery;
import com.rongji.rjsoft.search.core.query.SearchQuery;
import com.rongji.rjsoft.search.core.search.DocAo;
import com.rongji.rjsoft.search.core.search.DocDeleteAo;
import com.rongji.rjsoft.search.service.ISearchService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: ????????????
 * @author: JohnYehyo
 * @create: 2021-11-16 16:14:08
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * ????????????
     *
     * @throws IOException
     */
    @Override
    public void createIndex(String indexName, String settings) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        /**
         * {
         * 	"settings":{
         * 		"index":{
         * 			"number_of_shards":1,
         * 			"number_of_replicas":0
         *       }
         *    }
         * }
         */
        request.settings(Settings.builder().put("number_of_shards", 1).put("number_of_replicas", 0));
        request.mapping(settings, XContentType.JSON);
        //??????ES???????????????????????????????????????
        IndicesClient indicesClient = restHighLevelClient.indices();
        CreateIndexResponse response = indicesClient.create(request);
        //?????????CreateIndexResponse?????????????????????????????????????????????
        //??????????????????????????????????????????
        boolean acknowledged = response.isAcknowledged();
        System.out.println(acknowledged);
        //???????????????????????????????????????????????????????????????????????????????????????
        boolean shardsAcknowledged = response.isShardsAcknowledged();
        System.out.println(shardsAcknowledged);
    }

    /**
     * ????????????
     *
     * @param indexName
     * @throws IOException
     */
    @Override
    public void deleteIndex(String indexName) throws IOException {
        //??????????????????????????????
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        //???????????????
        //????????????????????????????????????????????????????????????TimeValue?????????
        request.timeout(TimeValue.timeValueMinutes(2));
        //??????????????????????????????????????????????????????????????????????????????
        // request.timeout("2m");
        //??????master?????????????????????(??????TimeValue??????)
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        //??????master?????????????????????(?????????????????????)
        // request.masterNodeTimeout("1m");

        //??????IndicesOptions????????????????????????????????????????????????????????????????????????
        request.indicesOptions(IndicesOptions.lenientExpandOpen());

        //????????????
        IndicesClient indices = restHighLevelClient.indices();
        AcknowledgedResponse deleteRsponse = indices.delete(request, RequestOptions.DEFAULT);

        //?????????DeleteIndexResponse????????????????????????????????????????????????????????????
        //????????????????????????????????????
        boolean acknowledged = deleteRsponse.isAcknowledged();
        System.out.println(acknowledged);

        //????????????????????????????????????ElasticsearchException???
        try {
            request = new DeleteIndexRequest("does_not_exist");
            restHighLevelClient.indices().delete(request);
        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {
                //????????????????????????????????????????????????????????????
                LogUtils.error("??????????????????????????????,index:" + indexName);
            }
        }
    }

    /**
     * ??????/????????????
     *
     * @param docAo ????????????
     */
    @Override
    public void addDoc(DocAo docAo) throws IOException {
        IndexRequest indexRequest = new IndexRequest(docAo.getIndex());
        if (StringUtils.isNotEmpty(docAo.getType())) {
            indexRequest.type(docAo.getType());
        } else {
            indexRequest.type("_doc");
        }
        if (null != docAo.getId()) {
            indexRequest.id(String.valueOf(docAo.getId()));
        }
        indexRequest.source(docAo.getContent(), XContentType.JSON);
        //??????
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
                LogUtils.error("??????/??????????????????:" + reason);
            }
            throw new BusinessException(ResponseEnum.DOC_INSERT_EDIT_ERROR);
        }
    }

    /**
     * ??????id????????????
     *
     * @param docDeleteAo ????????????
     */
    @Override
    public void deleteDoc(DocDeleteAo docDeleteAo) throws IOException {
        DeleteRequest request = new DeleteRequest(docDeleteAo.getIndex());
        if (StringUtils.isNotEmpty(docDeleteAo.getType())) {
            request.type(docDeleteAo.getType());
        }
        if (null != docDeleteAo.getId()) {
            request.id(String.valueOf(docDeleteAo.getId()));
        }
        DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
                LogUtils.error("??????????????????:" + reason);
            }
            throw new BusinessException(ResponseEnum.DOC_DELETE_ERROR);
        }
    }

    /**
     * ??????id????????????
     *
     * @param indexName
     * @param type
     * @param id
     * @return
     * @throws IOException
     */
    @Override
    public GetResponse findDocById(String indexName, String type, String id) throws IOException {
        GetRequest request = new GetRequest(indexName, type, id);
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        return response;
    }

    /**
     * ????????????
     *
     * @param searchPageQuery ????????????
     * @param <T>             ??????
     * @return ????????????
     */
    @Override
    public <T> ResponseVo<T> queryForlist(SearchPageQuery searchPageQuery) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(searchPageQuery.getIndexName());
        searchRequest.types(searchPageQuery.getIndexType());

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(searchPageQuery.getCurrent() - 1);
        sourceBuilder.size(searchPageQuery.getPageSize());
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));


        RangeQueryBuilder rangeQueryBuilder = rangeQueryByTime(searchPageQuery.getSTime(),
                searchPageQuery.getETime(), searchPageQuery.getTimeKey());
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        if (CollectionUtil.isEmpty(searchPageQuery.getParam())) {
            //???????????????????????????????????????????????????????????????????????????pid????????????
            //orgid???????????????pid???????????????
            if (!StringUtils.isEmpty(searchPageQuery.getBranchCode())) {
                MatchPhrasePrefixQueryBuilder matchPhrasePrefixQueryBuilder =
                        new MatchPhrasePrefixQueryBuilder(searchPageQuery.getBranchKey(), searchPageQuery.getBranchCode());
                boolBuilder.must(matchPhrasePrefixQueryBuilder);
            }

            //?????????????????????????????????
            if (null != rangeQueryBuilder) {
                boolBuilder.must(rangeQueryBuilder);
            }
            sourceBuilder.query(boolBuilder);
        } else {
            //????????????????????????
            //pid???????????????pid??????????????????
            if (!StringUtils.isEmpty(searchPageQuery.getBranchCode())) {
                MatchPhrasePrefixQueryBuilder matchPhrasePrefixQueryBuilder =
                        new MatchPhrasePrefixQueryBuilder(searchPageQuery.getBranchKey(), searchPageQuery.getBranchCode());
                boolBuilder.must(matchPhrasePrefixQueryBuilder);
            }
            //???????????????????????????
            if (null != rangeQueryBuilder) {
                boolBuilder.must(rangeQueryBuilder);

            }

            List<SearchBaseQuery> params = searchPageQuery.getParam();
            addBoolQuery(sourceBuilder, boolBuilder, params, false);

        }
        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
//        sourceBuilder.sort(new FieldSortBuilder("executionTime.keyword").order(SortOrder.DESC));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LogUtils.error("ES????????????:", e);
            return ResponseVo.error("????????????");
        }
        SearchHit[] results = searchResponse.getHits().getHits();
        long totalHits = searchResponse.getHits().getTotalHits();
        if (null == results || results.length == 0) {
            return null;
        }

        CommonPage page = new CommonPage();
        if (null == searchPageQuery.getClazz()) {
            List list = getOriginals(results);
            page.setList(list);
        } else {
            List<T> list = getOriginals(searchPageQuery.getClazz(), results);
            page.setList(list);
        }
        page.setCurrent((long) searchPageQuery.getCurrent());
        page.setPageSize((long) searchPageQuery.getPageSize());
        page.setTotalPage(totalHits % searchPageQuery.getPageSize() == 0 ?
                totalHits / searchPageQuery.getPageSize() : totalHits / searchPageQuery.getPageSize() + 1);
        page.setTotal(totalHits);
        return ResponseVo.response(ResponseEnum.SUCCESS, page);

    }

    private List getOriginals(SearchHit[] results) {
        List list = new ArrayList<>();
        Object obj;
        for (SearchHit hit : results) {
            String sourceAsString = hit.getSourceAsString();
            if (sourceAsString != null) {
                obj = JSON.parseObject(sourceAsString);
                list.add(obj);
            }
        }
        return list;
    }

    private <T> List<T> getOriginals(Class<T> clazz, SearchHit[] results) {
        T t;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        List<T> list = new ArrayList<>();
        for (SearchHit hit : results) {
            String sourceAsString = hit.getSourceAsString();
            if (sourceAsString != null) {
                t = JSON.parseObject(sourceAsString, clazz);
                if (null == t) {
                    continue;
                }
                list.add(t);
            }
        }
        return list;
    }

    private RangeQueryBuilder rangeQueryByTime(Long sTime, Long eTime, String key) {
        if (null != sTime && null != eTime) {
            RangeQueryBuilder rangeQueryTime = QueryBuilders.rangeQuery(key).from(sTime)
                    .to(eTime);
            return rangeQueryTime;
        }
        if (null != sTime) {
            RangeQueryBuilder rangeQueryTime = QueryBuilders.rangeQuery(key).gte(sTime);
            return rangeQueryTime;
        }
        if (null != eTime) {
            RangeQueryBuilder rangeQueryTime = QueryBuilders.rangeQuery(key).lte(eTime);
            return rangeQueryTime;
        }
        return null;
    }


    /**
     * ???????????????
     *
     * @param searchQuery ????????????
     * @param <T>         ??????
     * @return ????????????
     */
    @Override
    public <T> ResponseVo<T> queryForEntity(SearchQuery searchQuery) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(searchQuery.getIndexName());
        searchRequest.types(searchQuery.getIndexType());

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        List<SearchBaseQuery> params = searchQuery.getParam();
        addBoolQuery(sourceBuilder, boolBuilder, params, false);


        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LogUtils.error("ES????????????:", e);
            return ResponseVo.error("????????????");
        }
        SearchHit[] results = searchResponse.getHits().getHits();
        if (null == results || results.length == 0) {
            return null;
        }
        if (null == searchQuery.getClazz()) {
            List list = getOriginals(results);
            if (null == list || list.size() == 0) {
                return null;
            }
            return ResponseVo.response(ResponseEnum.SUCCESS, list.get(0));
        }
        List<T> list = getOriginals(searchQuery.getClazz(), results);
        if (null == list || list.size() == 0) {
            return null;
        }
        return ResponseVo.response(ResponseEnum.SUCCESS, list.get(0));

    }

    private void addBoolQuery(SearchSourceBuilder sourceBuilder, BoolQueryBuilder boolBuilder,
                              List<SearchBaseQuery> params, boolean fuzzy) {
        for (SearchBaseQuery param : params) {
            //???????????????????????????????????????????????????????????????????????????pid????????????
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(param.getKey(),
                    param.getValue());
            if (fuzzy) {
                // ??????????????????
                matchQueryBuilder.fuzziness(Fuzziness.AUTO);
            }
            //??????????????????
            boolBuilder.must(matchQueryBuilder);
        }
        sourceBuilder.query(boolBuilder);
    }

    /**
     * ??????????????????????????????
     *
     * @param searchMultiPageQuery ????????????
     * @return ????????????
     */
    @Override
    public <T> ResponseVo<T> multiSelect(SearchMultiPageQuery searchMultiPageQuery) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(searchMultiPageQuery.getIndexName());
        searchRequest.types(searchMultiPageQuery.getIndexType());

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(searchMultiPageQuery.getCurrent() - 1);
        sourceBuilder.size(searchMultiPageQuery.getPageSize());
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));


        if (null == searchMultiPageQuery.getParam()) {
            throw new BusinessException(ResponseEnum.NONE_SELECT_PARAM);
        }
        MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(
                searchMultiPageQuery.getParam().getValue(), searchMultiPageQuery.getParam().getKey()
        );
        multiMatchQueryBuilder.type(MultiMatchQueryBuilder.Type.BEST_FIELDS);
        multiMatchQueryBuilder.fuzziness(Fuzziness.AUTO);
        sourceBuilder.query(multiMatchQueryBuilder);

        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LogUtils.error("ES????????????:", e);
            return ResponseVo.error("????????????");
        }
        SearchHit[] results = searchResponse.getHits().getHits();
        long totalHits = searchResponse.getHits().getTotalHits();
        if (null == results || results.length == 0) {
            return null;
        }

        CommonPage page = new CommonPage();
        if (null == searchMultiPageQuery.getClazz()) {
            List list = getOriginals(results);
            page.setList(list);
        } else {
            List<T> list = getOriginals(searchMultiPageQuery.getClazz(), results);
            page.setList(list);
        }
        page.setCurrent((long) searchMultiPageQuery.getCurrent());
        page.setPageSize((long) searchMultiPageQuery.getPageSize());
        page.setTotalPage(totalHits % searchMultiPageQuery.getPageSize() == 0 ?
                totalHits / searchMultiPageQuery.getPageSize() : totalHits / searchMultiPageQuery.getPageSize() + 1);
        page.setTotal(totalHits);
        return ResponseVo.response(ResponseEnum.SUCCESS, page);
    }
}
