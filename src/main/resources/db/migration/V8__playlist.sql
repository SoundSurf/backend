CREATE TABLE `playlists` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `created_at` datetime(6) NOT NULL,
                             `complete` tinyint(1) NOT NULL,
                             `deleted` tinyint(1) NOT NULL,
                             `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                             `user_id` bigint DEFAULT NULL,
                             PRIMARY KEY (`id`)
);

CREATE TABLE `playlist_genres` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `genre_id` bigint DEFAULT NULL,
                                  `playlist_id` bigint DEFAULT NULL,
                                  PRIMARY KEY (`id`)
);

CREATE TABLE `playlist_musics` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `memo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                  `music_id` bigint DEFAULT NULL,
                                  `playlist_id` bigint DEFAULT NULL,
                                  PRIMARY KEY (`id`)
);

ALTER TABLE `musics`
    DROP COLUMN `released_date`,
    DROP COLUMN `album`,
    ADD COLUMN `track_id` varchar(255) NOT NULL AFTER `id`;
