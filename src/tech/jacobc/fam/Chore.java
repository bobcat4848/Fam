package tech.jacobc.fam;

import java.io.Serializable;
import java.util.UUID;

public class Chore implements Serializable {

    private String ID;
    private static final String raspberryID = "A652M8";

    private String name;
    private String frequency;
    private long points;

    public Chore() {
        this("No Name", "Daily", 0L);
    }

    public Chore(String name, String frequency, long points) {
        this.ID = UUID.randomUUID().toString();
        this.name = name;
        this.frequency = frequency;
        this.points = points;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String toString() {
        return name + " " + frequency +  " " + points;
    }
}
