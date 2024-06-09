package com.api.soundsurf.music.domain.recommendation;

import com.api.soundsurf.music.domain.CrawlerService;
import com.api.soundsurf.music.domain.spotify.Utils;
import com.api.soundsurf.music.dto.MusicDto;
import com.api.soundsurf.music.entity.UserRecommendationMusic;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.ArrayList;
import java.util.Arrays;


@Service
@RequiredArgsConstructor
public class UserRecommendationMusicBusinessService {
    private final UserRecommendationMusicService service;
    private final CrawlerService crawlerService;

    public void firstRecommendAndSave(final Track[] data, final Long lastOrder, final Long userId) {
        final var recommendedMusics = save(data, lastOrder, userId);
        listenAndDelete(userId, recommendedMusics.get(0).getId());
    }


    public ArrayList<UserRecommendationMusic> save(final Track[] data, final Long lastOrder, final Long userId) {
        final var recommendMusics = new ArrayList<UserRecommendationMusic>();

        for (var i = 0; i < data.length; i++) {

            final var jsonMusicians = new JSONArray();

            final var album = convertIntoAlbumDto(data[i].getAlbum());

            Arrays.stream(data[i].getArtists()).forEach(e -> {
                Utils.hydrateJsonMusicians(e, jsonMusicians);
            });

            final var albumArtist = Arrays.stream(album.artists().toArray()).map(
                    e -> ((MusicDto.ArtistSimpleInfo.Musician) e).artistName()
            ).toArray(String[]::new);
            final var albumTitle = Utils.searchAbleString(album.albumName());
            final var crawlResult = crawlerService.getAlbumGenresRating(albumTitle, albumArtist);
            final var albumGenre = crawlResult[0];
            final var albumRating = crawlResult[1];
            final var albumMetadata = Utils.albumDtoToString(album, albumGenre, albumRating);
            final var artistsMetadata = Utils.musicianJsonToString(jsonMusicians);

            final var music = new UserRecommendationMusic(data[i], lastOrder + i, userId, albumMetadata, artistsMetadata);
            recommendMusics.add(service.save(music));
        }

        return recommendMusics;
    }

    public void listenAndDelete(final Long userId, final Long id) {
        final var userRecommendationMusic = service.get(userId, id);
        userRecommendationMusic.delete();

        service.save(userRecommendationMusic);
    }

    private MusicDto.AlbumSimpleInfo.Info convertIntoAlbumDto(final AlbumSimplified album) {
        final var albumArtists = Arrays.stream(album.getArtists()).map(MusicDto.ArtistSimpleInfo.Musician::new).toList();
        final var albumImages = Arrays.stream(album.getImages()).map(Image::getUrl).toList();
        return new MusicDto.AlbumSimpleInfo.Info(album.getName(), album.getId(), album.getReleaseDate(), album.getHref(), null, null, album.getAlbumType().getType(), albumArtists, albumImages);
    }

}
