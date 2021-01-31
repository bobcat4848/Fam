package tech.jacobc.fam;

import java.util.UUID;

public class FamilyMember {

    private String ID;
    private static final String raspberryID = "A652M8";

    private String name;
    private String pushTokenId;
    private String familyName;
    private long totalPoints;

    public FamilyMember(String name, long totalPoints) {
        this.ID = UUID.randomUUID().toString();
        this.name = name;
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

    public long getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(long totalPoints) {
        this.totalPoints = totalPoints;
    }
}
