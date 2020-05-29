package com.reorz.yapa.executor;

import com.reorz.yapa.core.MappedStatement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Executor {
    /**
     * 查询数据库
     *
     * @param connection      数据库连接
     * @param mappedStatement MappedStatement对象，封装了查询数据库操作相关参数
     * @param parameters      查询参数
     * @param <E>             返回列表元素类型
     * @return 查询结果列表
     * @throws SQLException SQL执行异常
     */
    <E> List<E> query(Connection connection, MappedStatement mappedStatement, Object[] parameters) throws SQLException;

    /**
     * 数据库更新操作，包括插入、修改和删除
     *
     * @param connection      数据库连接
     * @param mappedStatement MappedStatement对象，封装了查询数据库操作相关参数
     * @param parameters      更新参数
     * @return 受影响行数
     * @throws SQLException SQL执行异常
     */
    int update(Connection connection, MappedStatement mappedStatement, Object[] parameters) throws SQLException;
}
