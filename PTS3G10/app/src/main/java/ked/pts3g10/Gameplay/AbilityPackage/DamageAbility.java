package ked.pts3g10.Gameplay.AbilityPackage;


import ked.pts3g10.Gameplay.Board;
import ked.pts3g10.Interface.Case;

public class DamageAbility extends Ability {


    public DamageAbility(int id, String name, String description){
        super(id,name,description);
    }

    public void use(Board board, Case base, int radius){}
}
