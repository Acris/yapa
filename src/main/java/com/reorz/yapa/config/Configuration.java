package com.reorz.yapa.config;

import com.reorz.yapa.core.MappedStatement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Yapa核心配置类
 *
 * @author Acris
 */
public class Configuration {
    /**
     * 数据源
     */
    private DataSource dataSource;

    /**
     * 以<code>namespace.methodName</code>为key，MappedStatement为值的Map
     */
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    // Getters and Setters

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
