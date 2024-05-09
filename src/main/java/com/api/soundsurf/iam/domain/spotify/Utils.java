package com.api.soundsurf.iam.domain.spotify;

import com.api.soundsurf.iam.dto.MusicDto;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

import java.util.List;
import java.util.stream.Stream;

public class Utils {

    public static List<MusicDto.Common.Song> convertToTrackDtoList(final Track[] tracks) {
        return Stream.of(tracks)
                .filter(track -> track.getPreviewUrl() != null)
                .map(MusicDto.Common.Song::new)
                .toList();
    }

    public static List<MusicDto.Common.Song> convertToTrackDtoList(final TrackSimplified[] tracks) {
        return Stream.of(tracks)
                .filter(track -> track.getPreviewUrl() != null)
                .map(MusicDto.Common.Song::new)
                .toList();
    }
    
}
