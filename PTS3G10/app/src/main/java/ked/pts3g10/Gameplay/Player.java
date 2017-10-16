package ked.pts3g10.Gameplay;


import java.util.ArrayList;

import ked.pts3g10.DB.CardDB;
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
        ArrayList<Card> cards = new ArrayList<Card>();
        CardDB cardDB = new CardDB(context);
        for(int i = 0; i < 4; ++i){//Pour peupler en attendant de récupérer les decks du serveur
            cards.add(cardDB.select().get(0));
            cards.add(cardDB.select().get(1));
        }
        deck = new Deck(context,1,1,cards, "Un deck");//Valeurs exemple
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
}
