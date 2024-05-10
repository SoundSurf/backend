package com.api.soundsurf.music.entity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum GenreType {
    ACOUSTIC("acoustic"),
    ALTERNATIVE("alternative"),
    AMBIENT("ambient"),
    ANIME("anime"),
    BLUES("blues"),
    BOSSANOVA("bossanova"),
    CLASSICAL("classical"),
    COUNTRY("country"),
    DANCE("dance"),
    DISCO("disco"),
    ELECTRONIC("electronic"),
    EDM("edm"),
    EMO("emo"),
    FOLK("folk"),
    FUNK("funk"),
    GARAGE("garage"),
    GOSPEL("gospel"),
    HIP_HOP("hip-hop"),
    HOUSE("house"),
    INDUSTRIAL("industrial"),
    JAZZ("jazz"),
    METAL("metal"),
    NEW_AGE("new-age"),
    NEW_RELEASE("new-release"),
    PIANO("piano"),
    POP("pop"),
    PUNK("punk"),
    R_N_B("r-n-b"),
    LATIN("latin"),
    ROCK("rock"),
    SINGER_SONGWRITER("singer-songwriter"),
    SOUNDTRACKS("soundtracks"),
    TECHNO("techno"),
    K_POP("k-pop"),
    INDIE("indie");


    private final String value;

    GenreType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static List<GenreType> getRandomGenres(int count) {
        List<GenreType> shuffledGenres = Stream.of(GenreType.values())
                .collect(Collectors.toList());
        Collections.shuffle(shuffledGenres);
        return shuffledGenres.stream().limit(count).collect(Collectors.toList());
    }
}
