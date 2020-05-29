package com.reorz.yapa.utils;

import java.io.InputStream;

public class ResourcesUtils {
    public static InputStream getResourceAsStream(String configPath) {
        return ResourcesUtils.class.getClassLoader().getResourceAsStream(configPath);
    }
}
