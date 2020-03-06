package com.fengx.Mapper;

import com.fengx.model.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fengXiong
 * @date 2019/12/30 11:09 上午
 */
@Component
public interface ItemRepository extends ElasticsearchRepository<Item, Long> {

    List<Item> findByPriceBetween(Double start, Double end);
}
