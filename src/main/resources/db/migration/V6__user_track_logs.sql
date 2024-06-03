CREATE TABLE `user_track_logs`
(
    `id`         bigint AUTO_INCREMENT,
    `user_id`    bigint      NOT NULL,
    `track_id`   varchar(32) NOT NULL,
    `title` varchar(255) NOT NULL,
    `image_url` varchar(255) ,
    `track_preview_url` varchar(255),
    `track_spotify_url` varchar(255),
    `track_duration_ms` int NOT NULL ,
    `artists_metadata` text,
    `album_metadata` text,
    `releasedDate` DATETIME ,
    `order`    bigint      NOT NULL,
    `created_at` DATETIME NOT NULL,
    `expired_at` DATETIME NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE `user_track_orders` (`id` int NOT NULL AUTO_INCREMENT,`userId` bigint NOT NULL,`order` bigint NOT NULL,`first` tinyint(1) NOT NULL DEFAULT '0',`last` tinyint(1) NOT NULL DEFAULT '0', PRIMARY KEY (id));