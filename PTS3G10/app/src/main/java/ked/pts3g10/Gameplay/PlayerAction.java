package ked.pts3g10.Gameplay;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.Gameplay.CardPackage.BoardCard;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.Gameplay.CardPackage.Spell;
import ked.pts3g10.Interface.Case;
import ked.pts3g10.R;


public class PlayerAction {

    private Player player;
    private int actionState;
    private Card chooseInitialCaseCard;

    public PlayerAction(Player player) {

        this.player = player;
        actionState = 0; // 0 rien; 1 choosing initial case; 2 moving
        chooseInitialCaseCard = null;
    }

    public void placeBoardCard(Card card, Case new_case) {
        new_case.setCard(card);
        player.setCrystals(player.getCrystals()-card.getCrystalCost());
        resetActionState();
    }

    public void moveCard(Case actual_case, Case new_case) {

    }

    public void useSpellCard(Spell spell, Case new_case) {

    }

    public void attack(Case actual_case, Case attack_case) {

    }

    public void resetActionState(){
        actionState = 0;
        ActivityMgr.gameActivity.getBoard().clearBoardActions();
    }
    public int getActionState() {return actionState;}

    public void chooseInitialCase(Card card){
        Board board = ActivityMgr.gameActivity.getBoard();
        actionState = 1;
        chooseInitialCaseCard = card;

        for(int i = 0; i < 5; ++i){
            Case c = board.getCaseWithLinearLayoutNumber(i,4);
            if(c.isCardThumbnailEmpty()){
                c.setCaseActionable(R.color.colorGreen);
            }else c.setCaseActionable(R.color.colorRed);
        }
    }

    public Card getChooseInitialCaseCard() {
        return chooseInitialCaseCard;
    }
}
