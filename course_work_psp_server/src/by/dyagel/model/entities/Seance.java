package by.dyagel.model.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

public class Seance implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;

    private int id;
    private Date date;
    private Time time;
    private Specialist specialist;

    public Seance() {
    }

    public Seance(int id, Date date, Time time, Specialist specialist) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.specialist = specialist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        java.util.Date dateRepresentation = cal.getTime();
        this.date = new Date(dateRepresentation.getTime());
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setTime(int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        this.time = new Time(cal.getTime().getTime());
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    @Override
    public String toString() {
        return "Seance{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", specialist=" + specialist +
                '}';
    }
}
