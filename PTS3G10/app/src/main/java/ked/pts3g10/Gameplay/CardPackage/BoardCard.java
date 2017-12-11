package ked.pts3g10.Gameplay.CardPackage;


import android.util.Log;
import android.widget.ImageView;

public abstract class BoardCard extends Card {
    protected int healthPoints;
    protected int movementPoints;

    public BoardCard(String name,String description,int crystalCost, int ap, int rp, int hp, int mp, ImageView bg, ImageView thmbn, boolean adversary){
        super(name,description,crystalCost,ap,rp,bg,thmbn, adversary);
        healthPoints = hp;
        movementPoints = mp;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) { this.healthPoints = healthPoints;}

    public int getMovementPoints() {
        return movementPoints;
    }

    @Override
    public String toString() {
        return super.toString()+ "healthPoints=" + healthPoints +
                ", movementPoints=" + movementPoints;
    }
}
