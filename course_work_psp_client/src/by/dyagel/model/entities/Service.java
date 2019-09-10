package by.dyagel.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class Service implements Serializable{
    private static final long serialVersionUID = 6429685098267757690L;

    private int id;
    private String category;
    private String name;
    private BigDecimal price;

    public Service(){

    }

    public Service(int id, String category, String name, BigDecimal price) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " " + price + " р.";
    }
}
