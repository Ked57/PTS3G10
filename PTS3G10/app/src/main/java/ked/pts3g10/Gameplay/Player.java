package ked.pts3g10.Gameplay;


import java.util.ArrayList;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.GameActivity;
import ked.pts3g10.Gameplay.CardPackage.Card;

public class Player {
    private Deck deck;
    private int crystals;
    private int healthPoints;
    private String nickName;
    private GameActivity context;
    private PlayerAction playerAction;
    private boolean adversary;

    public Player(GameActivity context,String nick, boolean adversary){
        nickName = nick;
        crystals = 1;
        healthPoints = 30; //A définir
        this.context = context;
        this.adversary = adversary;
        ArrayList<Card> cards = new ArrayList<Card>();
        if(adversary) {
            cards.add(ActivityMgr.launchActivity.cards.get(3).clone(adversary));
            cards.add(ActivityMgr.launchActivity.cards.get(5).clone(adversary));
            cards.add(ActivityMgr.launchActivity.cards.get(7).clone(adversary));
        }else{
            cards.add(ActivityMgr.launchActivity.cards.get(2).clone(adversary));
            cards.add(ActivityMgr.launchActivity.cards.get(4).clone(adversary));
            cards.add(ActivityMgr.launchActivity.cards.get(6).clone(adversary));
        }
        deck = new Deck(context,1,1,cards, "Un deck");//Valeurs exemple
        playerAction = new PlayerAction(this);
    }

    public Player(GameActivity context,String nick,ArrayList<Card> cards){
        nickName = nick;
        crystals = 0;
        healthPoints = 30; //A définir
        this.context = context;
        deck = new Deck(context,1,1,cards, "Un deck");//Valeurs exemple
    }

    public int getCrystals() {
        return crystals;
    }

    public void setCrystals(int crystals) {
        this.crystals = crystals;
    }

    public void setCrystals(int id,int crystals) {
        this.crystals = crystals;
        context.updateText(id,crystals);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public PlayerAction getPlayerAction() { return playerAction; }
}
