package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.music.dto.MusicDto;
import org.json.JSONArray;
import org.json.JSONObject;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;

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
        String albumType = albumJson.getString("albumType");

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

        return new MusicDto.AlbumSimpleInfo.Info(albumName, id, releaseDate, spotifyUrl, genres, rating, albumType, artists, images);
    }

    public static String albumDtoToString(final MusicDto.AlbumSimpleInfo.Info album, final String genre, final String rating) {
        JSONObject albumJson = new JSONObject();
        albumJson.put("albumName", album.albumName());
        albumJson.put("id", album.id());
        albumJson.put("releaseDate", album.releaseDate());
        albumJson.put("spotifyUrl", album.spotifyUrl());
        albumJson.put("albumType", album.albumType());
        albumJson.put("genres", genre);
        albumJson.put("rating", rating);

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

    static public String musicianJsonToString(final JSONArray jsonMusicians) {
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

    public static void hydrateJsonMusicians(final ArtistSimplified musician, final JSONArray jsonMusicians) {
        final var jsonMusician = new JSONObject();

        jsonMusician.put("artistName", musician.getName());
        jsonMusician.put("id", musician.getId());
        jsonMusician.put("spotifyUrl", musician.getExternalUrls().getExternalUrls().get("spotify"));
        jsonMusicians.put(jsonMusician);
    }

    public static void hydrateJsonMusicians(final MusicDto.ArtistSimpleInfo.Musician musician, final JSONArray jsonMusicians) {
        final var jsonMusician = new JSONObject();

        jsonMusician.put("artistName", musician.artistName());
        jsonMusician.put("id", musician.id());
        jsonMusician.put("spotifyUrl", musician.spotifyUrl());
        jsonMusicians.put(jsonMusician);
    }

}
