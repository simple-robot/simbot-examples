create table if not exists reply
(
    id      int auto_increment comment 'ID'
        primary key,
    keyword varchar(20) not null comment '触发关键词',
    content varchar(50) not null comment '回复语句',
    constraint reply_keyword_uindex
        unique (keyword)
);

insert into reply(keyword, content) values ( '你好', '你也好' );
insert into reply(keyword, content) values ( '晚安', '好梦' );