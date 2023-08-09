package com.yupi.springbootinit.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.PrivateKey;
import java.util.PrimitiveIterator;

/**
 * Bi返回结果
 */

@Data
public class BiResponse {

    /**
     * 图表
     */
    private String genChart;

    /**
     * 分析结论
     */
    private String genResult;

    /**
     * 新生成的图表ID
     */
    private Long chartId;

}
