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

    public static String writeAsString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Write object as string failed. Cause: " + e.getMessage(), e);
            throw new YamlParseErrorException(e.getMessage());
        }
    }

    public static <T> T readFromString(String yamlStr, Class<T> clazz) {
        try {
            return objectMapper.readValue(yamlStr, clazz);
        } catch (JsonProcessingException e) {
            logger.error("Read object from string failed. Cause: " + e.getMessage(), e);
            throw new YamlParseErrorException(e.getMessage());
        }
    }

    public static <T> T readFromStream(InputStream inputStream, Class<T> clazz) {
        try {
            return objectMapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            logger.error("Read object from stream failed. Cause: " + e.getMessage(), e);
            throw new YamlParseErrorException(e.getMessage());
        }
    }
}
