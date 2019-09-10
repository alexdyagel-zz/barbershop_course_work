package by.dyagel.model.entities;

import java.io.Serializable;

public class Order implements Serializable{
    private static final long serialVersionUID = 6529685098267757790L;

    private int id;
    private Service service;
    private Seance seance;
    private User user;

    public Order() {
    }

    public Order(int id, Service service, Seance seance, User user) {
        this.id = id;
        this.service = service;
        this.seance = seance;
        this.user = user;
    }

    public Order(Service service, Seance seance, User user) {
        this.service = service;
        this.seance = seance;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSpecialist(){
        return seance.getSpecialist().toString();
    }

    public String getDate(){
        return seance.getDate().toString();
    }

    public String getTime(){
        return seance.getTime().toString();
    }

    public String getFIO(){
        return user.getFIO();
    }

    public String getEmail(){
        return user.getEmail();
    }

    public String getPhoneNumber(){
        return user.getPhoneNumber();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", service=" + service +
                ", seance=" + seance +
                ", user=" + user +
                '}';
    }
}
