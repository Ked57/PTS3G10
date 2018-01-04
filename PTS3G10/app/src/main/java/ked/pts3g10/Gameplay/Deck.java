package ked.pts3g10.Gameplay;
import java.util.ArrayList;

import ked.pts3g10.Gameplay.CardPackage.Card;

public class Deck{

    private ArrayList<Card> cardList;

    public Deck(ArrayList<Card> cards){
        cardList = cards;
    }

    public ArrayList<Card> getCardList() {
        return cardList;
    }

    public void addCard(Card card){
        if(cardList.size() < 15)
        {
            cardList.add(card);
        }
    }

    public void removeCard(Card card){
        if(cardList.size() > 0){
            cardList.remove(card);
        }
    }

    public Card getWithId(int id){
        for(Card c : cardList){
            if(c.getId() == id) return c;
        }
        return null;
    }
}
