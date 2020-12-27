package oop.project;

import java.sql.SQLException;
import java.util.Date;

public class Passenger {
    private Ticket ticket;
    private IdentityCard identityCard; //think about it
    private Luggage luggage;
    private PassengerStatus status;

    public Passenger(String firstName, String lastName, String address,  String citizenship, int luggageWeight, int flight, int seat)  {
        this.ticket = new Ticket(flight, seat);
        this.identityCard = new IdentityCard(firstName, lastName, address,  citizenship);
        this.luggage = new Luggage(luggageWeight);
        this.status = PassengerStatus.IN_QUEUE;

        //MySqlCon.Query();
        int id = MySqlCon.Query();
        int luggage = MySqlCon.getLastIndex("luggage_id", "SELECT * FROM luggage ORDER BY luggage_id DESC LIMIT 1");
        int ticket = MySqlCon.getLastIndex("ticket_id", "SELECT * FROM ticket ORDER BY ticket_id DESC LIMIT 1");
        MySqlCon.insertIntoDB("INSERT INTO passenger (passenger_identityCard, passenger_luggage_id, passenger_ticket_id) VALUES ('" +id+"','"+luggage+"','"+ticket+"');");
    }




    public void setTicket(){

    }
    public Ticket getTicket(){
        return this.ticket;
    }
    public void weightLuggage(){

    }
    public void attachIdTag(){

    }
    public void gotoWaitingRoom(){

    }
    public void setIdentityCard(){
    }      //maybe replace with a constructor

    public IdentityCard getIdentityCard(){
        return identityCard;
    }
    public void setLuggage()
    {

    }
    public Luggage getLuggage()
    {
        return luggage;
    }
    public void moveThroughGate()
    {

    }

}

