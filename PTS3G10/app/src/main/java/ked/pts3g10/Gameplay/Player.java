package ked.pts3g10.Gameplay;


import java.util.ArrayList;

import ked.pts3g10.GameActivity;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.LaunchActivity;

public class Player {
    private Deck deck;
    private int crystals;
    private int healthPoints;
    private String nickName;
    private GameActivity context;
    private PlayerAction playerAction;
    private boolean adversary;

    public Player(GameActivity context,String nick, boolean adversary, Deck deck){
        nickName = nick;
        crystals = 1;
        healthPoints = 30; //A définir
        this.context = context;
        this.adversary = adversary;
        this.deck = deck;
        playerAction = new PlayerAction(this);
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

    public boolean isAdversary(){return adversary;}
}
