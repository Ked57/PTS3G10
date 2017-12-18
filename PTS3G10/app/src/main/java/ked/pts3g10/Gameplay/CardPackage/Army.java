package ked.pts3g10.Gameplay.CardPackage;


import android.util.Log;
import android.widget.ImageView;

public class Army extends BoardCard {

    public Army(String name,String description,int crystalCost, int ap, int rp, int hp, int mp, ImageView bg, ImageView thmbn, boolean adversary){
        super(name,description,crystalCost,ap,rp,hp,mp,bg,thmbn,adversary);
    }

    @Override
    public String toString() {
        return "Army{"+super.toString()+"}";
    }

    public Army clone(boolean adversary){
        return new Army(name,description,crystalCost,attactPoints,rangePoints, healthPoints, movementPoints, background, thumbnail, adversary);
    }
}
