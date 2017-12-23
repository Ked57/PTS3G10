package ked.pts3g10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ked.pts3g10.Gameplay.CardPackage.Army;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.Util.BackgroundAsyncXMLDownload;
import ked.pts3g10.Util.XMLParser;


public class LaunchActivity extends AppCompatActivity {


    private static String adversaryName = "";
    private static boolean starting = true;
    public static ArrayList<Card> cards = new ArrayList<>();
    private Timer t;
    private XMLParser xmlParser;
    private int version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        xmlParser = new XMLParser();
        version = 0;

        getDistantCards();


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
                if(cards.size() > 0) {
                    Intent intent = new Intent(LaunchActivity.this, GameActivity.class);
                    intent.putExtra("adversaryName", "Dev");
                    intent.putExtra("starting", "true");
                    startActivity(intent);
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

        ActivityMgr.launchActivity = this;

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

    public void startGame(){
        Intent intent = new Intent(LaunchActivity.this, GameActivity.class);
        intent.putExtra("adversaryName",adversaryName);
        intent.putExtra("starting",""+starting);
        startActivity(intent);
    }

    public void getDistantCards(){
        BackgroundAsyncXMLDownload backgroundAsyncXMLDownload = new BackgroundAsyncXMLDownload(new XMLParser(),this);
        backgroundAsyncXMLDownload.execute("http://vps238052.ovh.net/cards.xml");
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
                cards = xmlParser.parse(resultStream);
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
}
