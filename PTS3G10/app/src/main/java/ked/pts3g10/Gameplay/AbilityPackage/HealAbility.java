package ked.pts3g10.Gameplay.AbilityPackage;

import android.util.Log;

import ked.pts3g10.Gameplay.Board;
import ked.pts3g10.Interface.Case;
import ked.pts3g10.Util.Pos;

public class HealAbility extends Ability {

    public HealAbility(int id, String name, String description){
        super(id,name,description);
    }

    public void use(Board board, Case base, int radius, boolean adversary){
        for(Case c : board.getCases()){
            if(c.getXDistanceWith(base) <= radius && c.getYDistanceWith(base) <= radius){
                if(!c.isCardThumbnailEmpty()){
                    Log.i("SpellHeal","Case: ("+c.getPos().getPosX()+","+c.getPos().getPosY()+") -> Xdistance with base is "+c.getXDistanceWith(base)+"; y distance with base is "+c.getYDistanceWith(base)+"; radius is "+radius);
                    if(adversary && c.getCard().isAdversary()){
                        c.playHealAnimation();
                    }else if(!adversary && !c.getCard().isAdversary()){
                        board.getPlayer().getPlayerAction().heal(c);
                    }
                }
            }
        }
    }
}
