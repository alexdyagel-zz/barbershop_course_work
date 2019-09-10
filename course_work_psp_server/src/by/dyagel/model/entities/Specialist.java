package by.dyagel.model.entities;

import java.io.Serializable;

public class Specialist implements Serializable {
    private static final long serialVersionUID = 6529685098267757691L;

    private int id;
    private String position;
    private String name;

    public Specialist() {
    }

    public Specialist(int id, String position, String name) {
        this.id = id;
        this.position = position;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return position + " " + name;
    }
}
