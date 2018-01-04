package ked.pts3g10;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ked.pts3g10.Gameplay.AbilityPackage.Ability;
import ked.pts3g10.Gameplay.AbilityPackage.DamageAbility;
import ked.pts3g10.Gameplay.AbilityPackage.HealAbility;
import ked.pts3g10.Gameplay.AbilityPackage.HealAndDamageAbility;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.Gameplay.Deck;
import ked.pts3g10.Network.packet.PacketJoinGameWaitingList;
import ked.pts3g10.Network.packet.PacketSendImStillHere;
import ked.pts3g10.Network.packet.PacketSendLogOut;
import ked.pts3g10.Util.BackgroundAsyncXMLDownload;
import ked.pts3g10.Util.DeckManager;
import ked.pts3g10.Util.Preferences;
import ked.pts3g10.Util.XMLParser;


public class LaunchActivity extends AppCompatActivity {

   public final HealAndDamageAbility emptyAbility = new HealAndDamageAbility(0,null,null);

    private static String adversaryName = "";
    private static boolean starting = true;
    public static ArrayList<Card> cards = new ArrayList<>();
    public static Deck playerDeck;
    private Timer t, t2;
    private XMLParser xmlParser;
    private int version;
    public static ArrayList<Ability> abilities = new ArrayList<>();
    private boolean quit;

    public static boolean displayEndGame;
    public static String endGameMessage;
    public static boolean timeout;
    public static String timeoutMessage;
    public static int adversaryToken;
    public static boolean saveCards;

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

        saveCards = false;

        initAbilities();
        initCards(getDistantVersion());

        playerDeck = DeckManager.getPlayerDeck(ConnectionActivity.token,false);

        findViewById(R.id.playButton).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(cards.size() > 0 && playerDeck.getCardList().size() >= 10) {
                     new PacketJoinGameWaitingList().call();
                     Intent intent = new Intent(LaunchActivity.this, WaitingForGameActivity.class);
                     startActivity(intent);
                 }else if(playerDeck.getCardList().size() < 10){
                     Toast t = Toast.makeText(LaunchActivity.this,R.string.toastUncompleteDeck,Toast.LENGTH_SHORT);
                     t.show();
                 } else {
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
                if(cards.size() > 0) {
                    Intent intent = new Intent(LaunchActivity.this, ConstructionDeckActivity.class);
                    startActivity(intent);
                } else {
                    Toast t = Toast.makeText(LaunchActivity.this,R.string.toastLoadingCards,Toast.LENGTH_SHORT);
                    t.show();
                }
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
                intent.putExtra("logout",true);
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
                        if(saveCards){
                            saveCards();
                            saveCards = false;
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
        intent.putExtra("starting",starting);
        intent.putExtra("adversaryToken",adversaryToken);
        startActivity(intent);
        WaitingForGameActivity.notifyFinish();
        adversaryName = "";
    }

    public static void notifyStartGame(String advName, int token, String strting){
        adversaryName = advName;
        adversaryToken = token;
        starting = strting.equals("true");
    }

    public int getDistantVersion(){
        int version = 0;
        try {
            String url = "http://shyndard.eu/iut/s3/card-version.php";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Log.i("DATA", response.toString());
            for(String str : response.toString().split(",")) {
                version = Integer.parseInt(str);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return version;
    }

    public void getDistantCards(){
        Log.i("Parser","Retrieving cards from server ...");
        BackgroundAsyncXMLDownload backgroundAsyncXMLDownload = new BackgroundAsyncXMLDownload(new XMLParser(),this);
        backgroundAsyncXMLDownload.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void initCards(int distantVersion){
        version = Preferences.getVersion(this);
        Log.i("Parser","Local version is "+version+", distant version is "+distantVersion);
        if(version < distantVersion){
            version = distantVersion;
            getDistantCards();
            Log.i("Parser","Loaded version and cards from server");
        }else{
            File save = new File(getApplicationContext().getFilesDir() + "/cards.xml");
            Log.i("Parser", "File Dir:" + getApplicationContext().getFilesDir() + "/cards.xml");
            if (!save.exists()) getDistantCards();
            else {
                InputStream resultStream = null;
                try {
                    resultStream = new FileInputStream(save);
                    Log.i("Parser", "File Dir:" + save.getAbsolutePath());
                    cards = xmlParser.parse(resultStream, this);
                    resultStream.close();
                    Log.i("parser", "version : " + version);
                    Log.i("parser", "cards : " + cards.toString());
                } catch (FileNotFoundException e) {
                    Log.e("Parser", Log.getStackTraceString(e));
                } catch (IOException e) {
                    Log.e("Parser", Log.getStackTraceString(e));
                } catch (XmlPullParserException e) {
                    Log.e("Parser", Log.getStackTraceString(e));
                }
            }
        }
        Log.i("Parser","distantVersion :"+distantVersion);
    }

    public void saveCards(){
        Log.i("parser","WRITE version : "+version);
        try {
            xmlParser.write(this,cards);
            Preferences.setVersion(this,version);
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
