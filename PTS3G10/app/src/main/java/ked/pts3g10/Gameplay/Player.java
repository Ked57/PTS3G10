package ked.pts3g10.Gameplay;


import java.util.ArrayList;

public class Player {
    private Deck deck;
    private int crystals;
    private int healthPoints;
    private String nickName;

    public Player(String nick){
        nickName = nick;
        crystals = 0;
        healthPoints = 30; //A définir
        deck = new Deck(1,1,new ArrayList<Card>(), "Un deck");//Valeurs exemple
    }

    public int getCrystals() {
        return crystals;
    }

    public void setCrystals(int crystals) {
        this.crystals = crystals;
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
