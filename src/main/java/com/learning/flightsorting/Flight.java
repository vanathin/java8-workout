package com.learning.flightsorting;

public class Flight {

    private Long flightId;

    private String airlineType;

    private String origin;

    private String destination;

    private Integer stop;

    private Double price;

    public Flight(Long flightId, String airlineType, String origin, String destination, Integer stop, Double price) {
        this.flightId = flightId;
        this.airlineType = airlineType;
        this.origin = origin;
        this.destination = destination;
        this.stop = stop;
        this.price = price;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getAirlineType() {
        return airlineType;
    }

    public void setAirlineType(String airlineType) {
        this.airlineType = airlineType;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getStop() {
        return stop;
    }

    public void setStop(Integer stop) {
        this.stop = stop;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
