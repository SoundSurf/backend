package com.api.soundsurf.music.domain.recommendation;

import com.api.soundsurf.music.entity.UserRecommendationMusic;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.ArrayList;
import java.util.Arrays;


@Service
@RequiredArgsConstructor
public class UserRecommendationMusicBusinessService {
    private final UserRecommendationMusicService service;

    public void save(final Track[] data, final Long lastOrder, final Long userId) {
        for (var i = 0; i < data.length; i++) {

           final var  jsonMusicians = new ArrayList<JSONObject>();

           Arrays.stream(data[i].getArtists()).forEach(e -> {
               hydrateJsonMusicians(e, jsonMusicians);
           });

            final var artistsMetadata = musicianJsonToString(jsonMusicians);

            final var music = new UserRecommendationMusic(data[i], lastOrder + i, userId, artistsMetadata);
            service.save(music);
        }
    }

    public void listenAndDelete(final Long userId, final  Long id) {
        final var userRecommendationMusic = service.get(userId, id);
        userRecommendationMusic.delete();

        service.save(userRecommendationMusic);
    }

    private void hydrateJsonMusicians(final ArtistSimplified musician, final ArrayList<JSONObject> jsonMusicians) {
        final var jsonMusician = new JSONObject();

        jsonMusician.put("artistName", musician.getName());
        jsonMusician.put("id", musician.getId());
        jsonMusician.put("spotifyUrl", musician.getExternalUrls().getExternalUrls().get("spotify"));
        jsonMusicians.add(jsonMusician);
    }

    private String musicianJsonToString(final ArrayList<JSONObject> jsonMusicians) {
        final var artistsMetadata = new StringBuilder();

        for (var jsonMusician : jsonMusicians) {
            artistsMetadata.append(jsonMusician.toString());
            artistsMetadata.append(",");
        }

        if (artistsMetadata.length() > 0) {
            artistsMetadata.setLength(artistsMetadata.length() - 1);
        }

        return artistsMetadata.toString();
    }

}
