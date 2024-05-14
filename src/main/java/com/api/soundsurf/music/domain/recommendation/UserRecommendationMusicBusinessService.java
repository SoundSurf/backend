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

import java.util.Arrays;


@Service
@RequiredArgsConstructor
public class UserRecommendationMusicBusinessService {
    private final UserRecommendationMusicService service;
    private final CrawlerService crawlerService;

    public void save(final Track[] data, final Long lastOrder, final Long userId) {
        for (var i = 0; i < data.length; i++) {

            final var jsonMusicians = new JSONArray();

           final var album = convertIntoAlbumDto(data[i].getAlbum());

            Arrays.stream(data[i].getArtists()).forEach(e -> {
                hydrateJsonMusicians(e, jsonMusicians);
            });

            final var albumArtist = Utils.searchAbleString(album.artists().get(0).artistName());
            final var albumTitle = Utils.searchAbleString(album.albumName());
            final var crawlResult = crawlerService.getAlbumGenresRating(albumTitle, albumArtist);
            final var albumGenre = crawlResult[0];
            final var albumRating = crawlResult[1];
            final var albumMetadata = albumDtoToString(album, albumGenre, albumRating);
            final var artistsMetadata = musicianJsonToString(jsonMusicians);

            final var music = new UserRecommendationMusic(data[i], lastOrder + i, userId, albumMetadata, artistsMetadata);
            service.save(music);
        }
    }

    public void listenAndDelete(final Long userId, final Long id) {
        final var userRecommendationMusic = service.get(userId, id);
        userRecommendationMusic.delete();

        service.save(userRecommendationMusic);
    }

    private MusicDto.AlbumSimpleInfo.Info convertIntoAlbumDto(final AlbumSimplified album) {
        final var albumArtists = Arrays.stream(album.getArtists()).map(MusicDto.ArtistSimpleInfo.Musician::new).toList();
        final var albumImages = Arrays.stream(album.getImages()).map(Image::getUrl).toList();
        return new MusicDto.AlbumSimpleInfo.Info(album.getName(), album.getId(), album.getReleaseDate(), album.getHref(), null, null, albumArtists, albumImages);
    }

    private void hydrateJsonMusicians(final ArtistSimplified musician, final JSONArray jsonMusicians) {
        final var jsonMusician = new JSONObject();

        jsonMusician.put("artistName", musician.getName());
        jsonMusician.put("id", musician.getId());
        jsonMusician.put("spotifyUrl", musician.getExternalUrls().getExternalUrls().get("spotify"));
        jsonMusicians.put(jsonMusician);
    }

    private String albumDtoToString(final  MusicDto.AlbumSimpleInfo.Info album, final String genre, final String rating) {
        JSONObject albumJson = new JSONObject();
        albumJson.put("albumName", album.albumName());
        albumJson.put("id", album.id());
        albumJson.put("releaseDate", album.releaseDate());
        albumJson.put("spotifyUrl", album.spotifyUrl());
        albumJson.put("genres", genre);
        albumJson.put("rating",rating);

        JSONArray artistsJson = new JSONArray();
        for (MusicDto.ArtistSimpleInfo.Musician artist : album.artists()) {
            JSONObject artistJson = new JSONObject();
            artistJson.put("artistName", artist.artistName());
            artistJson.put("id", artist.id());
            artistJson.put("spotifyUrl", artist.spotifyUrl());
            artistsJson.put(artistJson);
        }
        albumJson.put("artists", artistsJson);

        JSONArray imagesJson = new JSONArray(album.images());
        albumJson.put("images", imagesJson);

        return albumJson.toString();
    }

    private String musicianJsonToString(final JSONArray jsonMusicians) {
        final var artistsMetadata = new StringBuilder();
        artistsMetadata.append("[");

        for (var jsonMusician : jsonMusicians) {
            artistsMetadata.append(jsonMusician.toString());
            artistsMetadata.append(",");
        }

        if (artistsMetadata.length() > 0) {
            artistsMetadata.setLength(artistsMetadata.length() - 1);
        }

        artistsMetadata.append("]");
        return artistsMetadata.toString();
    }

}
