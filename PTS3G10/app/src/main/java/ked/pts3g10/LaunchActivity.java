package ked.pts3g10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import ked.pts3g10.DB.CardDB;
import ked.pts3g10.Gameplay.CardPackage.Army;

public class LaunchActivity extends AppCompatActivity {

    private CardDB cardDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        cardDb = new CardDB(this);
        if(cardDb.select().isEmpty()){
            /* TODO: récupérer les cartes via le serveur (Json) */

                ImageView bg = new ImageView(this);
                bg.setBackgroundResource(R.drawable.castle);
                bg.setTag(R.drawable.castle);//A ne pas oublier
                ImageView thmbn = new ImageView(this);
                thmbn.setBackgroundResource(R.drawable.castle);
                thmbn.setTag(R.drawable.castle);
                cardDb.insert(new Army("Chateau", "Votre base principale",0,0,0,30,0, bg, thmbn,false));

                ImageView bg3 = new ImageView(this);
                bg3.setBackgroundResource(R.drawable.bow);
                bg3.setTag(R.drawable.bow);//A ne pas oublier
                ImageView thmbn3 = new ImageView(this);
                thmbn3.setBackgroundResource(R.drawable.bow);
                thmbn3.setTag(R.drawable.bow);
                cardDb.insert(new Army("Archers", "Une armée d'archers",2,2,2,2,1, bg3, thmbn3,false));

                ImageView bg2 = new ImageView(this);
                bg2.setBackgroundResource(R.drawable.sword);
                bg2.setTag(R.drawable.sword);//A ne pas oublier
                ImageView thmbn2 = new ImageView(this);
                thmbn2.setBackgroundResource(R.drawable.sword);
                thmbn2.setTag(R.drawable.sword);
                cardDb.insert(new Army("Légion","Une armée de légionnaires",1,2,1,2,1,bg2,thmbn2,false));
        }


        findViewById(R.id.playButton).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                               Intent intent = new Intent(LaunchActivity.this, ChargementActivity.class);
                               startActivity(intent);
                           }
         });

        //Boutton pour afficher la page crédits
        findViewById(R.id.credits).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LaunchActivity.this, CreditActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.constituerDeck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LaunchActivity.this, ConstructionDeckActivity.class);
                startActivity(intent);
            }
        });

        ActivityMgr.launchActivity = this;
    }

    @Override
    protected void onDestroy() {
        cardDb.disconnect();
        super.onDestroy();
    }

}
