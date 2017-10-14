package ked.pts3g10.Gameplay.CardPackage;


import android.widget.ImageView;

import ked.pts3g10.Gameplay.AbilityPackage.Ability;

public class Spell extends Card {

    private Ability ability;

    public Spell(String name,String description,int crystalCost, int ap, int rp, ImageView bg, ImageView thmbn, Ability ability){
        super(name,description,crystalCost,ap,rp,bg,thmbn);
        this.ability = ability;
    }
    @Override
    public String toString() {
        return "Army{"+super.toString()+","+ability.toString()+"}";
    }
}
