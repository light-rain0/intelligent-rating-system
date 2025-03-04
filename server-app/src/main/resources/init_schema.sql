create table user_info
(
    id         bigint auto_increment primary key,
    username   varchar(64) not null comment '工号',
    password   varchar(64) not null comment '密码',
    token      varchar(64) not null default '',
    created_at datetime    not null default now(),
    updated_at datetime    not null default now() on update now()
);

insert into user_info (username, password)
values ('admin', 'admin');

insert into user_info (username, password)
values ('cyh', '123456');