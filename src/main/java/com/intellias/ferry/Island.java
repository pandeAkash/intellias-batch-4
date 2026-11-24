package com.intellias.ferry;

import java.util.List;

public class Island {

    private final String name;

    private final List<Coordinate> borders;

    public Island(String name, List<Coordinate> borders) {
        this.name = name;
        this.borders = borders;
    }

    public String getName() {
        return name;
    }

    public List<Coordinate> getBorders() {
        return borders;
    }
}
