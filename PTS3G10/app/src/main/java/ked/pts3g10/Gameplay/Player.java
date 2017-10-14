package ked.pts3g10.Gameplay;


import java.util.ArrayList;

import ked.pts3g10.GameActivity;
import ked.pts3g10.Gameplay.CardPackage.Card;

public class Player {
    private Deck deck;
    private int crystals;
    private int healthPoints;
    private String nickName;
    private GameActivity context;

    public Player(GameActivity context,String nick){
        nickName = nick;
        crystals = 0;
        healthPoints = 30; //A définir
        this.context = context;
        deck = new Deck(context,1,1,new ArrayList<Card>(), "Un deck");//Valeurs exemple
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
}
