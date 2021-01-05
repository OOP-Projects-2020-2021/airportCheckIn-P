package oop.project;

import java.sql.SQLException;
import java.sql.Date;

public class Passenger {
    private Ticket ticket;
    private IdentityCard identityCard; //think about it
    private Luggage luggage;
    private PassengerStatus status;
    private Integer queueNumber;
    private Integer id;

    public Passenger(String firstName, String lastName,Date birthDate, String address, String citizenship, int luggageWeight, int flight, int seat, Integer queueNumber)  {
        this.ticket = new Ticket(flight, seat);
        this.identityCard = new IdentityCard(firstName, lastName, birthDate, address,  citizenship);
        this.luggage = new Luggage(luggageWeight);
        this.status = PassengerStatus.IN_QUEUE;
        this.queueNumber = queueNumber;

        int id = MySqlCon.getLastIndex("id_id", "SELECT * FROM identity_card ORDER BY id_id DESC LIMIT 1");
        int luggage = MySqlCon.getLastIndex("luggage_id", "SELECT * FROM luggage ORDER BY luggage_id DESC LIMIT 1");
        int ticket = MySqlCon.getLastIndex("ticket_id", "SELECT * FROM ticket ORDER BY ticket_id DESC LIMIT 1");
        MySqlCon.updateDB("INSERT INTO passenger (passenger_identityCard, passenger_luggage_id, passenger_ticket_id) VALUES ('" +id+"','"+luggage+"','"+ticket+"');");
        this.id = MySqlCon.getLastIndex("passenger_id", "SELECT * FROM passenger ORDER BY passenger_id DESC LIMIT 1");
    }

    public Passenger(Integer id, int idId, int ticketId, int luggageId, String firstName, String lastName,Date birthDate, String address,
                     String citizenship, int luggageWeight, int flight, int seat, Integer queueNumber)  {
        this.ticket = new Ticket(ticketId, flight, seat);
        this.identityCard = new IdentityCard(idId, firstName, lastName, birthDate, address,  citizenship);
        this.luggage = new Luggage(luggageWeight);
        this.status = PassengerStatus.IN_QUEUE;
        this.queueNumber = queueNumber;
        this.id = id;
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

    public PassengerStatus getStatus() {
        return status;
    }

    public void setStatus(PassengerStatus status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(int queueNumber) {
        this.queueNumber = queueNumber;
    }


}

