package oop.project;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Main{
    public static LinkedList<Queue<Passenger>> queue = new LinkedList<>();

    public static void main(String[] args){
	// write your code here
        Main.initQueue();
        MySqlCon.mySqlConnect();
        GUI.launchGUI();
        MySqlCon.mySqlDisconnect();
    }
    private static void initQueue(){
        for (int i=0; i<=10; i++){
            queue.add(new LinkedList<>());
        }
    }
    public static void addToQueue(Passenger passenger, int index){
        queue.get(index).add(passenger);
    }
}
