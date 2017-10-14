package ked.pts3g10.Gameplay;


import java.util.ArrayList;

import ked.pts3g10.Gameplay.CardPackage.Card;

public class Deck {

    private int playerId;
    private int deckId;
    private ArrayList<Card> cardList;
    private String name;

    public Deck(int pId, int dId, ArrayList<Card> cards, String name){
        playerId = pId;
        deckId = dId;
        cardList = cards;
        this.name = name;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getDeckId() {
        return deckId;
    }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }

    public ArrayList<Card> getCardList() {
        return cardList;
    }

    public void setCardList(ArrayList<Card> cardList) {
        this.cardList = cardList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCard(Card card){
        if(cardList.size() < 15) //TODO: Une classe/Enum avec ces paramètres
        {
            cardList.add(card);
        }
    }
}
