package oop.project;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;

public class Main{
    public static ArrayList<Passenger> queue1 = new ArrayList<>();
    public static ArrayList<Passenger> queue2 = new ArrayList<>();
    public static ArrayList<Passenger> queue3 = new ArrayList<>();
    public static ArrayList<Passenger> queue4 = new ArrayList<>();
    public static ArrayList<Passenger> queue5 = new ArrayList<>();
    public static ArrayList<Passenger> queue6 = new ArrayList<>();
    public static ArrayList<Passenger> queue7 = new ArrayList<>();
    public static ArrayList<Passenger> queue8 = new ArrayList<>();
    public static ArrayList<Passenger> queue9 = new ArrayList<>();
    public static ArrayList<Passenger> queue10 = new ArrayList<>();

    public static void main(String[] args){
	// write your code here

        MySqlCon.mySqlConnect();
        GUI.launchGUI();
        MySqlCon.mySqlDisconnect();
    }
    public static void addToQueue(Passenger passenger){
        queue1.add(passenger);
    }
}
