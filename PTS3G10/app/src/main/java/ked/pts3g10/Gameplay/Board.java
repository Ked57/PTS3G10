package ked.pts3g10.Gameplay;


import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.DeckActivity;
import ked.pts3g10.GameActivity;
import ked.pts3g10.Gameplay.CardPackage.BoardCard;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.Interface.Case;
import ked.pts3g10.LaunchActivity;
import ked.pts3g10.Network.packet.PacketEndRound;
import ked.pts3g10.R;
import ked.pts3g10.Util.Pos;

public class Board {

    private GameActivity context;
    private Player player;
    private Player adversary;

    private final int DEFAULT_SECONDS = 60;//Temps au tour par défaut

    private int seconds; //Temps du tour
    private boolean endRound;
    private boolean playersTurn; // true -> le joueur, false -> l'adversaire
    private int roundNumber;
    private Timer t,t2;

    private int serverRound;


    private LinearLayout V1,V2,V3,V4,V5;//V pour verical, le nombre pour le numéro de colonne
    private ArrayList<Case> cases;

    private Button endRoundButton;

    public Board(GameActivity context, Player player, Player adversary, boolean playersTurn){
        this.context = context;
        this.player = player;
        this.adversary = adversary;
        this.cases = new ArrayList<Case>();

        V1 = (LinearLayout) context.findViewById(R.id.V1);
        V2 = (LinearLayout) context.findViewById(R.id.V2);
        V3 = (LinearLayout) context.findViewById(R.id.V3);
        V4 = (LinearLayout) context.findViewById(R.id.V4);
        V5 = (LinearLayout) context.findViewById(R.id.V5);
        initLayout(V1,0);
        initLayout(V2,1);
        initLayout(V3,2);
        initLayout(V4,3);
        initLayout(V5,4);


        initCastles();

        /*Init des text view */
        updateTexts();


        endRoundButton = (Button) context.findViewById(R.id.EndButton);


        roundNumber = 0;
        serverRound = 0;
        seconds = DEFAULT_SECONDS;
        endRound = false;
        this.playersTurn = !playersTurn; // Valeur acheminée du serveur, implémenté plus tard

        t = new Timer();
        t2 = new Timer();

        newRound();

        initNetLoop();
    }

    public void initCastles(){
        Card castleFriend = LaunchActivity.cards.get(0);
        Card castleEni = LaunchActivity.cards.get(1);
        getCaseWithLinearLayoutNumber(2,0).setCard(context,(BoardCard)castleEni);
        getCaseWithLinearLayoutNumber(2,4).setCard(context,(BoardCard)castleFriend);
    }

