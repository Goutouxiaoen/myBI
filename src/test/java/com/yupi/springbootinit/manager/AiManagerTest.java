package com.yupi.springbootinit.manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AiManagerTest {


    @Resource
    private AiManager manager;

    @Test
    void doChat() {
        String answer = manager.doChat("介绍一下你自己");
        System.out.println(answer);
    }
}