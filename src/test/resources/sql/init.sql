-- 创建用户表
create table user
(
    id   bigint      not null comment '用户ID'
        primary key,
    name varchar(20) not null comment '姓名',
    age  int(4)      not null comment '年龄'
)
    comment '用户表';

-- 导入示例数据
INSERT INTO user (id, name, age) VALUES (1, '袁小钦', 28);
INSERT INTO user (id, name, age) VALUES (2, '李四', 21);
INSERT INTO user (id, name, age) VALUES (3, '张三', 23);
INSERT INTO user (id, name, age) VALUES (4, '张三丰', 35);