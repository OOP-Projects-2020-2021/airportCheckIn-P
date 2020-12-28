package oop.project;

import java.sql.Date;

public class IdentityCard {
    private String firstName;
    private String lastName;
    private String address;
    private Date birthDate;
    private String citizenship;

    public IdentityCard(String firstName, String lastName, Date birthDate, String address,  String citizenship){
        MySqlCon.insertIntoDB("INSERT INTO identity_card (id_first_name, id_last_name, id_address, id_birth_date, id_citizenship) VALUES ('" +firstName+"', '"+lastName+"', '"+address+"', '"+birthDate+"', '"+citizenship+"');");
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.citizenship = citizenship;
    }
}
