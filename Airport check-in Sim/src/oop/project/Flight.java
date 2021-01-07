package oop.project;

import java.sql.Timestamp;

public class Flight {
    private Integer id;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private String destination;


    public Flight(Integer id, Timestamp departureTime, Timestamp arrivalTime, String destination){
        this.id = id;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.destination = destination;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
