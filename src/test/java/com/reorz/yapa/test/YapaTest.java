package com.reorz.yapa.test;

import com.reorz.yapa.session.SqlSession;
import com.reorz.yapa.session.SqlSessionFactory;
import com.reorz.yapa.session.SqlSessionFactoryBuilder;
import com.reorz.yapa.test.entity.User;
import com.reorz.yapa.test.mapper.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Yapa测试类
 *
 * @author Acris
 */
public class YapaTest {
    private UserMapper userMapper;
    private SqlSession sqlSession;

    @BeforeEach
    public void initYapa() {
        // 加载配置文件，生成SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build();
        // 生成SqlSession
        sqlSession = sqlSessionFactory.openSession();
        // 通过动态代理生成Mapper
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @AfterEach
    public void close() {
        sqlSession.close();
    }

    @Test
    public void testSelectAll() {
        List<User> allUsers = userMapper.selectAll();
        allUsers.forEach(System.out::println);
    }

    @Test
    public void testSelectById() {
        long userId = 2;
        User userById = userMapper.selectById(userId);
        System.out.println(userById);
    }

    @Test
    public void testSelectByName() {
        String namePatter = "张%";
        List<User> usersByName = userMapper.selectByName(namePatter);
        usersByName.forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        long userId = 8;
        User user = new User(userId, "小八", 28);
        userMapper.addUser(user);
    }

    @Test
    public void testUpdate() {
        long userId = 8;
        User user = new User(userId, "小黄", 30);
        userMapper.updateUserById(user);
    }

    @Test
    public void testDelete() {
        long userId = 8;
        userMapper.deleteUserById(userId);
    }
}
