package com.citybus.model;

import java.util.List;

public class Route {
    private String name;
    private List<double[]> path;
    private List<Stop> stops;

    public Route() {
    }

    public Route(String name, List<double[]> path, List<Stop> stops) {
        this.name = name;
        this.path = path;
        this.stops = stops;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<double[]> getPath() {
        return path;
    }

    public void setPath(List<double[]> path) {
        this.path = path;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }
}