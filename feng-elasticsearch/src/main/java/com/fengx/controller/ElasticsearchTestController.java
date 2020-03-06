package com.fengx.controller;

import com.fengx.model.Item;
import com.fengx.service.ElasticsearchTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fengXiong
 * @date 2019/12/30 11:19 上午
 */
@RestController
@RequestMapping(value = "/api/elasticsearch")
public class ElasticsearchTestController {

    @Autowired
    private ElasticsearchTestService elasticsearchTestService;

    /**
     * 创建索引
     */
    @PostMapping(value = "/create")
    public void createIndex() {
        elasticsearchTestService.createIndex();
    }

    /**
     * 删除索引
     */
    @DeleteMapping(value = "/delete")
    public void deleteIndex() {
        elasticsearchTestService.deleteIndex();
    }

    /**
     * 添加数据
     */
    @PostMapping(value = "/add")
    private void add() {
        elasticsearchTestService.add();
    }

    /**
     * 批量添加
     */
    @PostMapping(value = "/addBatch")
    private void addBatch() {
        elasticsearchTestService.addBatch();
    }

    /**
     * 修改
     */
    @PutMapping(value = "/edit")
    private void edit() {
        elasticsearchTestService.edit();
    }

    @GetMapping(value = "/all")
    private List<Item> queryAll() {
        return elasticsearchTestService.queryAll();
    }

    @GetMapping(value = "")
    private List<Item> queryByPrice(@RequestParam(value = "start") Double start,
                                    @RequestParam(value = "end") Double end) {
        return elasticsearchTestService.queryByPrice(start, end);
    }

    @GetMapping(value = "/match")
    private Page<Item> queryMatch(@RequestParam(value = "word") String word) {
        return elasticsearchTestService.queryMatch(word);
    }

    @GetMapping(value = "/agg")
    private void queryAgg() {
        elasticsearchTestService.queryAgg();
    }
}
