package oop.project;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Main{
    public static Queue<Passenger> queue1 = new ArrayDeque<>();
    public static Queue<Passenger> queue2 = new ArrayDeque<>();
    public static Queue<Passenger> queue3 = new ArrayDeque<>();
    public static Queue<Passenger> queue4 = new ArrayDeque<>();
    public static Queue<Passenger> queue5 = new ArrayDeque<>();
    public static Queue<Passenger> queue6 = new ArrayDeque<>();
    public static Queue<Passenger> queue7 = new ArrayDeque<>();
    public static Queue<Passenger> queue8 = new ArrayDeque<>();
    public static Queue<Passenger> queue9 = new ArrayDeque<>();
    public static Queue<Passenger> queue10 = new ArrayDeque<>();

    public static void main(String[] args){
	// write your code here

        MySqlCon.mySqlConnect();
        Main.initQueue();
        GUI.launchGUI();
        MySqlCon.mySqlDisconnect();
    }
    private static void initQueue(){

        ResultSet rs = MySqlCon.Query("SELECT * FROM passenger JOIN identity_card on passenger.passenger_identityCard = identity_card.id_id JOIN ticket ON passenger.passenger_ticket_id = ticket.ticket_id JOIN luggage ON passenger.passenger_luggage_id = luggage.luggage_id JOIN flight ON ticket.ticket_flight_id = flight.flight_id");
        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                int index = rs.getInt("passenger_queue");
                Passenger passenger = new Passenger(rs.getInt("passenger_id"), rs.getString("id_first_name"), rs.getString("id_last_name"),
                        rs.getDate("id_birth_date"), rs.getString("id_address"),
                        rs.getString("id_citizenship"), rs.getInt("luggage_weight"), rs.getInt("flight_id"),
                        rs.getInt("ticket_flight_seat"), rs.getInt("passenger_queue"));
                Main.addToQueue(passenger, index);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public static void addToQueue(Passenger passenger, int index){
        if (index==1){
            queue1.add(passenger);
        }
        if (index==2){
            queue3.add(passenger);
        }
        if (index==3){
            queue2.add(passenger);
        }
        if (index==4){
            queue4.add(passenger);
        }
        if (index==5){
            queue5.add(passenger);
        }
        if (index==6){
            queue6.add(passenger);
        }
        if (index==7){
            queue7.add(passenger);
        }
        if (index==8){
            queue8.add(passenger);
        }
        if (index==9){
            queue9.add(passenger);
        }
        if (index==10){
            queue10.add(passenger);
        }
    }
}
