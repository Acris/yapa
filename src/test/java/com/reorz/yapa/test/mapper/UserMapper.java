package com.reorz.yapa.test.mapper;


import com.reorz.yapa.annotations.*;
import com.reorz.yapa.test.entity.User;

import java.util.List;

/**
 * 用户Mapper
 *
 * @author Acris
 */
public interface UserMapper {
    /**
     * 通过ID查询用户
     *
     * @param id 用户ID
     * @return 对应ID的用户对象
     */
    @Select("select * from user where id = #{id}")
    @ResultType(User.class)
    User selectById(Long id);

    /**
     * 查询所有用户
     *
     * @return 所有用户列表
     */
    @Select("select * from user")
    @ResultType(User.class)
    List<User> selectAll();

    /**
     * 通过名称查询用户，使用like模糊查询
     *
     * @param namePatter 用户名称
     * @return 符合条件的用户列表
     */
    @Select("select * from user where name like #{namePatter}")
    @ResultType(User.class)
    List<User> selectByName(String namePatter);

    /**
     * 新增用户
     *
     * @param user 待新增的用户参数
     * @return 受影响行数
     */
    @Insert("insert into user(id, name, age) values(#{id}, #{name}, #{age})")
    @ParameterType(User.class)
    int addUser(User user);

    /**
     * 通过ID更新用户信息
     *
     * @param user 待更新的用户信息
     * @return 受影响行数
     */
    @Update("update user set name = #{name}, age = #{age} where id = #{id}")
    @ParameterType(User.class)
    int updateUserById(User user);

    /**
     * 通过ID删除对应用户
     *
     * @param id 用户ID
     * @return 受影响行数
     */
    @Delete("delete from user where id = #{id}")
    int deleteUserById(Long id);
}
