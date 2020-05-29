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
    @Select("select * from user where id = #{id}")
    @ParameterType(Long.class)
    @ResultType(User.class)
    User selectById(Long id);

    @Select("select * from user")
    @ResultType(User.class)
    List<User> selectAll();

    @Select("select * from user where name like #{namePatter}")
    @ParameterType(String.class)
    @ResultType(User.class)
    List<User> selectByName(String namePatter);

    @Insert("insert into user(id, name, age) values(#{id}, #{name}, #{age})")
    @ParameterType(User.class)
    int addUser(User user);

    @Update("update user set name = #{name}, age = #{age} where id = #{id}")
    @ParameterType(User.class)
    int updateUserById(User user);

    @Delete("delete from user where id = #{id}")
    @ParameterType(Long.class)
    int deleteUserById(Long id);
}
