package com.example.webservicejpa;

import javax.persistence.Entity;

@Entity
public class Van  extends Vehicule{
    float maxweight;

    public Van(float maxweight) {
        super();
        this.maxweight = maxweight;
    }

    public Van() {

    }
    public float getMaxweight() {
        return maxweight;
    }

    public void setMaxweight(float maxweight) {
        this.maxweight = maxweight;
    }
}
