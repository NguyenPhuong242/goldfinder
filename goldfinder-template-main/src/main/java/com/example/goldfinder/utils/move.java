package com.example.goldfinder.utils;

public enum move {
    VALIDMOVE("VALID_MOVE"),
    INVALIDMOVE("INVALID_MOVE");

    private final String name;

    move(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
