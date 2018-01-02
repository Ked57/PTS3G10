package ked.pts3g10;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ked.pts3g10.Gameplay.CardPackage.BoardCard;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.Gameplay.CardPackage.Hero;
import ked.pts3g10.Gameplay.CardPackage.Spell;
import ked.pts3g10.Gameplay.Player;
import ked.pts3g10.Util.XMLParser;

public class ConstructionDeckActivity extends AppCompatActivity {

    private ConstructionDeckActivity context;
    private ImageButton terminer;
    private ImageView cardView;
    private Button addToDeck, removeFromDeck, leftArrow, rightArrow;
    private TextView cardName;
    private LinearLayout cardInfoLinear;
    private List<Card> card_list = new ArrayList<>();
    private List<Card> deck_card = new ArrayList<>();
    private int index = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_deck);

        //Liason éléments interface avec variables
        leftArrow = (Button) findViewById(R.id.leftArrow);
        rightArrow = (Button) findViewById(R.id.rightArrow);
        terminer = (ImageButton) findViewById(R.id.terminer);
        cardView = (ImageView) findViewById(R.id.cardView);
        addToDeck = (Button) findViewById(R.id.addToDeck);
        removeFromDeck = (Button) findViewById(R.id.removeFromDeck);
        cardName = (TextView) findViewById(R.id.cardName);
        cardInfoLinear = (LinearLayout) findViewById(R.id.cardInfoLinear);
        leftArrow.setEnabled(false);
        removeFromDeck.setEnabled(false);
        updateAddCardButton(0);

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
        loadCard();
        showCardForIndex();
    }

    private void updateAddCardButton(int count) {
        addToDeck.setText("AJOUTER (" + count + "/2)");
    }

    private void addToDeckCall() {
        Card selectedCard = card_list.get(index);
        int count = countCard(selectedCard);
        if(count < 2) {
            deck_card.add(selectedCard);
            count++;
        }
        if(count > 0) {
            if(count == 2) addToDeck.setEnabled(false);
            removeFromDeck.setEnabled(true);
        }
        updateAddCardButton(count);
    }

    private void removeFromDeckCall() {
        Card selectedCard = card_list.get(index);
        int count = countCard(selectedCard);
        if(count > 0) {
            deck_card.remove(selectedCard);
            count--;
        }
        if(count < 2) {
            if(count == 0) removeFromDeck.setEnabled(false);
            addToDeck.setEnabled(true);
        }
        updateAddCardButton(count);
    }

    private int countCard(Card selectedCard) {
        int v = 0;
        for(Card c : deck_card) if(selectedCard == c) v++;
        return v;
    }

    private void loadCard() {
        for(Card c : LaunchActivity.cards) {
            if(!c.isAdversary() && !c.getName().equals("Chateau")) {
                card_list.add(c);
            }
        }
    }

    private void showCardForIndex() {
        Card c = card_list.get(index);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f));

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
        ability.setOrientation(LinearLayout.HORIZONTAL);
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

        layout.addView(range);
        layout.addView(attack);
        layout.addView(cristal);

        rangeValue.setText(c.getRangePoints() + "");
        attackValue.setText(c.getAttactPoints() + "");
        cristalValue.setText(c.getCrystalCost() + "");

        if(c instanceof BoardCard) {
            BoardCard bc = (BoardCard)c;
            moveValue.setText(bc.getMovementPoints() + "");
            healthValue.setText(bc.getHealthPoints() + "");
            layout.addView(move);
            layout.addView(health);
        } else {
            if(c instanceof Spell) {
                Spell s = (Spell) c;
                abilityName.setText(s.getAbility().getName());
                abilityDescription.setText(s.getAbility().getName());
            } else if(c instanceof Hero) {
                Hero h = (Hero) c;
                abilityName.setText(h.getAbility().getName());
                abilityDescription.setText(h.getAbility().getName());
            }
            layout.addView(ability);
        }

        if(cardInfoLinear.getChildCount() == 4) cardInfoLinear.removeViewAt(2);
        cardInfoLinear.addView(layout, 2);

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