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

    public double gwtWeight()
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
