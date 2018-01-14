package ked.pts3g10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import ked.pts3g10.Gameplay.CardPackage.BoardCard;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.Gameplay.CardPackage.Hero;
import ked.pts3g10.Gameplay.CardPackage.Spell;
import ked.pts3g10.Gameplay.Deck;
import ked.pts3g10.Util.DeckManager;

public class ConstructionDeckActivity extends AppCompatActivity {

    private ConstructionDeckActivity context;
    private ImageView cardView;
    private Button saveDeck, addToDeck, removeFromDeck, leftArrow, rightArrow;
    private TextView cardName, deckCardCount;
    private LinearLayout cardInfoLinear;
    private List<Card> card_list = new ArrayList<>();
    private Deck deck_card;
    private int index = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_deck);

        //Liason éléments interface avec variables
        leftArrow = (Button) findViewById(R.id.leftArrow);
        rightArrow = (Button) findViewById(R.id.rightArrow);
        saveDeck = (Button) findViewById(R.id.saveDeck);
        cardView = (ImageView) findViewById(R.id.cardView);
        addToDeck = (Button) findViewById(R.id.addToDeck);
        removeFromDeck = (Button) findViewById(R.id.removeFromDeck);
        cardName = (TextView) findViewById(R.id.cardName);
        deckCardCount = (TextView) findViewById(R.id.deckCardCount);
        cardInfoLinear = (LinearLayout) findViewById(R.id.cardInfoLinear);
        leftArrow.setEnabled(false);
        removeFromDeck.setEnabled(false);

        context = this;

        //Action Fleches
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftArrowCall();
            }
        });
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightArrowCall();
            }
        });
        addToDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToDeckCall();
            }
        });
        removeFromDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFromDeckCall();
            }
        });
        saveDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDeck();
            }
        });

        deck_card = LaunchActivity.playerDeck;
        loadCard();
        showCardForIndex();
        if(countCard(card_list.get(index)) >= 2) addToDeck.setEnabled(false);
    }
    @Override
    public void onBackPressed()
    {
        finish();
    }

    private void saveDeck() {
        if(deck_card.getCardList().size() == 10 || deck_card.getCardList().size() == card_list.size()*2) {
            List<Integer> deck_card_id_list = new ArrayList<>();
            for(Card c : deck_card.getCardList()) deck_card_id_list.add(c.getId());
            if(DeckManager.save(deck_card_id_list, ConnectionActivity.token)) {
                Toast.makeText(context, "Deck enregistré", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Un problème est survenu. Veuillez réessayer", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "Vous devez sélectionner 10 cartes", Toast.LENGTH_SHORT).show();
        }
        LaunchActivity.playerDeck = deck_card;
    }

    private void updateAddCardButton(int count) {
        addToDeck.setText("AJOUTER (" + count + "/2)");
        deckCardCount.setText(" " + deck_card.getCardList().size());
    }

    private void addToDeckCall() {
        Card selectedCard = card_list.get(index).clone(false);
        int count = countCard(selectedCard);
        if(count < 2) {
            deck_card.addCard(selectedCard);
            count++;
        }
        if(count > 0) {
            if(count >= 2) addToDeck.setEnabled(false);
            removeFromDeck.setEnabled(true);
        }
        updateAddCardButton(count);
    }

    private void removeFromDeckCall() {
        Card selectedCard = deck_card.getWithId(card_list.get(index).getId());
        int count = countCard(selectedCard);
        if(count > 0) {
            deck_card.removeCard(selectedCard);
            count = countCard(selectedCard);
        }
        if(count < 2) {
            if(count == 0) removeFromDeck.setEnabled(false);
            addToDeck.setEnabled(true);
        }
        updateAddCardButton(count);
    }

    private int countCard(Card selectedCard) {
        int v = 0;
        for(Card c : deck_card.getCardList()) if(selectedCard.getId() == c.getId()) v++;
        return v;
    }

    private void loadCard() {
        for(Card c : LaunchActivity.cards) {
            if(!c.isAdversary() && !c.getName().equals("Chateau")) {
                Log.i("DeckConstruction","loaded card "+c.getName());
                card_list.add(c.clone(false));
            }
        }
    }

    private void showCardForIndex() {
        Card c = card_list.get(index);

        LinearLayout layoutLine1 = new LinearLayout(this);
        layoutLine1.setOrientation(LinearLayout.HORIZONTAL);
        layoutLine1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f));

        LinearLayout layoutLine2 = new LinearLayout(this);
        layoutLine2.setOrientation(LinearLayout.HORIZONTAL);
        layoutLine2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f));

        LinearLayout range = new LinearLayout(this);
        range.setOrientation(LinearLayout.VERTICAL);
        range.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.2f));

        LinearLayout attack = new LinearLayout(this);
        attack.setOrientation(LinearLayout.VERTICAL);
        attack.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.2f));

        LinearLayout move = new LinearLayout(this);
        move.setOrientation(LinearLayout.VERTICAL);
        move.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.2f));

        LinearLayout health = new LinearLayout(this);
        health.setOrientation(LinearLayout.VERTICAL);
        health.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.2f));

        LinearLayout cristal = new LinearLayout(this);
        cristal.setOrientation(LinearLayout.VERTICAL);
        cristal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.2f));

        LinearLayout ability = new LinearLayout(this);
        ability.setOrientation(LinearLayout.VERTICAL);
        ability.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.6f));

        TextView rangeValue = new TextView(this);
        TextView attackValue  = new TextView(this);
        TextView moveValue = new TextView(this);
        TextView healthValue = new TextView(this);
        TextView cristalValue = new TextView(this);
        TextView abilityName = new TextView(this);
        TextView abilityDescription = new TextView(this);

        rangeValue.setGravity(Gravity.CENTER);
        attackValue.setGravity(Gravity.CENTER);
        moveValue.setGravity(Gravity.CENTER);
        healthValue.setGravity(Gravity.CENTER);
        cristalValue.setGravity(Gravity.CENTER);
        abilityName.setGravity(Gravity.CENTER);
        abilityDescription.setGravity(Gravity.CENTER);

        ImageView rangeView = new ImageView(this);
        ImageView attackView = new ImageView(this);
        ImageView moveView = new ImageView(this);
        ImageView healthView = new ImageView(this);
        ImageView cristalView = new ImageView(this);

        rangeView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        attackView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        moveView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        healthView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        cristalView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        rangeView.setBackgroundResource(R.drawable.range);
        attackView.setBackgroundResource(R.drawable.sword);
        moveView.setBackgroundResource(R.drawable.feet);
        healthView.setBackgroundResource(R.drawable.heart);
        cristalView.setBackgroundResource(R.drawable.crystal);

        range.addView(rangeValue);
        range.addView(rangeView);

        attack.addView(attackValue);
        attack.addView(attackView);

        move.addView(moveValue);
        move.addView(moveView);

        health.addView(healthValue);
        health.addView(healthView);

        cristal.addView(cristalValue);
        cristal.addView(cristalView);

        ability.addView(abilityName);
        ability.addView(abilityDescription);

        layoutLine1.addView(range);
        layoutLine1.addView(attack);
        layoutLine1.addView(cristal);

        rangeValue.setText(c.getRangePoints() + "");
        attackValue.setText(c.getAttactPoints() + "");
        cristalValue.setText(c.getCrystalCost() + "");

        if(c instanceof BoardCard) {
            BoardCard bc = (BoardCard)c;
            moveValue.setText(bc.getMovementPoints() + "");
            healthValue.setText(bc.getHealthPoints() + "");
            layoutLine2.addView(move);
            layoutLine2.addView(health);
        } else {
            if(c instanceof Spell) {
                Spell s = (Spell) c;
                abilityName.setText(s.getAbility().getName());
                abilityDescription.setText(s.getAbility().getDescription());
            } else if(c instanceof Hero) {
                Hero h = (Hero) c;
                abilityName.setText(h.getAbility().getName());
                abilityDescription.setText(h.getAbility().getDescription());
            }
            layoutLine2.addView(ability);
        }

        if(cardInfoLinear.getChildCount() == 5) {
            cardInfoLinear.removeViewAt(2);
            cardInfoLinear.removeViewAt(2);
        }
        cardInfoLinear.addView(layoutLine1, 2);
        cardInfoLinear.addView(layoutLine2, 3);

        cardView.setBackgroundResource(c.getBackground());
        cardName.setText(c.getName());
        int count = countCard(c);
        updateAddCardButton(count);
        if(count == 2) addToDeck.setEnabled(false);
        else if(count < 2) addToDeck.setEnabled(true);
        if(count == 0) removeFromDeck.setEnabled(false);
        else if(count > 0) removeFromDeck.setEnabled(true);
    }

    private void leftArrowCall() {
        if(index > 0) {
            index--;
            showCardForIndex();
            rightArrow.setEnabled(true);
            if(index == 0) {
                leftArrow.setEnabled(false);
            }
        }
    }

    private void rightArrowCall() {
        if (index < card_list.size() - 1) {
            index++;
            showCardForIndex();
            leftArrow.setEnabled(true);
            if (index == card_list.size() - 1) {
                rightArrow.setEnabled(false);
            }
        }
    }
}