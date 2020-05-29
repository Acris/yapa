package com.reorz.yapa.test;

import com.reorz.yapa.session.SqlSession;
import com.reorz.yapa.session.SqlSessionFactory;
import com.reorz.yapa.session.SqlSessionFactoryBuilder;
import com.reorz.yapa.test.entity.User;
import com.reorz.yapa.test.mapper.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * IPersistence测试类
 *
 * @author Acris
 */
public class YapaTest {
    private static final Logger logger = LoggerFactory.getLogger(YapaTest.class);
    private UserMapper userMapper;
    private SqlSession sqlSession;

    @BeforeEach
    public void initIPersistence() {
        // 加载配置文件
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build();
        sqlSession = sqlSessionFactory.openSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @AfterEach
    public void close() {
        sqlSession.close();
    }

    @Test
    public void testSelectAll() {
        logger.info("开始查询所有用户...");
        List<User> allUsers = userMapper.selectAll();
        allUsers.forEach(System.out::println);
    }

    @Test
    public void testSelectById() {
        logger.info("开始查询ID为5的用户...");
        long userId = 2;
        User userById = userMapper.selectById(userId);
        System.out.println(userById);
    }

    @Test
    public void testSelectByName() {
        logger.info("开始查询所有姓张的用户...");
        String namePatter = "张%";
        List<User> usersByName = userMapper.selectByName(namePatter);
        usersByName.forEach(System.out::println);
    }
}
