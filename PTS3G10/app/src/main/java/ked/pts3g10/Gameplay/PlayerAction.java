package ked.pts3g10.Gameplay;

import android.content.Context;
import android.widget.Toast;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.GameActivity;
import ked.pts3g10.Gameplay.CardPackage.BoardCard;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.Gameplay.CardPackage.Spell;
import ked.pts3g10.Interface.Case;
import ked.pts3g10.R;


public class PlayerAction {

    private Player player;
    private int actionState;
    private BoardCard caseCard;
    private Case movingFrom;

    public PlayerAction(Player player) {

        this.player = player;
        actionState = 0; // 0 rien; 1 choosing initial case; 2 moving
        caseCard = null;
        movingFrom = null;
    }

    public void placeBoardCard(GameActivity context, BoardCard card, Case new_case) {
        new_case.setCard(context, card);
        player.setCrystals(player.getCrystals()-card.getCrystalCost());
        player.getDeck().getCardList().remove(card);
        ActivityMgr.gameActivity.getBoard().updateTexts();
        resetActionState();
        card.setHasMovedThisRound(true);
    }

    public void moveCard(GameActivity context, BoardCard card, Case new_case) {
        new_case.setCard(context,card);
        movingFrom.resetCard();
        caseCard = null;
        ActivityMgr.gameActivity.getBoard().clearBoardActions();
        resetActionState();
        card.setHasMovedThisRound(true);
    }

    public void moveCard(GameActivity context, BoardCard card,Case movingFrom, Case new_case) {
        new_case.setCard(context,card);
        movingFrom.resetCard();
        caseCard = null;
        ActivityMgr.gameActivity.getBoard().clearBoardActions();
        resetActionState();
        card.setHasMovedThisRound(true);
    }

    public void useSpellCard(Spell spell, Case new_case) {

    }

    public void attack(Case attack_case) {
        int ap = getCaseCard().getAttactPoints();
        int eniHp = attack_case.getCard().getHealthPoints();

        // Notifier le serveur
        // Lancer l'animation

        eniHp -= ap;

        if(eniHp <= 0){
            attack_case.resetCard();
        }else {
            attack_case.getCard().setHealthPoints(eniHp);
            attack_case.updateHp(eniHp);
        }

        resetActionState();
        caseCard.setHasMovedThisRound(true);
    }


    public void chooseCaseToGoTo(Case base){
        Board board = ActivityMgr.gameActivity.getBoard();
            actionState = 2;
            movingFrom = base;
            caseCard = movingFrom.getCard();
            for (Case c : board.getCases()) {
                if (base.getXDistanceWith(c) <= base.getCard().getMovementPoints() && base.getYDistanceWith(c) <= base.getCard().getMovementPoints()) {
                    if (c.isCardThumbnailEmpty())
                        c.setCaseActionable(R.color.colorGreen);
                    else if(c.isPlayersCastle()){
                        c.setCaseNonActionable();
                    }else if(c.isAdversaryCastle()){
                        c.setCaseActionable(R.color.colorBlue);
                    }
                    else if(c.getCard().isAdversary()){
                        c.setCaseActionable(R.color.colorBlue);
                    }
                    else c.setCaseNonActionable();
                }
            }
    }

    public void resetActionState(){
        actionState = 0;
        ActivityMgr.gameActivity.getBoard().clearBoardActions();
    }
    public int getActionState() {return actionState;}

    public void chooseInitialCase(BoardCard card){
        Board board = ActivityMgr.gameActivity.getBoard();
        actionState = 1;
        caseCard = card;

        for(int i = 0; i < 5; ++i){
            Case c = board.getCaseWithLinearLayoutNumber(i,4);
            if(c.isCardThumbnailEmpty()){
                c.setCaseActionable(R.color.colorGreen);
            }else c.setCaseActionable(R.color.colorRed);
        }
    }

    public BoardCard getCaseCard() {
        return caseCard;
    }
}
