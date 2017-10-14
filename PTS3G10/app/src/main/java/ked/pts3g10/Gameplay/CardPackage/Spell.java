package ked.pts3g10.Gameplay.CardPackage;


import android.widget.ImageView;

import ked.pts3g10.Gameplay.AbilityPackage.Ability;

public class Spell extends Card {

    private Ability ability;

    public Spell(String name, int ap, int rp, ImageView bg, ImageView thmbn, Ability ability){
        super(name,ap,rp,bg,thmbn);
        this.ability = ability;
    }
}
