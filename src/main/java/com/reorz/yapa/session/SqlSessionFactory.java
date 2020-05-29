package com.reorz.yapa.session;

public interface SqlSessionFactory {
    /**
     * 开启一个新的SqlSession
     *
     * @return SqlSession
     */
    SqlSession openSession();
}
