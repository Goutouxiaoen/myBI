package com.yupi.springbootinit.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfig {

    private Integer database;

    private String host;

    private Integer port;

    private String password;

    //Spring启动时，会创建一个Redisson对象
    @Bean
    public RedissonClient getRedissonClient() {
        //1.创建配置对象
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port)
                .setPassword(password)
                .setDatabase(database);

        //创建Redisson实例
        RedissonClient redissonClient = Redisson.create(config);
        return  redissonClient;
    }
}
