package ked.pts3g10.Gameplay.AbilityPackage;


import android.util.Log;

import ked.pts3g10.Gameplay.Board;
import ked.pts3g10.Interface.Case;
import ked.pts3g10.Util.Pos;

public class DamageAbility extends Ability {


    public DamageAbility(int id, String name, String description){
        super(id,name,description);
    }

    public void use(Board board, Case base, int radius){
        Pos p = base.getPos();
        for(Case c : board.getCases()){
            if(c.getXDistanceWith(base) <= radius && c.getYDistanceWith(base) <= radius){
                Log.i("Spell","Case: ("+c.getPos().getPosX()+","+c.getPos().getPosY()+") -> Xdistance with base is "+c.getXDistanceWith(base)+"; y distance with base is "+c.getYDistanceWith(base)+"; radius is "+radius);
                if(!c.isCardThumbnailEmpty()){
                    if(c.getCard().isAdversary()){
                        board.getPlayer().getPlayerAction().attack(c);
                    }
                }
            }
        }
    }
}
