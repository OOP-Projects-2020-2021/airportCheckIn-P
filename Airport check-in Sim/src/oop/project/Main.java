package oop.project;

import oop.project.database.MySqlCon;
import oop.project.gui.GUI;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Main{
    public static ArrayList<Queue> queue = new ArrayList<>();

    public static void main(String[] args){

        MySqlCon.mySqlConnect();
        Main.declareQueue();
        GUI.launchGUI();
        MySqlCon.mySqlDisconnect();
    }
    private static void declareQueue(){
        for (int i=0; i<=10; i++) {
            Queue<Passenger> tempQ = new ArrayDeque<>();
            queue.add(tempQ);
        }
    }
    private static void clearQueue(){
        for (int i=1; i<=10; i++){
            queue.get(i).clear();
        }
    }
    public static void initQueue(){

        clearQueue();
        ResultSet rs = MySqlCon.Query("SELECT * FROM passenger JOIN identity_card on passenger.passenger_identityCard = identity_card.id_id JOIN ticket ON passenger.passenger_ticket_id = ticket.ticket_id JOIN luggage ON passenger.passenger_luggage_id = luggage.luggage_id JOIN flight ON ticket.ticket_flight_id = flight.flight_id");
        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                int index = rs.getInt("ticket_flight_id");
                Passenger passenger = new Passenger(rs.getInt("passenger_id"), rs.getInt("identity_card.id_id"), rs.getInt("ticket.ticket_id"),
                        rs.getInt("luggage.luggage_id"), rs.getString("id_first_name"), rs.getString("id_last_name"),
                        rs.getDate("id_birth_date"), rs.getString("id_address"),
                        rs.getString("id_citizenship"), rs.getInt("luggage_weight"), rs.getInt("flight_id"),
                        rs.getInt("ticket_flight_seat"), rs.getInt("ticket_flight_id"));
                Main.addToQueue(passenger, index);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public static void addToQueue(Passenger passenger, int index){
        queue.get(index).add(passenger);
    }
}
