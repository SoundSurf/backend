package com.api.soundsurf.music.constant;

public enum SearchType {
    ARTIST("artist"),
    ALBUM("album"),
    TRACK("track");

    private final String type;

    SearchType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
