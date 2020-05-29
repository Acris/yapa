package com.reorz.yapa.config;

import com.reorz.yapa.annotations.*;
import com.reorz.yapa.core.MappedStatement;
import com.reorz.yapa.enums.SqlCommandType;
import com.reorz.yapa.utils.SqlCommandUtils;
import com.reorz.yapa.utils.YamlUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 配置构造类
 * <p>
 * 通过解析YAML配置文件以及扫描注解，生成Configuration对象。
 *
 * @author Acris
 */
public class ConfigurationBuilder {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationBuilder.class);

    private final Configuration configuration;
    private final ConfigurationProperties configurationProperties;

    public ConfigurationBuilder(InputStream inputStream) {
        this.configuration = new Configuration();
        // 解析配置文件
        logger.debug("解析配置文件...");
        this.configurationProperties = YamlUtils.readFromStream(inputStream, ConfigurationProperties.class);
    }

    public Configuration parse() {
        // 初始化DataSource
        logger.debug("初始化HikariCP连接池...");
        HikariConfig config = new HikariConfig();
        ConfigurationProperties.DataSource dataSourceProperties = configurationProperties.getDataSource();
        config.setDriverClassName(dataSourceProperties.getDriver());
        config.setJdbcUrl(dataSourceProperties.getUrl());
        config.setUsername(dataSourceProperties.getUsername());
        config.setPassword(dataSourceProperties.getPassword());
        HikariDataSource ds = new HikariDataSource(config);
        configuration.setDataSource(ds);

        // 扫描Mapper
        logger.debug("扫描Mapper...");
        ConfigurationProperties.Mappers mappersProperties = configurationProperties.getMappers();
        Map<String, MappedStatement> mappedStatementMap = new HashMap<>();
        Reflections reflections = new Reflections(mappersProperties.getBasePackage(), new SubTypesScanner(false));
        Set<Class<?>> allMappers = reflections.getSubTypesOf(Object.class);
        // 扫描Mapper中的带CRUD注解的方法，拼装mappedStatement。
        allMappers.forEach(mapper -> {
            String namespace = mapper.getName();
            Method[] methods = mapper.getDeclaredMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                String statementId = namespace + "." + methodName;
                String parameterType = null, resultType = null, sql = null;

                SqlCommandType commandType = SqlCommandUtils.getSqlCommandType(method);
                switch (commandType) {
                    case DELETE:
                        sql = method.getDeclaredAnnotation(Delete.class).value();
                        break;
                    case INSERT:
                        sql = method.getDeclaredAnnotation(Insert.class).value();
                        break;
                    case UPDATE:
                        sql = method.getDeclaredAnnotation(Update.class).value();
                        break;
                    case SELECT:
                        sql = method.getDeclaredAnnotation(Select.class).value();
                        break;
                    default:
                        break;
                }
                // 参数类型
                ParameterType parameterTypeAnno = method.getAnnotation(ParameterType.class);
                if (parameterTypeAnno != null) {
                    parameterType = parameterTypeAnno.value().getName();
                }
                // 返回值类型
                ResultType resultTypeAnno = method.getAnnotation(ResultType.class);
                if (resultTypeAnno != null) {
                    resultType = resultTypeAnno.value().getName();
                }
                mappedStatementMap.put(statementId, new MappedStatement(statementId, parameterType, resultType, sql));
            }
        });
        configuration.setMappedStatementMap(mappedStatementMap);
        return configuration;
    }
}
