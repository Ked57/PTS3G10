package ked.pts3g10.Gameplay.AbilityPackage;

import android.util.Log;

import ked.pts3g10.Gameplay.Board;
import ked.pts3g10.Interface.Case;
import ked.pts3g10.Util.Pos;

/**
 * Une ability qui heal et fait des dégats en même temps
 */

public class HealAndDamageAbility extends Ability {

    public HealAndDamageAbility(int id, String name, String description) {
        super(id, name, description);
    }

    public void use(Board board, Case base, int radius, boolean adversary){
        for(Case c : board.getCases()){
            if(c.getXDistanceWith(base) <= radius && c.getYDistanceWith(base) <= radius){
                if(!c.isCardThumbnailEmpty()){
                    Log.i("SpellAttack","Case: ("+c.getPos().getPosX()+","+c.getPos().getPosY()+") -> Xdistance with base is "+c.getXDistanceWith(base)+"; y distance with base is "+c.getYDistanceWith(base)+"; radius is "+radius);
                    if(adversary){
                        if(c.getCard().isAdversary())
                            c.playHealAnimation();
                        else c.playFireAnimation();
                    }else{
                        if(!c.getCard().isAdversary())
                            board.getPlayer().getPlayerAction().heal(c);
                        else board.getPlayer().getPlayerAction().attack(c);
                    }
                }
            }
        }
    }
}