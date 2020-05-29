package com.reorz.yapa.core;

import com.reorz.yapa.exceptions.PersistenceException;
import com.reorz.yapa.mapping.BoundSql;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MappedStatement {
    /**
     * Statement ID
     * <p>
     * 组成方式为<code>namespace.methodName</code>
     */
    private String id;

    /**
     * 参数类型
     */
    private String parameterType;

    /**
     * 返回值类型
     */
    private String resultType;

    /**
     * 待执行的SQL
     */
    private String sql;

    public MappedStatement(String id, String parameterType, String resultType, String sql) {
        this.id = id;
        this.parameterType = parameterType;
        this.resultType = resultType;
        this.sql = sql;
    }

    public BoundSql getBoundSql() {
        if (sql == null) {
            throw new PersistenceException("SQL could not be null.");
        }
        List<String> parameterNameList = new ArrayList<>();
        Pattern pattern = Pattern.compile("#\\{(\\w+)}");
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            parameterNameList.add(matcher.group(1));
        }
        String parsedSql = matcher.replaceAll("?");
        return new BoundSql(parsedSql, parameterNameList);
    }


    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
