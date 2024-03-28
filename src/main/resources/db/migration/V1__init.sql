create table users
(
    id bigint auto_increment,
    email varchar(255) not null,
    password   varchar(255) not null,
    nickname   varchar(255)  null,
    car_id bigint not null,
    user_profile_id bigint not null,
    user_qr_id bigint not null,
    new_user tinyint not null default 1,
    created_at datetime(6) not null,

    Primary Key (id)
);

CREATE TABLE `session_tokens` (
                                  `id` BIGINT NOT NULL AUTO_INCREMENT,
                                  `token` VARCHAR(36) NOT NULL,
                                  `user_id` VARCHAR(36) NOT NULL,
                                  `created_at` DATETIME NOT NULL,
                                  `expired_at` DATETIME NOT NULL,
                                  PRIMARY KEY (`id`)
);