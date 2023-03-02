package fr.univcotedazur.simpletcfs.entities;



public class Gift {

    public int pointsNeeded;

    public String description;

    public AccountStatus RequiredStatus;

    public int getPointsNeeded() {
        return pointsNeeded;
    }

    public void setPointsNeeded(int pointsNeeded) {
        this.pointsNeeded = pointsNeeded;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccountStatus getRequiredStatus() {
        return RequiredStatus;
    }

    public void setRequiredStatus(AccountStatus requiredStatus) {
        RequiredStatus = requiredStatus;
    }
}

