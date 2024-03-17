package com.api.soundsurf.api;

public enum BooleanDeleted {
    TRUE(true),
    FALSE(false);

    private boolean b;

    public boolean isTrue() {
        return this.b;
    }

    BooleanDeleted(boolean b) {
        this.b = b;
    }
}
