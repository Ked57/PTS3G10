package ked.pts3g10.Gameplay.CardPackage;


import android.widget.ImageView;

import ked.pts3g10.Gameplay.AbilityPackage.Ability;

public class Spell extends Card {

    private Ability ability;

    public Spell(int id, String name,String description,int crystalCost,int ap, int rp, int bg, int thmbn, Ability ability, boolean adversary){
        super(id, name,description,crystalCost,ap,rp,bg,thmbn, adversary);
        this.ability = ability;
    }

    public Ability getAbility() {
        return ability;
    }

    @Override
    public String toString() {
        return "Army{"+super.toString()+","+ability.toString()+"}";
    }

    public Spell clone(boolean adversary){
        return new Spell(id, name,description,crystalCost,attactPoints,rangePoints, background, thumbnail, ability, adversary);
    }
}
