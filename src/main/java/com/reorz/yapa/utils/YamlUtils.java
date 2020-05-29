package com.reorz.yapa.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.reorz.yapa.exceptions.YamlParseErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class YamlUtils {
    private static final Logger logger = LoggerFactory.getLogger(YamlUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    static {
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    }

    /**
     * 将对象序列化为YAML字符串
     *
     * @param object 待序列化的对象
     * @return 序列化后的YAML字符串
     */
    public static String writeAsString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Write object as string failed. Cause: " + e.getMessage(), e);
            throw new YamlParseErrorException(e.getMessage());
        }
    }

    /**
     * 将YAML字符串反序列化为对象
     *
     * @param yamlStr 要反序列化的YAML字符串
     * @param clazz   要反序列化的对象类型
     * @param <T>     返回对象类型泛型
     * @return 反序列化后的对象
     */
    public static <T> T readFromString(String yamlStr, Class<T> clazz) {
        try {
            return objectMapper.readValue(yamlStr, clazz);
        } catch (JsonProcessingException e) {
            logger.error("Read object from string failed. Cause: " + e.getMessage(), e);
            throw new YamlParseErrorException(e.getMessage());
        }
    }


    /**
     * 将YAML文件输入流反序列化为对象
     *
     * @param inputStream 要反序列化的YAML文件输入流
     * @param clazz       要反序列化的对象类型
     * @param <T>         返回对象类型泛型
     * @return 反序列化后的对象
     */
    public static <T> T readFromStream(InputStream inputStream, Class<T> clazz) {
        try {
            return objectMapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            logger.error("Read object from stream failed. Cause: " + e.getMessage(), e);
            throw new YamlParseErrorException(e.getMessage());
        }
    }
}
