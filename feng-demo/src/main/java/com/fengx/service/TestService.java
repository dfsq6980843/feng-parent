package com.fengx.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author fengXiong
 * @date 2019/12/9 11:40 上午
 */

@Slf4j
@Service
public class TestService {

    public void test1() {
        String string1 = "abc";
        String intern = string1.intern();
        System.out.println(intern);
    }

    public static void main(String[] args) {

    }
}
