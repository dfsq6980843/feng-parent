package com.fengx.controller;

import com.fengx.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @author fengXiong
 * @date 2019/12/5 10:41 上午
 */
@Slf4j
@Api(value = "测试")
@RestController
@RequestMapping(value = "/api/v1/demo")
public class TestController {

    @Autowired
    private TestService testService;

    @ApiOperation(value = "test1", notes = "test2")
    @GetMapping(value = "/test1")
    public Mono<String> test1() {
        testService.test1();
        return Mono.just("test1调用成功");
    }

    @ApiOperation(value = "计算+", notes = "加法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "a", paramType = "path", value = "数字a", required = true, dataType = "Long", example = "1"),
            @ApiImplicitParam(name = "b", paramType = "path", value = "数字b", required = true, dataType = "Long", example = "1")
    })
    @GetMapping("/{a}/{b}")
    public Mono<Integer> get(@PathVariable(value = "a") Integer a, @PathVariable(value = "b") Integer b) {
        return Mono.just(a + b);
    }

    @ApiOperation(value = "时间", notes = "时间")
    @GetMapping("/date/{date}")
    public Mono<Date> date(@PathVariable(value = "date") Date date) {
        log.info("时间: {}", date.toString());
        return Mono.just(date);
    }

    @ApiOperation(value = "时间", notes = "时间")
    @GetMapping("/date1/{date}")
    public Date date1(@PathVariable(value = "date") Date date) {
        log.info("时间: {}", date.toString());
        return date;
    }

    @PostMapping("/test2")
    public String test2(@RequestBody String param) {
        log.info("参数: {}", param);
        return param;
    }
}
