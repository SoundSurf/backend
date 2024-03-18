create table users
(
    id bigint auto_increment,
    uuid       varchar(36)  not null,
    username         varchar(255) not null,
    password   varchar(255) not null,
    email varchar(255) not null,
    deleted tinyint not null default 0,
    locked tinyint not null default 0,
    created_at datetime(6) not null,
    updated_at datetime(6) null,

    Primary Key (id)
);