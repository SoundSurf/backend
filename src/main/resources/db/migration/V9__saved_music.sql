ALTER TABLE `saved_musics`
    DROP COLUMN `user_recommendation_music_id`,
    CHANGE `saved_at` `saved_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN `track_id` varchar(32) NOT NULL AFTER `user_id`,
    ADD COLUMN `track_name` varchar(255) NOT NULL AFTER `user_id`,
    ADD COLUMN `track_preview_url` varchar(255) NULL AFTER `saved_at`,
    ADD COLUMN `track_spotify_url` varchar(255) NULL AFTER `saved_at`,
    ADD COLUMN `track_duration_ms` int NULL AFTER `saved_at`,
    ADD COLUMN `artists_metadata` text NULL AFTER `saved_at`,
    ADD COLUMN `album_metadata` text NULL AFTER `saved_at`;