package com.reorz.yapa.session;

import com.reorz.yapa.config.Configuration;
import com.reorz.yapa.config.ConfigurationBuilder;
import com.reorz.yapa.utils.ResourcesUtils;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    private static final String DEFAULT_CONFIG_FILE_PATH = "yapa.yml";

    public SqlSessionFactory build() {
        return build(DEFAULT_CONFIG_FILE_PATH);
    }

    /**
     * 生成SqlSessionFactory实列
     *
     * @param configFilePath 配置文件路径
     * @return SqlSessionFactory实列
     */
    public SqlSessionFactory build(String configFilePath) {
        InputStream inputStream = ResourcesUtils.getResourceAsStream(configFilePath);
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder(inputStream);
        Configuration configuration = configurationBuilder.parse();
        return new DefaultSqlSessionFactory(configuration);
    }
}
