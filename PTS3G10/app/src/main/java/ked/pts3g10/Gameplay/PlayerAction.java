package ked.pts3g10.Gameplay;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.GameActivity;
import ked.pts3g10.Gameplay.AbilityPackage.DamageAbility;
import ked.pts3g10.Gameplay.CardPackage.BoardCard;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.Gameplay.CardPackage.Spell;
import ked.pts3g10.Interface.Case;
import ked.pts3g10.Network.packet.PacketSendMovement;
import ked.pts3g10.Network.packet.PacketSendSpell;
import ked.pts3g10.Network.packet.PacketUpdateHP;
import ked.pts3g10.R;
import ked.pts3g10.Util.Pos;


public class PlayerAction {

    private Player player;
    private int actionState;
    private Card caseCard;
    private Case movingFrom;

    private BoardCard toBePlaced;
    private Case caseToPlaceIt;
    private boolean placeBoardCardNext;

    private Case caseItComesFrom;
    private Case caseItsGoingTo;
    private boolean moveBoardCardNext;

    private ArrayList<Case> caseToUpdateHp;
    private boolean updateHp;

    private boolean resetCard;
    private ArrayList<Case> caseToResetCard;

    private boolean useSpell;
    private Case caseToUseSpellOn;

    public PlayerAction(Player player) {

        this.player = player;
        actionState = 0; // 0 rien; 1 choosing initial case; 2 moving
        caseCard = null;
        movingFrom = null;

        toBePlaced = null;
        caseToPlaceIt = null;
        placeBoardCardNext = false;

        caseItComesFrom = null;
        caseItsGoingTo = null;
        moveBoardCardNext = false;

        caseToUpdateHp = new ArrayList<>();
        caseToResetCard = new ArrayList<>();
        updateHp = false;

        resetCard = false;

        useSpell = false;
        caseToUseSpellOn = null;
    }

    public boolean isMoveBoardCardNext() {
        return moveBoardCardNext;
    }

    public void setCaseItComesFrom(Case caseItComesFrom) {
        this.caseItComesFrom = caseItComesFrom;
    }

    public void setCaseItsGoingTo(Case caseItsGoingTo) {
        this.caseItsGoingTo = caseItsGoingTo;
    }

    public void setMoveBoardCardNext(boolean moveBoardCardNext) {
        this.moveBoardCardNext = moveBoardCardNext;
    }

    public void setCaseToPlaceIt(Case caseToPlaceIt){
        this.caseToPlaceIt = caseToPlaceIt;
    }

    public void setToBePlaced(BoardCard toBePlaced){
        this.toBePlaced = toBePlaced;
    }

    public void setPlaceBoardCardNext(boolean placeBoardCardNext){
        this.placeBoardCardNext = placeBoardCardNext;
    }

    public boolean isPlaceBoardCardNext(){return placeBoardCardNext;}

    public void placeBoardCardNext(GameActivity context){
        placeBoardCard(context,toBePlaced, caseToPlaceIt);
        placeBoardCardNext = false;
    }

    public void moveBoardCardNext(GameActivity context){
        moveCard(context,caseItComesFrom,caseItsGoingTo);
        moveBoardCardNext = false;
    }

    public void placeBoardCard(GameActivity context, BoardCard card, Case new_case) {
        new_case.setCard(context, card);
        player.setCrystals(player.getCrystals()-card.getCrystalCost());
        player.getDeck().getCardList().remove(card);
        ActivityMgr.gameActivity.getBoard().updateTexts();
        resetActionState();
        card.setHasMovedThisRound(true);
    }
    //Appelé quand c'est le joueur qui bouge une carte
    public void moveCard(GameActivity context, BoardCard card, Case new_case) {
        Pos basePos = movingFrom.getPos();
        Pos newPos = new_case.getPos();
        new PacketSendMovement().call(ConnectionActivity.token,basePos.getPosX(),basePos.getPosY(),newPos.getPosX(),newPos.getPosY());
        new_case.setCard(context,card);
        movingFrom.resetCard();
        caseCard = null;
        ActivityMgr.gameActivity.getBoard().clearBoardActions();
        resetActionState();
        card.setHasMovedThisRound(true);
    }
    //Appelé quand c'est l'adversaire
    public void moveCard(GameActivity context,Case movingFrom, Case new_case) {
        BoardCard card = movingFrom.getCard();
        new_case.setCard(context,card);
        movingFrom.resetCard();
        caseCard = null;
        ActivityMgr.gameActivity.getBoard().clearBoardActions();
        resetActionState();
        card.setHasMovedThisRound(true);
    }

