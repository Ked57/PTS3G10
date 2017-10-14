package ked.pts3g10.Gameplay;


import android.widget.ImageView;

import java.util.ArrayList;

import ked.pts3g10.GameActivity;
import ked.pts3g10.Gameplay.CardPackage.Army;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.R;

public class Deck {

    private GameActivity context;
    private int playerId;
    private int deckId;
    private ArrayList<Card> cardList;
    private String name;

    public Deck(GameActivity context, int pId, int dId, ArrayList<Card> cards, String name){
        playerId = pId;
        deckId = dId;
        cardList = cards;
        this.name = name;
        this.context = context;

        /* Placeholder */
        for(int i = 0; i < 15; ++i){
            ImageView bg = new ImageView(context);
            bg.setBackgroundResource(R.drawable.sword);
            ImageView thmbn = new ImageView(context);
            thmbn.setBackgroundResource(R.drawable.sword);
            cardList.add(new Army("Placeholder "+i , 3, 1, 2, 1, bg,thmbn));
        }
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
