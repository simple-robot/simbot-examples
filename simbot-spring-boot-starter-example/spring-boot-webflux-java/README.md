# Spring Boot Webflux Java

这是在Spring Boot 3.0下，结合使用：
- Spring Reactive Web 
- Spring Data R2DBC
- H2 Database
- JDK17

的 Demo 工程。在这里，将会提供一个简单的示例：关键词自动回复。

通过数据库的表 `reply` 来记录某关键词和对应的回复语句，然后在bot的监听中进行检测与回复的示例。

参考用表结构:

```mysql
# noinspection SqlNoDataSourceInspectionForFile

-- auto-generated definition
create table reply
(
    id      int auto_increment comment 'ID'
        primary key,
    keyword varchar(20) not null comment '触发关键词',
    content varchar(50) not null comment '回复语句',
    constraint reply_keyword_uindex
        unique (keyword)
);
```

