package oop.project;

import java.util.PriorityQueue;

public class CheckInGate {
    private PriorityQueue<Passenger> queue = new PriorityQueue<>();

    public void addToQueue(Passenger passenger){
        queue.add(passenger);
    }

    public PriorityQueue<Passenger> getQueue() {
        return queue;
    }

    public void setQueue(PriorityQueue<Passenger> queue) {
        this.queue = queue;
    }
}
