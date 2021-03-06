package com.bibabo.bibabowdmservice.services.impl;

import com.bibabo.bibabowdmservice.domain.SkuMain;
import com.bibabo.bibabowdmservice.model.enums.ElasticsearchIndexEnum;
import com.bibabo.bibabowdmservice.services.ISkuMainService;
import com.bibabo.utils.model.RpcResponseDTO;
import com.bibabo.wdm.dto.SkuQueryRequest;
import com.bibabo.wdm.dto.SkuQueryResponse;
import com.bibabo.wdm.services.ISkuDubboService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author fukuixiang
 * @date 2022/6/15
 * @time 11:21
 * @description
 */
@Slf4j
@DubboService
public class SkuDubboServiceImpl implements ISkuDubboService {

    @Autowired
    ISkuMainService skuMainService;

    @Autowired
    RestHighLevelClient restHighLevelClient;


    /*@SneakyThrows
    @PostConstruct
    private void postData2Es() {
        List<SkuMain> skuMainList = skuMainService.findAll();
        BulkRequest bulkRequest = new BulkRequest();
        ObjectMapper mapper = new ObjectMapper();
        skuMainList.forEach(skuMain -> {
            String skuJson = "";
            try {
                skuJson = mapper.writeValueAsString(skuMain);
            } catch (JsonProcessingException e) {
                log.error("skuMain.id:{}, convert to Json Exception:{}", skuMain.getSkuId(), e.getMessage(), e);
            }
            bulkRequest.add(new IndexRequest(ElasticsearchIndexEnum.SKU.getIndex()).id(skuMain.getSkuId().toString()).source(skuJson, XContentType.JSON));
        });
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        log.info("???????????????Took:{}???Items:{}", bulkResponse.getTook(), bulkResponse.getItems());
    }*/


    @SneakyThrows
    @Override
    public RpcResponseDTO<SkuQueryResponse> findSkuByConditions(SkuQueryRequest skuQueryRequest) {
        // skuId?????????
        if (skuQueryRequest.getSkuId() != null) {
            Optional<SkuMain> optionalSkuMain = skuMainService.findById(skuQueryRequest.getSkuId());
            SkuMain skuMain = optionalSkuMain.orElse(null);
            if (skuMain == null) {
                return RpcResponseDTO.<SkuQueryResponse>builder().fail(skuQueryRequest.getSkuId() + "?????????").build();
            }
            SkuQueryResponse response = new SkuQueryResponse();
            BeanUtils.copyProperties(skuMain, response);
            return RpcResponseDTO.<SkuQueryResponse>builder().success(response).build();
        }

        if (skuQueryRequest.getSkuName() == null) {
            return RpcResponseDTO.<SkuQueryResponse>builder().fail("????????????").build();
        }

        // skuName???ES?????????
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.fuzzyQuery("skuName", skuQueryRequest.getSkuName()).fuzziness(Fuzziness.ONE));
        if (skuQueryRequest.getFrom() != null && skuQueryRequest.getSize() != null) {
            searchSourceBuilder.from(skuQueryRequest.getFrom()).size(skuQueryRequest.getSize());
        }
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        log.info("query conditions :{}, ??????:{}, ????????????:{}", skuQueryRequest.toString(), response.getTook(), hits.getTotalHits());

        List<SkuQueryResponse> skuQueryResponseList = new ArrayList<>();
        for (SearchHit hit : hits) {
            log.info("id:{}, source:{}", hit.getId(), hit.getSourceAsString());
            ObjectMapper mapper = new ObjectMapper();
            SkuQueryResponse skuQueryResponse = mapper.readValue(hit.getSourceAsString(), SkuQueryResponse.class);
            skuQueryResponseList.add(skuQueryResponse);
        }

        return RpcResponseDTO.<SkuQueryResponse>builder().success(skuQueryResponseList).build();
    }
}
