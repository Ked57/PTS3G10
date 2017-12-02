package ked.pts3g10.Gameplay.CardPackage;


import android.widget.ImageView;

import ked.pts3g10.Gameplay.AbilityPackage.Ability;

public class Hero extends BoardCard {

    private Ability ability;

    public Hero(String name,String description,int crystalCost, int ap, int rp, int hp, int mp, ImageView bg, ImageView thmbn, Ability ability, boolean adversary){
        super(name,description,crystalCost,ap,rp,hp,mp,bg,thmbn, adversary);
        this.ability = ability;
    }

    public Ability getAbility() {
        return ability;
    }

    @Override
    public String toString() {
        return "Hero{"+super.toString()+","+ability.toString()+"}";
    }
}
