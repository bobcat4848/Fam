package tech.jacobc.fam;

import java.util.UUID;

public class FamilyMember {

    private String ID;
    private static final String raspberryID = "A652M8";

    private String name;
    private String pushTokenId;
    private String familyName;
    private int totalPoints;

    public FamilyMember(String name, String familyName, int totalPoints) {
        this.ID = UUID.randomUUID().toString();
        this.name = name;
        this.familyName = familyName;
        this.totalPoints = totalPoints;
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

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}
