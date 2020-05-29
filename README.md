# Yapa
Yet Another Persistence framework made by Acris

## Quick Start

### 引入依赖
通过引入编译后的jar文件，或者通过Maven等引入依赖（未发布到中央仓库）:
```xml
<!-- Yapa -->
<dependency>
    <groupId>com.reorz</groupId>
    <artifactId>yapa</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 创建配置文件
在resources目录新建配置文件，默认配置文件名为`yapa.yml`，也可以设置为其他名称。以下为配置文件内容示例：
```yaml
yapa:
  # 数据源配置
  datasource:
    driver: org.mariadb.jdbc.Driver
    url: jdbc:mariadb://localhost:3306/yapa
    username: root
    password: yourpassword
  # 需要扫描的Mapper包配置
  mappers:
    base-package: com.yourdomain.mapper
```

### 编写Mapper接口
编写Mapper接口，并且编写对应的方法，然后在方法上添加相关注解。
支持的注解有：
- `@Select` 代表该方法执行查询语句，注解的值为对应的SQL。
- `@Insert` 代表该方法执行插入语句，注解的值为对应的SQL。
- `@Update` 代表该方法执行更新语句，注解的值为对应的SQL。
- `@Delete` 代表该方法执行删除语句，注解的值为对应的SQL。
- `@ParameterType` 参数类型。当参数为具体对象类型的时候需要使用该注解，值为参数类型的Class对象。
- `@ResultType` 返回值类型。当返回值为具体对象类型的时候需要使用该注解，值为返回值类型的Class对象。
当参数类型和返回值为基本数据类型及其包装类的时候，可以省略`@ParameterType`和`@ResultType`注解。

以下为接口示例：
```java
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
```

### 获取SqlSession及Mapper
代码示例：
```java
// 加载配置文件，生成SqlSessionFactory，如果配置文件名不是yapa.yml,则需要在build()方法传入对应的配置文件路径。
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build();
// 生成SqlSession
SqlSession sqlSession = sqlSessionFactory.openSession();
// 通过动态代理生成Mapper
UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
```

### 调用Mapper中的方法
代码示例：
```java
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
```

更多用法可以参考源码中的`com.reorz.yapa.test.YapaTest`测试类。