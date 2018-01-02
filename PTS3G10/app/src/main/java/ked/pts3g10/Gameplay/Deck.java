package ked.pts3g10.Gameplay;


import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ked.pts3g10.DeckActivity;
import ked.pts3g10.GameActivity;
import ked.pts3g10.Gameplay.CardPackage.Army;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.R;

public class Deck{

    private GameActivity context;
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

    public Deck(GameActivity context, int pId, int dId, ArrayList<Card> cards, String name){
        this(pId,dId,cards,name);
        //initPlaceholder(context);
    }

    public Deck(DeckActivity context, int pId, int dId, ArrayList<Card> cards, String name){
        this(pId,dId,cards,name);
        //initPlaceholder(context);
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

    @Override
    public String toString() {
        String str = "Deck{playerId="+playerId+", deckId=" + deckId +", cardList={";
        boolean firstCard = true;
        for(Card c : cardList){
            if(!firstCard){
                str += ",";
            }else firstCard = false;
            str += c.toString();
        }
        str += "}, name='" + name + '\'' +'}';
        return str;
    }
}
