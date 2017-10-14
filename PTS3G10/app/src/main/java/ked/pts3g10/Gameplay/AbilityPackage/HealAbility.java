package ked.pts3g10.Gameplay.AbilityPackage;

import ked.pts3g10.Interface.Case;

public class HealAbility extends NormalAbility {

    public HealAbility(int id, String name, String description, int radius, int amount, Case target){
        super(id,name,description,radius, amount, target);
    }
}
