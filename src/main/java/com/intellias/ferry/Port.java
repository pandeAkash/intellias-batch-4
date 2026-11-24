package com.intellias.ferry;

public class Port {

    private final String name;

    private final Coordinate position;

    public Port(String name, Coordinate position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public Coordinate getPosition() {
        return position;
    }
}
