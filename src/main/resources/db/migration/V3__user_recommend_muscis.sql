CREATE TABLE `user_recommendation_musics`
(`id` bigint AUTO_INCREMENT,
`user_id` bigint NOT NULL,
`order` bigint NOT NULL,
`track_id` varchar(32) NOT NULL,
 `track_name` varchar(255) NOT NULL ,
 `track_preview_url` varchar(255) NULL,
 `track_spotify_url` varchar(255) NULL,
 `track_duration_ms` int NULL,
 `artists_metadata` text NULL ,
 `album_metadata` text NULL,
 `deleted` tinyint(1) NOT NULL DEFAULT '0',
PRIMARY KEY (id));
