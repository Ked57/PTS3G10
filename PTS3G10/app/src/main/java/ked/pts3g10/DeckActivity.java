package ked.pts3g10;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import ked.pts3g10.DB.CardDB;
import ked.pts3g10.Events.DeckTouchEventMgr;
import ked.pts3g10.Gameplay.CardPackage.BoardCard;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.Gameplay.Deck;

public class DeckActivity extends AppCompatActivity {

    private DeckTouchEventMgr dtemgr;
    private Deck deck;
    private TextView deckCrystalCost, deckCardName, deckCardDescription, deckAttackPoints, deckHealthPoints, deckRangePoints, deckMovementPoints;
    private int currIndex;
    private ImageView deckBackgroundImage;

    private CardDB cardDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);
        cardDb = new CardDB(this);

        dtemgr = new DeckTouchEventMgr(this);
        Intent i = getIntent();
        int deckId = i.getIntExtra("deckId", 0);
        int pId = i.getIntExtra("pId", 0);
        String dName = i.getStringExtra("dName");
        deck = new Deck(this, deckId, pId, cardDb.select(), dName);//Pour l'instant il n'y a que des placeholders mais la classe devra aller chercher les infos sur le serveur

        deckCrystalCost = (TextView) findViewById(R.id.deckCrystalCost);
        deckCardName = (TextView) findViewById(R.id.deckCardName);
        deckCardDescription = (TextView) findViewById(R.id.deckCardDescription);
        deckHealthPoints = (TextView) findViewById(R.id.deckHealthPoints);
        deckRangePoints = (TextView) findViewById(R.id.deckRangePoints);
        deckMovementPoints = (TextView) findViewById(R.id.deckMovementPoints);
        deckAttackPoints = (TextView) findViewById(R.id.deckAttackPoints);
        deckBackgroundImage = (ImageView) findViewById(R.id.deckBackgroundImage);

        currIndex = 0;
        displayCardForIndex(currIndex);

    }

    @Override
    protected void onDestroy() {
        cardDb.disconnect();
        super.onDestroy();
    }

    public void displayCardForIndex(int k) {
        Card card = deck.getCardList().get(k);
        deckCrystalCost.setText("" + card.getCrystalCost());
        deckCardName.setText("" + card.getName());
        deckCardDescription.setText("" + card.getDescription());
        if (card instanceof BoardCard) {
            deckHealthPoints.setText(((BoardCard) card).getHealthPoints() + "");
            deckMovementPoints.setText(((BoardCard) card).getMovementPoints() + "");
            //Log.i("MP",""+((BoardCard) card).getMovementPoints());
        }
        deckRangePoints.setText(card.getRangePoints() + "");
        deckAttackPoints.setText(card.getAttactPoints() + "");
        int bgResource = Integer.parseInt(card.getBackground().getTag().toString());
        deckBackgroundImage.setBackgroundResource(bgResource);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dtemgr.setOnTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void swipeLeft() {
        if (currIndex > 0) {
            --currIndex;
            displayCardForIndex(currIndex);
        }//TODO: else : afficher un hint
    }

    public void swipeRight() {
        if (currIndex < deck.getCardList().size()-1 && currIndex < 15-1) { //TODO: utiliser une variable finale globale à l'appli pour ce genre de valeurs
            ++currIndex;
            displayCardForIndex(currIndex);
        }//TODO: else : afficher un hint

    }
}