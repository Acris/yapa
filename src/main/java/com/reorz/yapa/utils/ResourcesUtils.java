package com.reorz.yapa.utils;

import java.io.InputStream;

public class ResourcesUtils {
    /**
     * 将文件读取为InputStream
     *
     * @param configPath 文件路径
     * @return 输入流
     */
    public static InputStream getResourceAsStream(String configPath) {
        return ResourcesUtils.class.getClassLoader().getResourceAsStream(configPath);
    }
}
