package com.citybus.model;

public class Stop {
    private String name;
    private double[] coords;

    public Stop() {
    }

    public Stop(String name, double[] coords) {
        this.name = name;
        this.coords = coords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getCoords() {
        return coords;
    }

    public void setCoords(double[] coords) {
        this.coords = coords;
    }
}