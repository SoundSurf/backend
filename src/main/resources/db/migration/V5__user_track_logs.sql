CREATE TABLE `user_track_logs`
(
    `id`         bigint AUTO_INCREMENT,
    `user_id`    bigint      NOT NULL,
    `track_id`   varchar(32) NOT NULL,
    `created_at` datetime    NOT NULL,
    PRIMARY KEY (id)
);