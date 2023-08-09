package com.yupi.springbootinit.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.constant.CommonConstant;
import com.yupi.springbootinit.constant.UserConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.manager.AiManager;
import com.yupi.springbootinit.manager.RedisLimiterManager;
import com.yupi.springbootinit.model.dto.chart.*;
import com.yupi.springbootinit.model.entity.Chart;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.model.vo.BiResponse;
import com.yupi.springbootinit.service.ChartService;
import com.yupi.springbootinit.service.UserService;
import com.yupi.springbootinit.utils.ExcelUtils;
import com.yupi.springbootinit.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.BaseStream;

/**
 * 测试任务队列
 */
@RestController
@RequestMapping("/queue")
@Slf4j
@Profile({"dev","local"})
public class QueueController {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;


    @GetMapping("/add")
    //接受一个参数name,然后将任务添加到线程池中
    public void add(String name) {
        //运行一个异步任务
        CompletableFuture.runAsync(() ->{
            //打印一条日志信息，包括任务名称和线程名称
            log.info("任务执行中：" + name + ", 执行线程：" + Thread.currentThread().getName());
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //异步任务运行在threadPoolExecutor中
        },threadPoolExecutor);

    }


    /**
     * 返回线程池的状态信息
     * @return
     */
    @GetMapping("/get")
    public String get() {
        //创建一个HashMap存储线程池的状态信息
        Map<Object, Object> map = new HashMap<>();
        //获取线程池的队列长度
        int size = threadPoolExecutor.getQueue().size();
        //将队列长度放入map中
        map.put("队列长度" ,size);
        //获取线程池已接收任务总数
        long taskCount = threadPoolExecutor.getTaskCount();
        map.put("接收任务总数" , taskCount);
        //获取线程池已完成任务总数
        long completedTaskCount = threadPoolExecutor.getCompletedTaskCount();
        map.put("已完成任务总数",completedTaskCount);
        //获取线程池正在执行任务总数
        int activeCount = threadPoolExecutor.getActiveCount();
        map.put("正在执行线程数",activeCount);
        return JSONUtil.toJsonStr(map);

    }




}
