package ked.pts3g10;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.Gameplay.Player;

public class ConstructionDeckActivity extends AppCompatActivity {

    private RelativeLayout espacCarte;
    private Button selectionner;
    private ImageButton left_arrow, right_arrow;
    private ArrayList<Card> listCardPlayer;



    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_construction_deck);
        selectionner = (Button) findViewById(R.id.selectionner);
        left_arrow = (ImageButton) findViewById(R.id.left_arrow);
        right_arrow = (ImageButton) findViewById(R.id.right_arrow);








    }


    /***
     *
     * @param player
     */
    public void afficher( Player player){



    }



}
