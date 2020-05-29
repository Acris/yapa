package com.reorz.yapa.session;

import com.reorz.yapa.config.Configuration;

import java.io.Closeable;
import java.sql.Connection;
import java.util.List;

/**
 * SQL会话接口
 * <p>
 * 定义了CRUD方法，以及获取Mapper、当前配置和数据库连接的方法。
 *
 * @author Acris
 */
public interface SqlSession extends Closeable {
    /**
     * 查询单条记录
     *
     * @param <T>         查询结果类型
     * @param statementId 用来查找Statement的唯一Statement ID
     * @param parameters  查询参数
     * @return 查询结果
     */
    <T> T selectOne(String statementId, Object... parameters);

    /**
     * 查询列表
     *
     * @param <E>         列表元素类型
     * @param statementId 用来查找Statement的唯一Statement ID
     * @param parameters  查询参数
     * @return 查询结果列表
     */
    <E> List<E> selectList(String statementId, Object... parameters);


    /**
     * 执行插入语句
     *
     * @param statementId 用来查找Statement的唯一Statement ID
     * @param parameters  插入参数
     * @return 受影响行数
     */
    int insert(String statementId, Object... parameters);

    /**
     * 执行更新语句
     *
     * @param statementId 用来查找Statement的唯一Statement ID
     * @param parameters  更新参数
     * @return 受影响行数
     */
    int update(String statementId, Object... parameters);

    /**
     * 执行删除语句
     *
     * @param statementId 用来查找Statement的唯一Statement ID
     * @param parameters  删除参数
     * @return 受影响行数
     */
    int delete(String statementId, Object... parameters);

    /**
     * 关闭会话
     */
    @Override
    void close();

    /**
     * 查询当前配置
     *
     * @return 当前配置
     */
    Configuration getConfiguration();

    /**
     * 获取Mapper对象
     *
     * @param <T>  Mapper类型
     * @param type Mapper接口
     * @return 绑定到该SqlSession的Mapper
     */
    <T> T getMapper(Class<T> type);

    /**
     * 获取数据库连结
     *
     * @return 数据库连结
     */
    Connection getConnection();
}
