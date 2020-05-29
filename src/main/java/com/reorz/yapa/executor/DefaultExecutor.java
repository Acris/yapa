package com.reorz.yapa.executor;

import com.reorz.yapa.core.MappedStatement;
import com.reorz.yapa.exceptions.PersistenceException;
import com.reorz.yapa.mapping.BoundSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultExecutor implements Executor {
    private static final Logger logger = LoggerFactory.getLogger(DefaultExecutor.class);

    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> query(Connection connection, MappedStatement mappedStatement, Object[] parameters) throws SQLException {
        String parameterType = mappedStatement.getParameterType();
        String resultType = mappedStatement.getResultType();
        BoundSql boundSql = mappedStatement.getBoundSql();
        String sql = boundSql.getSql();
        List<String> parameterMappings = boundSql.getParameterMappings();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        List<E> results = new ArrayList<>();
        try {
            for (int i = 0; i < parameterMappings.size(); i++) {
                String paramName = parameterMappings.get(i);
                Class<?> parameterTypeClass = Class.forName(parameterType);
                Object paramObj = null;
                if (parameterTypeClass == Integer.class) {
                    paramObj = parameters[0];
                } else if (parameterTypeClass == String.class) {
                    paramObj = parameters[0];
                } else {
                    Field field = parameterTypeClass.getDeclaredField(paramName);
                    field.setAccessible(true);
                    paramObj = field.get(parameters[0]);
                }
                pstmt.setObject(i + 1, paramObj);
            }
            ResultSet resultSet = pstmt.executeQuery();
            Class<?> resultTypeClass = Class.forName(resultType);
            while (resultSet.next()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                E resultObj = (E) resultTypeClass.getDeclaredConstructor().newInstance();
                int columnCount = metaData.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = metaData.getColumnName(i + 1);
                    Object value = resultSet.getObject(columnName);

                    Field field = resultTypeClass.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(resultObj, value);
                }
                results.add(resultObj);
            }
        } catch (Exception e) {
            logger.error("Execute query failed. Cause: " + e.getMessage());
            throw new PersistenceException(e.getMessage());
        }
        return results;
    }

    @Override
    public int update(Connection connection, MappedStatement mappedStatement, Object[] parameters) throws SQLException {
        return 0;
    }
}
