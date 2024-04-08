CREATE TABLE `session_tokens` (
                                  `id` BIGINT NOT NULL AUTO_INCREMENT,
                                  `token` VARCHAR(36) NOT NULL,
                                  `user_id` VARCHAR(36) NOT NULL,
                                  `created_at` DATETIME NOT NULL,
                                  `expired_at` DATETIME NOT NULL,
                                  PRIMARY KEY (`id`)
);

CREATE TABLE `cars` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                        `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                        `image` tinyblob NOT NULL,
                        PRIMARY KEY (`id`)
);

CREATE TABLE `genres` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                          `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
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

CREATE TABLE `music_genres` (
                                `genre_id` bigint DEFAULT NULL,
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `music_id` bigint DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                KEY `FKiiq4is3n9a9yh8hko5i51pqji` (`genre_id`),
                                KEY `FKt91jx9wrovjwi8op9crnqdish` (`music_id`),
                                CONSTRAINT `FKiiq4is3n9a9yh8hko5i51pqji` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`),
                                CONSTRAINT `FKt91jx9wrovjwi8op9crnqdish` FOREIGN KEY (`music_id`) REFERENCES `musics` (`id`)
);

CREATE TABLE `qrs` (
                       `id` bigint NOT NULL AUTO_INCREMENT,
                       `qr` tinyblob NOT NULL,
                       PRIMARY KEY (`id`)
);

CREATE TABLE `user_profiles` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `image` tinyblob NOT NULL,
                                 PRIMARY KEY (`id`)
);



CREATE TABLE `users` (
                         `new_user` bit(1) NOT NULL,
                         `car_id` bigint NOT NULL,
                         `created_at` datetime(6) NOT NULL,
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `profile_id` bigint NOT NULL,
                         `qr_id` bigint DEFAULT NULL,
                         `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                         `nickname` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                         `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UK_569dovefoscirbt4o6qt29ml4` (`qr_id`),
                         KEY `FKfrbpbk3ue6xhdoi0sj1inyuu` (`car_id`),
                         KEY `FK53vd0kkkq53stcvl51np2j728` (`profile_id`),
                         CONSTRAINT `FK53vd0kkkq53stcvl51np2j728` FOREIGN KEY (`profile_id`) REFERENCES `user_profiles` (`id`),
                         CONSTRAINT `FKfrbpbk3ue6xhdoi0sj1inyuu` FOREIGN KEY (`car_id`) REFERENCES `cars` (`id`),
                         CONSTRAINT `FKpspeer8lwddbkigff2nrx8vb` FOREIGN KEY (`qr_id`) REFERENCES `qrs` (`id`)
);



CREATE TABLE `saved_musics` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `music_id` bigint DEFAULT NULL,
                                `user_id` bigint DEFAULT NULL,
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
                               CONSTRAINT `FKmr5v9qbtxpoa72t8acn72vu17` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`),
                               CONSTRAINT `FKp96ubmofdb42r46tugvvms8fv` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);