    public void useSpellCard(Case new_case) {
        Log.i("Spell","Using spell");
        if(caseCard instanceof Spell){
            ((Spell)caseCard).getAbility().use(ActivityMgr.gameActivity.getBoard(),new_case, caseCard.getRangePoints(),player.isAdversary());
            player.setCrystals(player.getCrystals()-caseCard.getCrystalCost());
            player.getDeck().getCardList().remove(caseCard);
            caseCard = null;
            resetActionState();
            ActivityMgr.gameActivity.getBoard().updateTexts();
        }
    }

    public void attack(Case attack_case) {
        int ap = getCaseCard().getAttactPoints();
        int eniHp = attack_case.getCard().getHealthPoints();

        // Notifier le serveur
        // Lancer l'animation

        eniHp -= ap;

        new PacketUpdateHP().call(ConnectionActivity.token,eniHp,attack_case.getPos());

        if(eniHp <= 0){
            attack_case.resetCard();
        }else {
            attack_case.getCard().setHealthPoints(eniHp);
            attack_case.updateHp(eniHp);
        }
        attack_case.playFireAnimation(); // TODO: différencier les spells des attaques normales
        resetActionState();
        caseCard.setHasMovedThisRound(true);
    }

    public void heal(Case heal_case){
        if(caseCard != null) {
            heal_case.playHealAnimation();
            int ap = getCaseCard().getAttactPoints();
            int hp = heal_case.getCard().getHealthPoints();
            heal_case.updateHp(hp + ap);
            new PacketUpdateHP().call(ConnectionActivity.token,hp+ap,heal_case.getPos());
            resetActionState();
            caseCard.setHasMovedThisRound(true);
        }
    }


    public void chooseCaseToGoTo(Case base){
        Board board = ActivityMgr.gameActivity.getBoard();
            actionState = 2;
            movingFrom = base;
            caseCard = movingFrom.getCard();
            for (Case c : board.getCases()) {
                if (base.getXDistanceWith(c) <= base.getCard().getMovementPoints() && base.getYDistanceWith(c) <= base.getCard().getMovementPoints()) {
                    if (c.isCardThumbnailEmpty()) {
                        c.setCaseActionable(R.color.colorGreen);
                    }else if(c.isPlayersCastle()){
                        c.setCaseNonActionable();
                    }else if(c.isAdversaryCastle()){
                        c.setCaseActionable(R.color.colorBlue);
                    }
                    else if(c.getCard().isAdversary()){
                        c.setCaseActionable(R.color.colorBlue);
                    }
                    else {
                        c.setCaseNonActionable();
                    }
                }
                if(base.getCard().getRangePoints() > base.getCard().getMovementPoints()
                        && !c.isCardThumbnailEmpty()
                        && base.getXDistanceWith(c) <= base.getCard().getRangePoints()
                        && base.getYDistanceWith(c) <= base.getCard().getRangePoints()) {
                    if(c.isPlayersCastle()){
                        c.setCaseNonActionable();
                    }else if(c.isAdversaryCastle()){
                        c.setCaseActionable(R.color.colorBlue);
                    }
                    else if(c.getCard().isAdversary()){
                        c.setCaseActionable(R.color.colorBlue);
                    }
                    else {
                        c.setCaseNonActionable();
                    }
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

    public void chooseSpellAimPoint(Spell card){
        Board board = ActivityMgr.gameActivity.getBoard();
        actionState = 1;
        caseCard = card;
        for(Case c : board.getCases()){
            if (c.isCardThumbnailEmpty()) {
                c.setCaseActionable(R.color.colorGreen);
            } else c.setCaseActionable(R.color.colorBlue);
        }
    }

    public Card getCaseCard() {
        return caseCard;
    }

    public Case getMovingFrom() {return movingFrom;}

    public synchronized void clearUpdateHp(){
        caseToUpdateHp.clear();
    }

    public synchronized void addCaseToUpdateHp(Case c) {
        caseToUpdateHp.add(c);
    }

    public synchronized ArrayList<Case> getCaseToUpdateHp(){return caseToUpdateHp;}

    public synchronized void clearResetHp(){
        caseToResetCard.clear();
    }

    public synchronized void addCaseToResetCard(Case c) {
        caseToResetCard.add(c);
    }


    public synchronized ArrayList<Case> getCaseToResetCard(){return caseToResetCard;}

    public boolean isUpdateHp() {
        return updateHp;
    }

    public void setUpdateHp(boolean updateHp) {
        this.updateHp = updateHp;
    }

    public void setCaseCard(Card c){
        caseCard = c;
    }

    public boolean isResetCard() {
        return resetCard;
    }

    public void setResetCard(boolean resetCard) {
        this.resetCard = resetCard;
    }

    public boolean isUseSpell() {
        return useSpell;
    }

    public void setUseSpell(boolean useSpell) {
        this.useSpell = useSpell;
    }

    public Case getCaseToUseSpellOn() {
        return caseToUseSpellOn;
    }

    public void setCaseToUseSpellOn(Case caseToUseSpellOn) {
        this.caseToUseSpellOn = caseToUseSpellOn;
    }
}
