package com.reorz.yapa.executor;

import com.reorz.yapa.core.MappedStatement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Executor {
    <E> List<E> query(Connection connection, MappedStatement mappedStatement, Object[] parameters) throws SQLException;

    int update(Connection connection, MappedStatement mappedStatement, Object[] parameters) throws SQLException;
}
