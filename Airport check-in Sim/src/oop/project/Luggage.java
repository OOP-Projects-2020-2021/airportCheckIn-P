package oop.project;

import oop.project.database.MySqlCon;
import java.util.Random;

public class Luggage {
    private final int weight;
    private int luggageId;
    private LuggageStatus status;

    public Luggage(int weight)
    {
        MySqlCon.updateDB("INSERT INTO luggage (luggage_weight) VALUES ('" +weight+"');");
        this.weight = weight;
        int odd = getRandom();
        if (odd == 0){
            this.status = LuggageStatus.NOT_WEIGHTED;
        }else{
            this.status = LuggageStatus.WEIGHTED;
        }
    }
    public Luggage(int luggageId, int weight){
        this.weight = weight;
        this.luggageId = luggageId;
        int odd = getRandom();
        if (odd == 0){
            this.status = LuggageStatus.NOT_WEIGHTED;
        }else{
            this.status = LuggageStatus.WEIGHTED;
        }
    }

    private int getRandom(){
        Random rand = new Random();
        return rand.nextInt(2);
    }
    public int getWeight()
    {
        return this.weight;
    }
    public int getLuggageId()
    {
        return luggageId;
    }

    public LuggageStatus getStatus() {
        return status;
    }

    public void setStatus(LuggageStatus status) {
        this.status = status;
    }
}
