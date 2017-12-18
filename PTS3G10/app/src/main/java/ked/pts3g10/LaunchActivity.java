package ked.pts3g10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ked.pts3g10.Gameplay.CardPackage.Army;
import ked.pts3g10.Gameplay.CardPackage.Card;


public class LaunchActivity extends AppCompatActivity {


    private static String adversaryName = "";
    private static boolean starting = true;
    public static ArrayList<Card> cards = new ArrayList<>();
    private Timer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);



            /* TODO: récupérer les cartes via le serveur (Json) */

                ImageView bg = new ImageView(this);
                bg.setBackgroundResource(R.drawable.castle);
                bg.setTag(R.drawable.castle);//A ne pas oublier
                ImageView thmbn = new ImageView(this);
                thmbn.setBackgroundResource(R.drawable.castle);
                thmbn.setTag(R.drawable.castle);
                cards.add(new Army("Chateau", "Votre base principale",0,0,0,30,0, bg, thmbn,false));

                ImageView bg4 = new ImageView(this);
                bg4.setBackgroundResource(R.drawable.castle);
                bg4.setTag(R.drawable.castle);//A ne pas oublier
                ImageView thmbn4 = new ImageView(this);
                thmbn4.setBackgroundResource(R.drawable.castle);
                thmbn4.setTag(R.drawable.castle);
                cards.add(new Army("Chateau", "Votre base principale",0,0,0,30,0, bg4, thmbn4,true));

                ImageView bg3 = new ImageView(this);
                bg3.setBackgroundResource(R.drawable.bow);
                bg3.setTag(R.drawable.bow);//A ne pas oublier
                ImageView thmbn3 = new ImageView(this);
                thmbn3.setBackgroundResource(R.drawable.bow);
                thmbn3.setTag(R.drawable.bow);
                cards.add(new Army("Archers", "Une armée d'archers",2,2,2,2,1, bg3, thmbn3,false));

                ImageView bg2 = new ImageView(this);
                bg2.setBackgroundResource(R.drawable.sword);
                bg2.setTag(R.drawable.sword);//A ne pas oublier
                ImageView thmbn2 = new ImageView(this);
                thmbn2.setBackgroundResource(R.drawable.sword);
                thmbn2.setTag(R.drawable.sword);
                cards.add(new Army("Légion","Une armée de légionnaires",1,2,1,2,1,bg2,thmbn2,false));



        findViewById(R.id.playButton).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                               Intent intent = new Intent(LaunchActivity.this, ChargementActivity.class);
                               startActivity(intent);
                           }
         });

        findViewById(R.id.devButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LaunchActivity.this, GameActivity.class);
                intent.putExtra("adversaryName","Dev");
                intent.putExtra("starting","true");
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

        //Button pour affiche la page de constitution du deck
        findViewById(R.id.constituerDeck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LaunchActivity.this, ConstructionDeckActivity.class);
                startActivity(intent);
            }
        });

        ActivityMgr.launchActivity = this;

        //Check toutes les 500ms si réponse
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }

                });
            }

        }, 0, 500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static void newGame(String adversaryName, boolean starting){
        ActivityMgr.launchActivity.adversaryName = adversaryName;
        ActivityMgr.launchActivity.starting = starting;
    }

    public void startGame(String adversaryName, boolean starting){
        Intent intent = new Intent(LaunchActivity.this, GameActivity.class);
        intent.putExtra("adversaryName",adversaryName);
        intent.putExtra("starting",""+starting);
        startActivity(intent);
    }
}
