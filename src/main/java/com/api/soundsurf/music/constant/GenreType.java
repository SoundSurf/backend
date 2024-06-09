package com.api.soundsurf.music.constant;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum GenreType {
    ACOUSTIC("acoustic", 0),
    ALTERNATIVE("alternative", 1),
    AMBIENT("ambient", 2),
    ANIME("anime", 3),
    BLUES("blues", 4),
    BOSSANOVA("bossanova", 5),
    CLASSICAL("classical", 6),
    COUNTRY("country", 7),
    DANCE("dance", 8),
    DISCO("disco", 9),
    ELECTRONIC("electronic", 10),
    EDM("edm", 11),
    EMO("emo", 12),
    FOLK("folk", 13),
    FUNK("funk", 14),
    GARAGE("garage", 15),
    GOSPEL("gospel", 16),
    HIP_HOP("hip-hop", 17),
    HOUSE("house", 18),
    INDUSTRIAL("industrial", 19),
    JAZZ("jazz", 20),
    METAL("metal", 21),
    NEW_AGE("new-age", 22),
    NEW_RELEASE("new-release", 23),
    PIANO("piano", 24),
    POP("pop", 25),
    PUNK("punk", 26),
    R_N_B("r-n-b", 27),
    LATIN("latin", 28),
    ROCK("rock", 29),
    SINGER_SONGWRITER("singer-songwriter", 30),
    SOUNDTRACKS("soundtracks", 31),
    TECHNO("techno", 32),
    K_POP("k-pop", 33),
    INDIE("indie", 34);

    private final String value;
    private final int index;

    GenreType(String value, int index) {
        this.value = value;
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public static List<GenreType> getRandomGenres(int count) {
        List<GenreType> shuffledGenres = Stream.of(GenreType.values())
                .collect(Collectors.toList());
        Collections.shuffle(shuffledGenres);
        return shuffledGenres.stream().limit(count).collect(Collectors.toList());
    }

    public static GenreType getByIndex(int index) {
        for (GenreType genre : GenreType.values()) {
            if (genre.getIndex() == index) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Invalid genre index: " + index);
    }
}
