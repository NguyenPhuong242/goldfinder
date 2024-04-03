package com.example.goldfinder.utils;

public enum dir {
    UP("UP"),
    DOWN("DOWN"),
    LEFT("LEFT"),
    RIGHT("RIGHT");

    private final String name;

    dir(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
