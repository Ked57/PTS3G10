package ked.pts3g10.Gameplay;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.Gameplay.CardPackage.BoardCard;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.Gameplay.CardPackage.Spell;
import ked.pts3g10.Interface.Case;
import ked.pts3g10.R;


public class PlayerAction {

    private Player player;

    public PlayerAction(Player player) {
        this.player = player;
    }

    public void placeBoardCard(BoardCard card, Case new_case) {

    }

    public void moveCard(Case actual_case, Case new_case) {

    }

    public void useSpellCard(Spell spell, Case new_case) {

    }

    public void attack(Case actual_case, Case attack_case) {

    }

    public void chooseInitialCase(Card card){
        Board board = ActivityMgr.gameActivity.getBoard();

        for(int i = 0; i < 5; ++i){
            Case c = board.getCaseWithLinearLayoutNumber(i,4);
            if(c.isCardThumbnailEmpty()){
                c.setBackgroundResource(R.color.colorGreen);
            }else c.setBackgroundResource(R.color.colorRed);
        }
    }
}
