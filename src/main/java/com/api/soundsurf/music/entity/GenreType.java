package com.api.soundsurf.music.entity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO : Genre 기획에 맞게 수정
public enum GenreType {
    ACOUSTIC("acoustic"),
    AFROBEAT("afrobeat"),
    ALT_ROCK("alt-rock"),
    ALTERNATIVE("alternative"),
    AMBIENT("ambient"),
    ANIME("anime"),
    BLACK_METAL("black-metal"),
    BLUEGRASS("bluegrass"),
    BLUES("blues"),
    BOSSANOVA("bossanova"),
    BRAZIL("brazil"),
    BREAKBEAT("breakbeat"),
    BRITISH("british"),
    CANTOPOP("cantopop"),
    CHICAGO_HOUSE("chicago-house"),
    CHILDREN("children"),
    CHILL("chill"),
    CLASSICAL("classical"),
    CLUB("club"),
    COMEDY("comedy"),
    COUNTRY("country"),
    DANCE("dance"),
    DANCEHALL("dancehall"),
    DEATH_METAL("death-metal"),
    DEEP_HOUSE("deep-house"),
    DETROIT_TECHNO("detroit-techno"),
    DISCO("disco"),
    DISNEY("disney"),
    DRUM_AND_BASS("drum-and-bass"),
    DUB("dub"),
    DUBSTEP("dubstep"),
    EDM("edm"),
    ELECTRO("electro"),
    ELECTRONIC("electronic"),
    EMO("emo"),
    FOLK("folk"),
    FORRO("forro"),
    FRENCH("french"),
    FUNK("funk"),
    GARAGE("garage"),
    GERMAN("german"),
    GOSPEL("gospel"),
    GOTH("goth"),
    GRINDCORE("grindcore"),
    GROOVE("groove"),
    GRUNGE("grunge"),
    GUITAR("guitar"),
    HAPPY("happy"),
    HARD_ROCK("hard-rock"),
    HARDCORE("hardcore");

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
