package com.citybus.model;

public class BusLocation {
    private String routeId;
    private double[] coords;
    private String nextStop;
    private String source;

    // Constructors
    public BusLocation() {
    }

    public BusLocation(String routeId, double[] coords, String nextStop, String source) {
        this.routeId = routeId;
        this.coords = coords;
        this.nextStop = nextStop;
        this.source = source;
    }

    // Getters and setters
    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public double[] getCoords() {
        return coords;
    }

    public void setCoords(double[] coords) {
        this.coords = coords;
    }

    public String getNextStop() {
        return nextStop;
    }

    public void setNextStop(String nextStop) {
        this.nextStop = nextStop;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}