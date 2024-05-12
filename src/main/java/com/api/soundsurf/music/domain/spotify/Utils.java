package com.api.soundsurf.music.domain.spotify;

import com.api.soundsurf.music.dto.MusicDto;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<MusicDto.ArtistSimpleInfo.Musician> convertJsonStringToMusicianDtoList(final String metadata) {
        final var artistArr = new JSONArray(metadata);
        final var MusicianList = new ArrayList<MusicDto.ArtistSimpleInfo.Musician>();

        for (var i = 0; i< artistArr.length(); i++) {
            final var nowArtist = artistArr.getJSONObject(i);

            MusicianList.add(new MusicDto.ArtistSimpleInfo.Musician(nowArtist));
        }

        return MusicianList;
    }
}