    public void initNetLoop(){
        t2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        if(adversary.getPlayerAction().isPlaceBoardCardNext()){
                            adversary.getPlayerAction().placeBoardCardNext(context);
                        }
                        if(adversary.getPlayerAction().isMoveBoardCardNext()){
                            adversary.getPlayerAction().moveBoardCardNext(context);
                        }
                        if(adversary.getPlayerAction().isUpdateHp()){
                            for(Case c : adversary.getPlayerAction().getCaseToUpdateHp()){
                                c.notifyUpdateHp();
                            }
                            adversary.getPlayerAction().clearUpdateHp();
                            adversary.getPlayerAction().setUpdateHp(false);
                        }
                        if(adversary.getPlayerAction().isResetCard()){
                            for(Case c : adversary.getPlayerAction().getCaseToResetCard()){
                                c.notifyResetCard();
                                if(c.isPlayersCastle()){
                                    context.setNormalFinish(true);
                                    context.endGame("Vous avez perdu !","Vous avez gagné !");
                                }else if(c.isAdversaryCastle()){
                                    context.setNormalFinish(true);
                                    context.endGame("Vous avez gagné !","Vous avez perdu !");
                                }
                            }
                            adversary.getPlayerAction().clearResetHp();
                            adversary.getPlayerAction().setResetCard(false);
                        }

                        if(adversary.getPlayerAction().isUseSpell()){
                            adversary.getPlayerAction().useSpellCard(adversary.getPlayerAction().getCaseToUseSpellOn());
                            adversary.getPlayerAction().setUseSpell(false);
                        }
                    }

                });
            }

        }, 0, 100);
    }

    public ArrayList<Case> getCases() {
        return cases;
    }

    public Player getPlayer(){ return player;}

    public void initLayout(LinearLayout V, int number){
        for(int i = 0; i < 5; ++i){
            Case c = new Case(context,new Pos(number,i));
            V.addView(c);
            cases.add(c);
            context.registerForContextMenu(c);
        }
    }

    public Case getCaseWithLinearLayoutNumber(int linearLayoutNumber, int caseNumber){
        return cases.get(linearLayoutNumber*5+caseNumber);
    }

    public void updateTexts(){
        context.updateText(R.id.ADVCardsLeftText,adversary.getDeck().getCardList().size()); // Par défaut pour le moment
        context.updateText(R.id.ADVCrystalsText,adversary.getCrystals());
        context.updateText(R.id.ADVNick,adversary.getNickName());

        context.updateText(R.id.PlayerCardsLeftText,player.getDeck().getCardList().size());
        context.updateText(R.id.PlayerCrystalsText,player.getCrystals());
        context.updateText(R.id.PlayerNick,player.getNickName());
    }

    public void newRound(){
        t.cancel();
        t.purge();

        t = new Timer();

        resetCardsHaveMovedThisRound();
        seconds = DEFAULT_SECONDS;
        endRound = false;
        playersTurn = !playersTurn;
        Log.i("Network","New round number is "+roundNumber);
        if(roundNumber <= 2)
        {
            player.setCrystals(R.id.PlayerCrystalsText,1);
            adversary.setCrystals(R.id.ADVCrystalsText,1);
        }
        else if(roundNumber <= 18) { // TODO: Un paramètre avec le nombre maximum de crystaux, ici un dira que c'est 9
            player.setCrystals(R.id.PlayerCrystalsText,(roundNumber/2)+(roundNumber%2)); //Pas besoin de vérifier les crystaux de l'aversaire
            adversary.setCrystals(R.id.ADVCrystalsText,(roundNumber/2)+(roundNumber%2));//Chaque joueur voit ses crystaux remis au nombre initial +1 (donc au n° de tour)
        }
        else{
            player.setCrystals(R.id.PlayerCrystalsText,9);
            adversary.setCrystals(R.id.ADVCrystalsText,9);
        }

        if(playersTurn){
            endRoundButton.setBackgroundColor(Color.rgb(0,153,0));
            endRoundButton.setClickable(true);
            DeckActivity.setChoiceButtonText(context.getResources().getString(R.string.deck_choice));
        }else {
            endRoundButton.setBackgroundColor(Color.GRAY);
            endRoundButton.setClickable(false);
            DeckActivity.setChoiceButtonText(context.getResources().getString(R.string.deck_choice_wrong));
        }

        clearBoardActions(); // Pour enlever les éventuelles actions en cours

        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        TextView tv = (TextView) context.findViewById(R.id.Timer);
                        tv.setText(String.valueOf(seconds));
                        if(seconds > 0)
                            seconds -= 1;
                        if(endRound){
                            roundNumber = serverRound;
                            newRound();
                        }
                    }

                });
            }

        }, 0, 1000);
    }

    public Player getAdversary(){return adversary;}

    public void clearBoardActions(){
        for(Case c : cases){
            c.setCaseNonActionable();
        }
    }

    public void resetCardsHaveMovedThisRound(){
        for(Case c : cases){
            if(c.getCard() != null && c.getCard().hasMovedThisRound()) c.getCard().setHasMovedThisRound(false);
        }
    }

    public boolean isPlayersTurn() {
        return playersTurn;
    }
    //Destinée aux tests, met quelques cartes adverses sur le board
    public void populate(){
        adversary.getPlayerAction().placeBoardCard(context,(BoardCard) adversary.getDeck().getCardList().get(0),cases.get(7));
        adversary.getPlayerAction().placeBoardCard(context,(BoardCard) adversary.getDeck().getCardList().get(0),cases.get(12));
    }

    public void setEndRound(boolean endRound, int roundNumber){
        this.serverRound = roundNumber;
        this.endRound=endRound;
    }

    public void onEndRoundButtonClick(){
        new PacketEndRound().call(ConnectionActivity.token);
    }
}
