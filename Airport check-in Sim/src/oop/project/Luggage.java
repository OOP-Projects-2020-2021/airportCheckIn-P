package oop.project;

import java.util.Random;

public class Luggage {
    private int weight;
    private int luggageId; //must look again
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
