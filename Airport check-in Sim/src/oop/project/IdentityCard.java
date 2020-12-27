package oop.project;

import java.util.Date;

public class IdentityCard {
    private String firstName;
    private String lastName;
    private String address;
    private Date birthDate;
    private String citizenship;

    public IdentityCard(String firstName, String lastName, String address,  String citizenship){
        MySqlCon.insertIntoDB("INSERT INTO identity_card (id_first_name, id_last_name, id_address, id_citizenship) VALUES ('" +firstName+"', '"+lastName+"', '"+address+"', '"+citizenship+"');");
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.citizenship = citizenship;
    }
}
