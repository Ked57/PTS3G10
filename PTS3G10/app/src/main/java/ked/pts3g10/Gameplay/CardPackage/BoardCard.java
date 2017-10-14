package ked.pts3g10.Gameplay.CardPackage;


import android.widget.ImageView;

public abstract class BoardCard extends Card {
    protected int healthPoints;
    protected int movementPoints;

    public BoardCard(String name, int ap, int rp, int hp, int mp, ImageView bg, ImageView thmbn){
        super(name,ap,rp,bg,thmbn);
        healthPoints = hp;
        movementPoints = mp;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getMovementPoints() {
        return movementPoints;
    }
}
