package oop.project;

public class Ticket {
    private Flight flight;
    private int seat;

    public Ticket(int flight, int seat) {
        MySqlCon.insertIntoDB("INSERT INTO ticket (ticket_flight_id, ticket_flight_seat) VALUES ('" +flight+"', '"+seat+"');");
        this.seat = seat;
        //something with flight too
    }
}
