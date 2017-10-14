package ked.pts3g10.Gameplay.CardPackage;


import android.widget.ImageView;

import ked.pts3g10.Gameplay.AbilityPackage.Ability;

public class Hero extends BoardCard {

    private Ability ability;

    public Hero(String name, int ap, int rp, int hp, int mp, ImageView bg, ImageView thmbn, Ability ability){
        super(name,ap,rp,hp,mp,bg,thmbn);
        this.ability = ability;
    }
}
