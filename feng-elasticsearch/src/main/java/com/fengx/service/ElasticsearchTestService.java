package com.fengx.service;

import com.fengx.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fengXiong
 * @date 2019/12/30 11:21 上午
 */
@Service
public interface ElasticsearchTestService {

    /**
     * 创建索引
     */
    void createIndex();

    /**
     * 删除索引
     */
    void deleteIndex();

    List<Item> queryAll();

    /**
     * 添加数据
     */
    void add();

    void addBatch();

    void edit();

    List<Item> queryByPrice(Double start, Double end);

    Page<Item> queryMatch(String word);

    void queryAgg();
}
