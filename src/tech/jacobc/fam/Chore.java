package tech.jacobc.fam;

import java.util.UUID;

public class Chore {

    private String ID;
    private static final String raspberryID = "A652M8";

    private String name;
    private String frequency;
    private int points;

    public Chore(String name, String frequency, int points) {
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
