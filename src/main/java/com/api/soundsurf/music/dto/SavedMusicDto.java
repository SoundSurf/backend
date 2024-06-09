package com.api.soundsurf.music.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class SavedMusicDto {
    @Schema(name = "SavedMusicDto.SavedMusic")
    @Getter
    public static class SavedMusic {
        private final Long id;
        private final String title;
        private final String artist;
        private final String album;
        private final String trackId;
        public SavedMusic(com.api.soundsurf.list.entity.SavedMusic entitySavedMusic) {
            this.id = entitySavedMusic.getId();
            this.title = entitySavedMusic.getUserRecommendationMusic().getTrackName();
            this.artist = entitySavedMusic.getUserRecommendationMusic().getArtistsMetadata();
            this.album = entitySavedMusic.getUserRecommendationMusic().getAlbumMetadata();
            this.trackId = entitySavedMusic.getUserRecommendationMusic().getTrackId();
        }
    }

    public static class GetAll {
        @Schema(name = "SavedMusicDto.Get.Response")
        @Getter
        @AllArgsConstructor
        public static class Response {
            private List<SavedMusic> savedMusics;
        }
    }
}
