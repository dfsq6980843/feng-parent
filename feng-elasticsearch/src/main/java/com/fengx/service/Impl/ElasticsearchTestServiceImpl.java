package com.fengx.service.Impl;

import com.fengx.Mapper.ItemRepository;
import com.fengx.model.Item;
import com.fengx.service.ElasticsearchTestService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fengXiong
 * @date 2019/12/30 11:21 上午
 */
@Slf4j
@Service
public class ElasticsearchTestServiceImpl implements ElasticsearchTestService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void createIndex() {
        elasticsearchTemplate.createIndex(Item.class);
    }

    @Override
    public void deleteIndex() {
        elasticsearchTemplate.deleteIndex(Item.class);
    }

    @Override
    public List<Item> queryAll() {
        List<Item> items = Lists.newArrayList(itemRepository.findAll(Sort.by("price").ascending()));
        items.forEach(System.out::println);
        return items;
    }

    @Override
    public void add() {
        Item item = new Item(1L, "iphone-xs-max", "手机", "苹果", 6789.00, "http://image.baidu.com/13123.jpg");
        itemRepository.save(item);
    }

    @Override
    public void addBatch() {
        List<Item> list = Lists.newArrayList();
//        list.add(new Item(2L, "iphone-11-pro", " 手机", "苹果", 8789.00, "http://image.baidu.com/13123.jpg"));
//        list.add(new Item(3L, "iphone-11", " 手机", "苹果", 7789.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(1L, "小米手机7", "手机", "小米", 3299.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", "手机", "华为", 4499.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00, "http://image.baidu.com/13123.jpg"));
        itemRepository.saveAll(list);
    }

    @Override
    public void edit() {
        Item item = new Item(1L, "iphone-xs-max", "手机", "苹果", 6789.00, "http://image.baidu.com/13123.jpg");
        itemRepository.save(item);
    }

    @Override
    public List<Item> queryByPrice(Double start, Double end) {
        List<Item> items = itemRepository.findByPriceBetween(start, end);
        items.forEach(System.out::println);
        return items;
    }

    @Override
    public Page<Item> queryMatch(String word) {
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withQuery(QueryBuilders.fuzzyQuery("title", word));
        Page<Item> page = itemRepository.search(searchQueryBuilder.build());
        List<Item> items = Lists.newArrayList(page);
        items.forEach(System.out::println);
        return page;
    }

    @Override
    public void queryAgg() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand"));
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) itemRepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        buckets.forEach(bucket -> {
            System.out.println("key : " + bucket.getKey());
            System.out.println("keyAsString : " + bucket.getKeyAsString());
            System.out.println("docCount : " + bucket.getDocCount());
        });
    }


}
