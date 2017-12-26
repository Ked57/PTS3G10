package ked.pts3g10.Gameplay.CardPackage;


import android.widget.ImageView;

import ked.pts3g10.Gameplay.AbilityPackage.Ability;
import ked.pts3g10.Gameplay.AbilityPackage.NormalAbility;

public class Spell extends Card {

    private Ability ability;

    public Spell(String name,String description,int crystalCost, ImageView bg, ImageView thmbn, Ability ability, boolean adversary){
        super(name,description,crystalCost,((NormalAbility)ability).getAmount(),((NormalAbility)ability).getRadius(),bg,thmbn, adversary);
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
        return new Spell(name,description,crystalCost, background, thumbnail, ability, adversary);
    }
}
