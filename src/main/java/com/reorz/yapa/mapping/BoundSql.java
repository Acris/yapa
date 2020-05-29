package com.reorz.yapa.mapping;

import java.util.List;

/**
 * 封装后的SQL
 *
 * @author Acris
 */
public class BoundSql {
    /**
     * 解析后的带?参数的SQL
     */
    private final String sql;
    /**
     * 占位符中的参数值
     */
    private final List<String> parameterMappings;

    public BoundSql(String sql, List<String> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    public String getSql() {
        return sql;
    }

    public List<String> getParameterMappings() {
        return parameterMappings;
    }
}
