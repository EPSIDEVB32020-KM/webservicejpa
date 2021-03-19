package com.example.webservicejpa;

import javax.persistence.Entity;

@Entity
public class Car extends Vehicule{
    int numberofseats;

    public Car() {

    }
    public Car(int numberofseats){
        this.numberofseats = numberofseats;
    }
    public int getNumberofseats() {
        return numberofseats;
    }

    public void setNumberofseats(int numberofseats) {
        this.numberofseats = numberofseats;
    }
}
