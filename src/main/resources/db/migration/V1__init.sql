CREATE TABLE `session_tokens` (
                                  `id` BIGINT NOT NULL AUTO_INCREMENT,
                                  `token` VARCHAR(36) NOT NULL,
                                  `user_id` BIGINT NOT NULL,
                                  `created_at` DATETIME NOT NULL,
                                  `expired_at` DATETIME NOT NULL,
                                  PRIMARY KEY (`id`)
);

CREATE TABLE `musics` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `released_date` datetime(6) DEFAULT NULL,
                          `album` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                          `artist` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                          `image_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                          `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                          PRIMARY KEY (`id`)
);

CREATE TABLE `users` (
                         `new_user` bit(1) NOT NULL,
                         `car_id` bigint NOT NULL,
                         `created_at` datetime(6) NOT NULL,
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `image_s3_bucket_path` varchar(255) NULL,
                         `qr_s3_bucket_path` varchar(255) NULL,
                         `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                         `nickname` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                         `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                         PRIMARY KEY (`id`)
);



CREATE TABLE `saved_musics` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `music_id` bigint DEFAULT NULL,
                                `user_id` bigint DEFAULT NULL,
                                `user_recommendation_music_id` bigint DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                KEY `FKqljpftfcsas9avu9karm0ffwa` (`music_id`),
                                KEY `FK20hr47cutwpwmdaiul3mxqkx8` (`user_id`),
                                CONSTRAINT `FK20hr47cutwpwmdaiul3mxqkx8` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                                CONSTRAINT `FKqljpftfcsas9avu9karm0ffwa` FOREIGN KEY (`music_id`) REFERENCES `musics` (`id`)
);

CREATE TABLE `user_genres` (
                               `genre_id` bigint DEFAULT NULL,
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `user_id` bigint DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FKmr5v9qbtxpoa72t8acn72vu17` (`genre_id`),
                               KEY `FKp96ubmofdb42r46tugvvms8fv` (`user_id`),
                               CONSTRAINT `FKp96ubmofdb42r46tugvvms8fv` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);



