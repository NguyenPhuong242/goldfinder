package com.example.goldfinder.utils;

public enum items {
    GOLD("GOLD"), 
    WALL("WALL"), 
    EMPTY("EMPTY");

    private final String name;

    items(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}