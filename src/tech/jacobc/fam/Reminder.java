package tech.jacobc.fam;

import java.util.UUID;

public class Reminder {

    private String ID;
    private static final String raspberryID = "A652M8";

    private String name;
    private String assignedToId;
    private int priority;
    private int points;
    private int timeStamp;

    public Reminder(String name, int priority, int points, int time) {
        this.ID = UUID.randomUUID().toString();
        this.name = name;
        this.priority = priority;
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

    public String getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(String assignedToId) {
        this.assignedToId = assignedToId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }
}
