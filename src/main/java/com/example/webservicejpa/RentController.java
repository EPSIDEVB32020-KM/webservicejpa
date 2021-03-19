package com.example.webservicejpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@RestController
public class RentController {
    //retoureve moi CardRepository et donne moi accès
    @Autowired
    private VehiculeRepository vehiculerepo;
    //retoureve moi CardRepository et donne moi accès
    @Autowired
    private PersonRepository persrepo;
    //retoureve moi CardRepository et donne moi accès
    @Autowired
    private RentRepository rentrepo;

    @Autowired
    public RentController(VehiculeRepository vehiculerepo) throws ParseException {
        System.out.println(vehiculerepo);
        this.vehiculerepo = vehiculerepo;
        //Creation de la classe Van fille de classe Vehicule
        Van van1 = new Van(1000);
        //Attribuer les valeurs de la classe Van
        van1.setBrand("fiat");
        van1.setPlateNumber("1124AEGG");
        van1.setPrice(10450);
        //Creation de la classe Rent
        Rent rent1 = new Rent();
        //Creation des 2 variables date
        String s = "22/09/2006";
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = sd.parse(s);
        String st = "23/09/2006";
        SimpleDateFormat sd1 = new SimpleDateFormat("dd/MM/yyyy");
        Date date2 = sd1.parse(st);
        //Set des 2 dates sur la classe Rent Créer
        rent1.setBeginRent(date1);
        rent1.setEndRent(date2);
        //Creation de la classe Personne
        Person per4 = new Person(5,"jojo");
        //Set du Vehicule sur la classe Rent
        rent1.setVehicule(van1);
        //Set du Rent dans la classe Vehicule
        van1.addRent_Vehicule(rent1);
        //Set du Person sur la classe Rent
        rent1.setPerson(per4);
        //Set du Rent sur la classe Person
        per4.addRent_Person(rent1);
        //Ici on créer le vehicule seulement mais grâce aux Cascade ca crée aussi le Rent avec le vehicule
        vehiculerepo.save(van1);
    }
    //Regarder tous les voitures au lancement de l'application la bdd est vide alors tout d'abord créer une bdd à l aide
    //de http://localhost:8088/voitures/create/1122AB/feri/2400 par exemple avant de lancer http://localhost:8088/voitures
    @GetMapping("/rents/car/{platenumber}")
    @ResponseBody
    public Collection<Rent> findAllRentByPlatenumber(@PathVariable (value = "platenumber") String plateNumber) {
        Vehicule it = vehiculerepo.findById(plateNumber).get();
        return it.getRents();
    }
    //Regarder tous les voitures au lancement de l'application la bdd est vide alors tout d'abord créer une bdd à l aide
    //de http://localhost:8088/voitures/create/1122AB/feri/2400 par exemple avant de lancer http://localhost:8088/voitures
    @GetMapping("/rents/person/{id}")
    @ResponseBody
    public Collection<Rent> findAllRentByPersonId(@PathVariable (value = "id") int id) {
        Person it = persrepo.findById(id).get();
        return it.getRents();
    }

    //   {
    //      "id":2,
    //      "beginRent":"31/12/2019",
    //      "endRent":"24/12/2019",
    //      "vehicule":{
    //         "plateNumber":"221EBXC",
    //         "brand":"ferrari",
    //         "price":10300
    //      },
    //      "person":{
    //         "id":3,
    //         "name":"Maryse"
    //      }
    //   }

    //OU

    //   {
    //      "beginRent":"31/12/2019",
    //      "endRent":"24/12/2019"
    //   }

    //http://host.docker.internal:8089/rents/creer/221EBXC/3

    //creer un nouvelle rent a l aide d un fichier json
    @PostMapping(value = "/rents/creer/{platenumber}/{id}")
    @ResponseBody
    public  Vehicule Rents_CreatOrModify(@RequestBody Rent rent,@PathVariable (value = "platenumber") String plateNumber,@PathVariable (value = "id") int id) {
        //Attribution à une classe Vehicule : grace à la recherche de vehiculerepo de findbyId pour recuperer le vehicule par son Id
        Vehicule vehicule = vehiculerepo.findById(plateNumber).get();
        //Attribution à une classe Person : grace à la recherche de persrepo de findbyId pour recuperer la personne par son Id
        Person person = persrepo.findById(id).get();
        //Set du Vehicule sur la classe Rent
        rent.setVehicule(vehicule);
        //Set du Rent dans la classe Vehicule
        vehicule.addRent_Vehicule(rent);
        //Set du Person sur la classe Rent
        rent.setPerson(person);
        //Set du Rent sur la classe Person
        person.addRent_Person(rent);
        //Ici on créer le vehicule seulement mais grâce aux Cascade ca crée aussi le Rent avec le vehicule
        vehiculerepo.save(vehicule);
        if(vehiculerepo != null){
            System.out.println("Rent creer avec succès");
        }
        else{
            System.out.println("Creation impossible!! Erreur!");
        }
        return vehiculerepo.save(vehicule);
    }
    //   {
    //      "id":6
    //   }

    //http://host.docker.internal:8089/rents/supprimer

    //supprimer une rent a l aide d un fichier jsonn
    @DeleteMapping(value = "/rents/supprimer")
    @ResponseBody
    public  void  Person_Delete(@RequestBody Rent rent_created) {
        if(rentrepo != null){
            rentrepo.deleteById(rent_created.getId());
            System.out.println("Rent supprimer avec succès");
        }
        else{
            System.out.println("Suppresion impossible!! Erreur!");
        }
    }
}
