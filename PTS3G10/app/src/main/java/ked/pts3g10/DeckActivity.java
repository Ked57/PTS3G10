package ked.pts3g10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    private static Button deckChoiceButton;
    private Intent i;
    private boolean cardInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);

        dtemgr = new DeckTouchEventMgr(this);
        i = getIntent();
        deck = GameActivity.getBoard().getPlayer().getDeck();//Java .................

        if(GameActivity.getBoard().isPlayersTurn())
            setChoiceButtonText(getResources().getString(R.string.deck_choice));
        else setChoiceButtonText(getResources().getString(R.string.deck_choice_wrong));

        deckCrystalCost = (TextView) findViewById(R.id.deckCrystalCost);
        deckCardName = (TextView) findViewById(R.id.deckCardName);
        deckCardDescription = (TextView) findViewById(R.id.deckCardDescription);
        deckHealthPoints = (TextView) findViewById(R.id.deckHealthPoints);
        deckRangePoints = (TextView) findViewById(R.id.deckRangePoints);
        deckMovementPoints = (TextView) findViewById(R.id.deckMovementPoints);
        deckAttackPoints = (TextView) findViewById(R.id.deckAttackPoints);
        deckBackgroundImage = (ImageView) findViewById(R.id.deckBackgroundImage);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        currIndex = 0;

        deckChoiceButton = (Button) findViewById(R.id.deckChoiceButton);
        if(i.getBooleanExtra("info",false)){
            deckChoiceButton.setText(R.string.end_button);
            int id = i.getIntExtra("idCard",0);
            displayCardForId(id);
            cardInfo = true;
            deckChoiceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    finish();
                }
            });
        }else {
            displayCardForIndex(currIndex);
            cardInfo = false;
        }
    }

    @Override
    protected void onDestroy() {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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
        }
        deckRangePoints.setText(card.getRangePoints() + "");
        deckAttackPoints.setText(card.getAttactPoints() + "");
        deckBackgroundImage.setBackgroundResource(0);
        deckBackgroundImage.setBackgroundResource(card.getBackground());
    }

    public void displayCardForId(int k){
        Card card = LaunchActivity.getCardById(k);
        deckCrystalCost.setText("" + card.getCrystalCost());
        deckCardName.setText("" + card.getName());
        deckCardDescription.setText("" + card.getDescription());
        if (card instanceof BoardCard) {
            deckHealthPoints.setText(((BoardCard) card).getHealthPoints() + "");
            deckMovementPoints.setText(((BoardCard) card).getMovementPoints() + "");
        }
        deckRangePoints.setText(card.getRangePoints() + "");
        deckAttackPoints.setText(card.getAttactPoints() + "");
        deckBackgroundImage.setBackgroundResource(0);
        deckBackgroundImage.setBackgroundResource(card.getBackground());
    }

    public int getCurrIndex() {
        return currIndex;
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

    public static void setChoiceButtonText(String text){
        if(deckChoiceButton != null)
            deckChoiceButton.setText(text);
    }

    public boolean isCardInfo() {
        return cardInfo;
    }
}