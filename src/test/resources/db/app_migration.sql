create table users
(
    uuid       varchar(36)  not null,
    username         varchar(255) not null,
    password   vachar(255) not null,
    deleted tinyint not null default 0,
    created_at datetime(6) not null,
    updated_at datetime(6) null,

    Primary Key (uuid)
);

