package com.reorz.yapa.session;

import com.reorz.yapa.config.Configuration;
import com.reorz.yapa.core.MappedStatement;
import com.reorz.yapa.enums.SqlCommandType;
import com.reorz.yapa.exceptions.PersistenceException;
import com.reorz.yapa.executor.Executor;
import com.reorz.yapa.utils.SqlCommandUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * SQL会话接口的默认实现
 *
 * @author Acris
 */
public class DefaultSqlSession implements SqlSession {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSqlSession.class);

    private final Configuration configuration;
    private final Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <T> T selectOne(String statementId, Object... parameters) {
        return null;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... parameters) {
        try {
            return executor.query(getConnection(), getMappedStatement(statementId), parameters);
        } catch (SQLException e) {
            logger.error("Query faied. Cause: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public int insert(String statementId, Object... parameters) {
        return 0;
    }

    @Override
    public int update(String statementId, Object... parameters) {
        return 0;
    }

    @Override
    public int delete(String statementId, Object... parameters) {
        return 0;
    }

    @Override
    public void close() {
        try {
            getConnection().close();
        } catch (SQLException e) {
            logger.error("Close connection failed. Cause: " + e.getMessage(), e);
        }
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getMapper(Class<T> mapperClass) {
        return (T) Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[]{mapperClass}, (proxy, method, args) -> {
            String namespace = method.getDeclaringClass().getName();
            String methodName = method.getName();
            String statementId = namespace + "." + methodName;
            SqlCommandType commandType = SqlCommandUtils.getSqlCommandType(method);
            switch (commandType) {
                case SELECT:
                    // 判断返回类型是否为集合
                    Class<?> returnType = method.getReturnType();
                    if (Collection.class.isAssignableFrom(returnType)) {
                        return selectList(statementId, args);
                    }
                    return selectOne(statementId, args);
                case UPDATE:
                    return update(statementId, args);
                case INSERT:
                    return insert(statementId, args);
                case DELETE:
                    return delete(statementId, args);
                default:
                    throw new PersistenceException("Unknown command type.");
            }
        });
    }

    @Override
    public Connection getConnection() {
        try {
            return configuration.getDataSource().getConnection();
        } catch (SQLException e) {
            logger.error("Error getting a new connection. Cause: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    private MappedStatement getMappedStatement(String statementId) {
        Map<String, MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();
        return mappedStatementMap.get(statementId);
    }
}
