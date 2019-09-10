package by.dyagel.model.entities;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 6429685098367757690L;

    private int id;
    private String FIO;
    private String Email;
    private String password;
    private String phoneNumber;
    private Role role;


    public User() {
        this.FIO = null;
        this.Email = null;
        this.password = null;
        this.phoneNumber = null;
        this.role = null;
    }

    public User(String FIO, String Email, String password, String phoneNumber, Role role) {
        this.FIO = FIO;
        this.Email = Email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public User(int id, String FIO, String Email, String password, String phoneNumber, Role role) {
        this.id = id;
        this.FIO = FIO;
        this.Email = Email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public User(String email, String password) {
        Email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return FIO + " Эл. почта:" + Email + " тел.:" + phoneNumber;
    }
}
