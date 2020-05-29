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
    private final List<String> parameterNameList;

    public BoundSql(String sql, List<String> parameterNameList) {
        this.sql = sql;
        this.parameterNameList = parameterNameList;
    }

    public String getSql() {
        return sql;
    }

    public List<String> getParameterNameList() {
        return parameterNameList;
    }
}
