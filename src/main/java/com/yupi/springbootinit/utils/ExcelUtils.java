package com.yupi.springbootinit.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrBuilder;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Excel相关工具类
 */
@Slf4j
public class ExcelUtils {

    /**
     * @param multipartFile
     * @return
     * @throws Exception
     * @Describation
     */
    public static String excelToCsv(MultipartFile multipartFile) {
        //将文件加载为流对象
//        File file  = null;

        List<Map<Integer, String>> list = null;
        try {
//            file = ResourceUtils.getFile("classpath:test_excel.xlsx");
             //使用EasyExcel读取流数据
        list = EasyExcel.read(multipartFile.getInputStream())
                .excelType(ExcelTypeEnum.XLSX)
                .sheet()
                .headRowNumber(0)
                .doReadSync();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //如果数据为空
        if (CollUtil.isEmpty(list)) {
            return "";
        }
         StrBuilder strBuilder = new StrBuilder();
        //转换为csv
        //读取表头内容(第一行)
        LinkedHashMap<Integer, String> headerMap = (LinkedHashMap<Integer, String>) list.get(0);
        //提取所有非空值
        List<String> headList =
                headerMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
        strBuilder.append(StringUtils.join(headList,",")).append("\n");

        //读取完表头，从第一行数据开始读取
        for (int i = 1; i < list.size(); i++) {
            LinkedHashMap<Integer, String> dataMap = (LinkedHashMap<Integer, String>) list.get(i);
            List<String> dataList =
                    dataMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
            strBuilder.append(StringUtils.join(dataList, ",")).append("\n");
        }

        return strBuilder.toString();
    }
}
