package com.reorz.yapa.executor;

import com.reorz.yapa.core.MappedStatement;
import com.reorz.yapa.exceptions.PersistenceException;
import com.reorz.yapa.mapping.BoundSql;
import com.reorz.yapa.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultExecutor implements Executor {
    private static final Logger logger = LoggerFactory.getLogger(DefaultExecutor.class);

    @Override
    public <E> List<E> query(Connection connection, MappedStatement mappedStatement, Object[] parameters) throws SQLException {
        String parameterType = mappedStatement.getParameterType();
        String resultType = mappedStatement.getResultType();
        BoundSql boundSql = mappedStatement.getBoundSql();
        String sql = boundSql.getSql();
        List<String> parameterNameList = boundSql.getParameterNameList();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        List<E> results = new ArrayList<>();
        try {
            handleParameters(parameters, parameterType, parameterNameList, pstmt);
            ResultSet resultSet = pstmt.executeQuery();
            handleResultSet(resultType, results, resultSet);
        } catch (Exception e) {
            logger.error("Execute query failed. Cause: " + e.getMessage());
            throw new PersistenceException(e.getMessage());
        }
        return results;
    }

    @Override
    public int update(Connection connection, MappedStatement mappedStatement, Object[] parameters) throws SQLException {
        String parameterType = mappedStatement.getParameterType();
        BoundSql boundSql = mappedStatement.getBoundSql();
        String sql = boundSql.getSql();
        List<String> parameterNameList = boundSql.getParameterNameList();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        int affectedRows;
        try {
            handleParameters(parameters, parameterType, parameterNameList, pstmt);
            affectedRows = pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error("Execute query failed. Cause: " + e.getMessage());
            throw new PersistenceException(e.getMessage());
        }
        return affectedRows;
    }

    private void handleParameters(Object[] parameters, String parameterType, List<String> parameterNameList, PreparedStatement pstmt) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, SQLException {
        Class<?> parameterTypeClass = null;
        if (parameterType != null) {
            parameterTypeClass = ClassUtils.parseType(parameterType);
        }
        for (int i = 0; i < parameterNameList.size(); i++) {
            String paramName = parameterNameList.get(i);
            Object paramObj;
            if (parameterTypeClass == null || ClassUtils.isSimpleType(parameterTypeClass)) {
                paramObj = parameters[i];
            } else {
                Field field = parameterTypeClass.getDeclaredField(paramName);
                field.setAccessible(true);
                paramObj = field.get(parameters[0]);
            }
            pstmt.setObject(i + 1, paramObj);
        }
    }


    @SuppressWarnings("unchecked")
    private <E> void handleResultSet(String resultType, List<E> results, ResultSet resultSet) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
        Class<?> resultTypeClass = null;
        if (resultType != null) {
            resultTypeClass = ClassUtils.parseType(resultType);
        }
        while (resultSet.next()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            E resultObj = resultTypeClass == null ? null : (E) resultTypeClass.getDeclaredConstructor().newInstance();
            int columnCount = metaData.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                String columnName = metaData.getColumnName(i + 1);
                Object value = resultSet.getObject(columnName);

                if (resultTypeClass == null || ClassUtils.isSimpleType(resultTypeClass)) {
                    resultObj = (E) value;
                } else {
                    Field field = resultTypeClass.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(resultObj, value);
                }
            }
            results.add(resultObj);
        }
    }
}
