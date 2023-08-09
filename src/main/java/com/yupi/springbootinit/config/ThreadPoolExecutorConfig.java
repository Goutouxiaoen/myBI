package com.yupi.springbootinit.config;


import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolExecutorConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        //创建一个线程工厂,从该工厂获取线程
        ThreadFactory threadFactory = new ThreadFactory() {
            //初始化线程数为1
            private int count = 1;

            //当线程池需要创建线程时，就会调用newThread方法
            @Override
            public Thread newThread(@NotNull Runnable r) {
                //创建一个新线程
                Thread thread = new Thread(r);
                //给线程设置一个名称
                thread.setName("线程" + count);
                //线程数递增
                count++;
                return thread;
            }
        };

        //创建一个线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                4,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4),
                threadFactory
        );
        return threadPoolExecutor;
    }
}
