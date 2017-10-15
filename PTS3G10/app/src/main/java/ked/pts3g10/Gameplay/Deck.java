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

    public void initPlaceholder(GameActivity context){
        /* Placeholder */
        for(int i = 0; i < 15; ++i){
            ImageView bg = new ImageView(context);
            bg.setBackgroundResource(R.drawable.sword);
            bg.setTag(R.drawable.sword);//Pour pouvoir récupérer l'id du drawale plus tard
            ImageView thmbn = new ImageView(context);
            thmbn.setBackgroundResource(R.drawable.sword);
            thmbn.setTag(R.drawable.sword);
            cardList.add(new Army("Placeholder "+i ,"Description du placeholder "+i,2, 3, 1, 2, 1, bg,thmbn));
        }
    }

    public void initPlaceholder(DeckActivity context){
        /* Placeholder */
        for(int i = 0; i < 15; ++i){
            ImageView bg = new ImageView(context);
            bg.setBackgroundResource(R.drawable.sword);
            bg.setTag(R.drawable.sword);//Pour pouvoir récupérer l'id du drawale plus tard
            ImageView thmbn = new ImageView(context);
            thmbn.setBackgroundResource(R.drawable.sword);
            thmbn.setTag(R.drawable.sword);
            cardList.add(new Army("Placeholder "+i ,"Description du placeholder "+i,2, 3, 1, 2, 1, bg,thmbn));
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
