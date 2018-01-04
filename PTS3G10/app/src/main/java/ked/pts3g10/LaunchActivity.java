package ked.pts3g10;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ked.pts3g10.Gameplay.AbilityPackage.Ability;
import ked.pts3g10.Gameplay.AbilityPackage.DamageAbility;
import ked.pts3g10.Gameplay.AbilityPackage.HealAbility;
import ked.pts3g10.Gameplay.AbilityPackage.HealAndDamageAbility;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.Network.packet.PacketJoinGameWaitingList;
import ked.pts3g10.Network.packet.PacketSendImStillHere;
import ked.pts3g10.Network.packet.PacketSendLogOut;
import ked.pts3g10.Util.BackgroundAsyncXMLDownload;
import ked.pts3g10.Util.XMLParser;


public class LaunchActivity extends AppCompatActivity {

   public final HealAndDamageAbility emptyAbility = new HealAndDamageAbility(0,null,null);

    private static String adversaryName = "";
    private static boolean starting = true;
    public static ArrayList<Card> cards = new ArrayList<>();
    private Timer t, t2;
    private XMLParser xmlParser;
    private int version;
    public static ArrayList<Ability> abilities = new ArrayList<>();
    private boolean quit;

    public static boolean displayEndGame;
    public static String endGameMessage;
    public static boolean timeout;
    public static String timeoutMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        xmlParser = new XMLParser();
        version = 0;
        quit = false;

        displayEndGame = false;
        endGameMessage = "";

        timeout = false;
        timeoutMessage = "";

        initAbilities();
        getDistantCards();

        findViewById(R.id.playButton).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(cards.size() > 0) {
                     new PacketJoinGameWaitingList().call();
                     Intent intent = new Intent(LaunchActivity.this, WaitingForGameActivity.class);
                     startActivity(intent);
                 }else {
                     Toast t = Toast.makeText(LaunchActivity.this,R.string.toastLoadingCards,Toast.LENGTH_SHORT);
                     t.show();
                 }
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
        //
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t2.cancel();
                t2.purge();
                t2 = null;
                new PacketSendLogOut().call(ConnectionActivity.token);
                Intent intent = new Intent(LaunchActivity.this, ConnectionActivity.class);
                startActivity(intent);
                finish();
            }
        }
        );


        //Check toutes les 500ms si réponse
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!adversaryName.equals("") && cards.size() > 0) {
                            startGame();
                        }
                        if(displayEndGame){
                            Toast t = Toast.makeText(LaunchActivity.this, endGameMessage, Toast.LENGTH_SHORT);
                            t.show();
                            displayEndGame = false;
                        }
                        if(timeout){
                            ConnectionActivity.token = 0;
                            Intent intent = new Intent(LaunchActivity.this, ConnectionActivity.class);
                            intent.putExtra("timeout_message",timeoutMessage);
                            Log.i("Timeout",timeoutMessage+" : "+timeout);
                            startActivity(intent);
                            timeoutMessage = "";
                            timeout = false;
                            finish();
                        }

                    }

                });
            }

        }, 0, 500);

        //Toutes les 5s envoi packet ImStillHere au serveur
        t2 = new Timer();
        t2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!timeout && ConnectionActivity.token != 0) {
                            new PacketSendImStillHere().call(ConnectionActivity.token);
                        }
                    }

                });
            }

        }, 0, 5000);
    }

    @Override
    protected void onDestroy() {
        Log.i("Destroy","Launch On Destroy");
        if(ConnectionActivity.token != 0)
            new PacketSendLogOut().call(ConnectionActivity.token);
        super.onDestroy();
    }

    @Override
    public void onBackPressed()
    {
        if(!quit){
            Toast t = Toast.makeText(this,R.string.toastQuitLaunch,Toast.LENGTH_SHORT);
            t.show();
            quit = true;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            quit = false;
                        }
                    });
                }
            },4000);
        }else{
            t2.cancel();
            t2.purge();
            t2 = null;
            quit = false;
            finishAffinity();
        }
    }

    public void startGame(){
        Intent intent = new Intent(LaunchActivity.this, GameActivity.class);
        intent.putExtra("adversaryName",adversaryName);
        intent.putExtra("starting",""+starting);
        startActivity(intent);
        WaitingForGameActivity.notifyFinish();
        adversaryName = "";
    }

    public static void notifyStartGame(String advName, String strting){
        adversaryName = advName;
        starting = strting.equals("true");
    }

    public void getDistantCards(){
        Log.i("Parser","Retrieving cards from server ...");
        BackgroundAsyncXMLDownload backgroundAsyncXMLDownload = new BackgroundAsyncXMLDownload(new XMLParser(),this);
        backgroundAsyncXMLDownload.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void initCards(int distantVersion, ArrayList<Card> distantCards){

        Log.i("Parser","distantVersion :"+distantVersion);
        Log.i("Parser","distantCards :"+distantCards.toString());

        File save = new File(getApplicationContext().getFilesDir() + "/cards.xml");
        Log.i("Parser", "File Dir:" + getApplicationContext().getFilesDir() + "/cards.xml");
        if (save.exists()) {
            InputStream resultStream = null;
            try {
                resultStream = new FileInputStream(save);
                Log.i("Parser", "File Dir:" + save.getAbsolutePath());
                cards = xmlParser.parse(resultStream,this);
                version = xmlParser.getVersion();
                resultStream.close();

                Log.i("parser","version : "+version);
                Log.i("parser","cards : "+cards.toString());
            } catch (FileNotFoundException e) {
                Log.e("Parser", Log.getStackTraceString(e));
            } catch (IOException e) {
                Log.e("Parser", Log.getStackTraceString(e));
            } catch (XmlPullParserException e) {
                Log.e("Parser", Log.getStackTraceString(e));
            }
        }
        if(version <= distantVersion){
            cards = distantCards;
            version = distantVersion;
            Log.i("Parser","Loaded version and cards from server");
        }
        saveCards(cards,version);
    }

    public void saveCards(ArrayList<Card> cards, int version){
        Log.i("parser","WRITE version : "+version);
        Log.i("parser","WRITE cards : "+cards.toString());
        try {
            xmlParser.write(this,cards,version);
        } catch (IOException e) {
            Log.e("Parser", Log.getStackTraceString(e));
        }
    }

    public void initAbilities(){
        //Codé en dur pour l'instant, à voir plus tard
        abilities.add(new HealAndDamageAbility(1,"Sort de zone","Un sort de zone qui heal vos cartes et fait des dégats à celles de votre adversaire"));
        abilities.add(new DamageAbility(2,"Sort d'attaque","Un sort d'attaque qui fait des dégats aux cartes de votre adversaire"));
        abilities.add(new HealAbility(3,"Sort de soins","Un sort de soins qui soigne vos cartes"));
    }

    public Ability getAbilityById(int id){
        if(abilities.size()<= 0) return emptyAbility;
        for (Ability a : abilities){
            if(a.getId() == id){
                return a;
            }
        }
        return emptyAbility;
    }

    public static void displayEndGameMessage(String message){
        displayEndGame = true;
        endGameMessage = message;
    }
}
