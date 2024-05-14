package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.music.dto.MusicDto;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String searchAbleString(final String str) {
        return str.toLowerCase().replace(' ', '-');
    }

    public static List<MusicDto.ArtistSimpleInfo.Musician> convertJsonStringToMusicianDtoList(final String metadata) {
        final var artistArr = new JSONArray(metadata);
        final var MusicianList = new ArrayList<MusicDto.ArtistSimpleInfo.Musician>();

        for (var i = 0; i < artistArr.length(); i++) {
            final var nowArtist = artistArr.getJSONObject(i);

            MusicianList.add(new MusicDto.ArtistSimpleInfo.Musician(nowArtist));
        }

        return MusicianList;
    }


    public static MusicDto.AlbumSimpleInfo.Info convertJsonStringToAlbumDto(final String metadata) {

        JSONObject albumJson = new JSONObject(metadata);
        String albumName = albumJson.getString("albumName");
        String id = albumJson.getString("id");
        String releaseDate = albumJson.getString("releaseDate");
        String spotifyUrl = albumJson.getString("spotifyUrl");
        String genres = albumJson.isNull("genres") ? null : albumJson.getString("genres");
        String rating = albumJson.isNull("rating") ? null : albumJson.getString("rating");

        JSONArray artistsJson = albumJson.getJSONArray("artists");
        List<MusicDto.ArtistSimpleInfo.Musician> artists = new ArrayList<>();
        for (int i = 0; i < artistsJson.length(); i++) {
            JSONObject artistJson = artistsJson.getJSONObject(i);
            String artistName = artistJson.getString("artistName");
            String artistId = artistJson.getString("id");
            String artistSpotifyUrl = artistJson.getString("spotifyUrl");
            artists.add(new MusicDto.ArtistSimpleInfo.Musician(artistName, artistId, artistSpotifyUrl));
        }

        JSONArray imagesJson = albumJson.getJSONArray("images");
        List<String> images = new ArrayList<>();
        for (int i = 0; i < imagesJson.length(); i++) {
            images.add(imagesJson.getString(i));
        }

        return new MusicDto.AlbumSimpleInfo.Info(albumName, id, releaseDate, spotifyUrl, genres, rating, artists, images);
    }
}
