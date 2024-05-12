package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.music.dto.MusicDto;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    private final static String ARTISTS_IN_META = "artists";

    public static List<MusicDto.ArtistSimpleInfo.Musician> convertJsonStringToMusicianDtoList(final String metadata) {
        final var obj = new JSONObject(metadata);
        final var artistArr = obj.getJSONArray(ARTISTS_IN_META);
        final var MusicianList = new ArrayList<MusicDto.ArtistSimpleInfo.Musician>();

        for (var i = 0; i< artistArr.length(); i++) {
            final var nowArtist = artistArr.getJSONObject(i);

            MusicianList.add(new MusicDto.ArtistSimpleInfo.Musician(nowArtist));
        }

        return MusicianList;
    }
}
