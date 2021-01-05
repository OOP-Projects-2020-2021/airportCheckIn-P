package oop.project;

public class Luggage {
    private int weight;
    private int luggageId; //must look again
    //private LuggageStatus status;

    public Luggage(int weight)
    {
        MySqlCon.insertIntoDB("INSERT INTO luggage (luggage_weight) VALUES ('" +weight+"');");
        this.weight = weight;
    }
    public Luggage(int luggageId, int weight){
        this.weight = weight;
        this.luggageId = luggageId;
    }

    public int getWeight()
    {
        return this.weight;
    }
    public int getLuggageId()
    {
        return luggageId;
    }
    /*public LuggageStatus getLuggageStatus()
    {
        return status;
    }*/
}
