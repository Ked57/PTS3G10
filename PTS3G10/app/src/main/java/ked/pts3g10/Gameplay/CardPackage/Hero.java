package ked.pts3g10.Gameplay.CardPackage;


import android.widget.ImageView;

import ked.pts3g10.Gameplay.AbilityPackage.Ability;

public class Hero extends BoardCard {

    private Ability ability;

    public Hero(int id, String name,String description,int crystalCost, int ap, int rp, int hp, int mp, int bg, int thmbn, Ability ability, boolean adversary){
        super(id, name,description,crystalCost,ap,rp,hp,mp,bg,thmbn, adversary);
        this.ability = ability;
    }

    public Ability getAbility() {
        return ability;
    }

    @Override
    public String toString() {
        return "Hero{"+super.toString()+","+ability.toString()+"}";
    }

    public Hero clone(boolean adversary){
        return new Hero(id, name,description,crystalCost,attactPoints,rangePoints, healthPoints, movementPoints, background, thumbnail,ability, adversary);
    }
}
