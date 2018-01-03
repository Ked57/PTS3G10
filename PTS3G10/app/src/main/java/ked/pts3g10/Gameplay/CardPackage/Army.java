package ked.pts3g10.Gameplay.CardPackage;


import android.util.Log;
import android.widget.ImageView;

public class Army extends BoardCard {

    public Army(int id, String name,String description,int crystalCost, int ap, int rp, int hp, int mp, int bg, int thmbn, boolean adversary){
        super(id, name,description,crystalCost,ap,rp,hp,mp,bg,thmbn,adversary);
    }

    @Override
    public String toString() {
        return "Army{"+super.toString()+"}";
    }

    public Army clone(boolean adversary){
        return new Army(id, name,description,crystalCost,attactPoints,rangePoints, healthPoints, movementPoints, background, thumbnail, adversary);
    }
}
