package oop.project;

public class Ticket {
    private Flight flight;
    private int seat;
    private int flightId;
    int id;

    public Ticket(int flight, int seat) {
        MySqlCon.insertIntoDB("INSERT INTO ticket (ticket_flight_id, ticket_flight_seat) VALUES ('" +flight+"', '"+seat+"');");
        this.seat = seat;
        this.flightId = flight;
        //something with flight too
    }
    public Ticket(int id, int flight, int seat){
        this.seat = seat;
        this.id = id;
        this.flightId = flight;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }
}
