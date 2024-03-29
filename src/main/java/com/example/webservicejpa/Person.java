package com.example.webservicejpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String name;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Collection<Rent> rents = new ArrayList<Rent>();

    public Person(String name) {
        super();
        this.name = name;
    }
    public Person(int id,String name) {
        super();
        this.name = name;
        this.id = id;
    }

    public Person() {

    }

    public Collection<Rent> getRents() {
        return rents;
    }
    public void addRent_Person(Rent rent ){
        this.getRents().add( rent );
        rent.person = this;
    }
    public void setRents(Collection<Rent> rents) {
        this.rents = rents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
