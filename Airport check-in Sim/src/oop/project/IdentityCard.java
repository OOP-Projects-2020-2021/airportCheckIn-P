package oop.project;

import oop.project.database.MySqlCon;
import java.sql.Date;

public class IdentityCard {
    private String firstName;
    private String lastName;
    private String address;
    private Date birthDate;
    private String citizenship;
    private int id;

    public IdentityCard(String firstName, String lastName, Date birthDate, String address,  String citizenship){
        MySqlCon.updateDB("INSERT INTO identity_card (id_first_name, id_last_name, id_address, id_birth_date, id_citizenship) VALUES ('" +firstName+"', '"+lastName+"', '"+address+"', '"+birthDate+"', '"+citizenship+"');");
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.citizenship = citizenship;
    }
    public IdentityCard(int id, String firstName, String lastName, Date birthDate, String address,  String citizenship){
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.citizenship = citizenship;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
