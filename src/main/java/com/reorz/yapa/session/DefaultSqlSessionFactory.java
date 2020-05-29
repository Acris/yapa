package com.reorz.yapa.session;

import com.reorz.yapa.config.Configuration;
import com.reorz.yapa.executor.DefaultExecutor;
import com.reorz.yapa.executor.Executor;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        Executor executor = new DefaultExecutor();
        return new DefaultSqlSession(configuration, executor);
    }
}
