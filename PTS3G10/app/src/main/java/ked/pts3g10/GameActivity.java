package ked.pts3g10;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ked.pts3g10.Events.GameTouchEventMgr;
import ked.pts3g10.Gameplay.Board;
import ked.pts3g10.Gameplay.CardPackage.BoardCard;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.Gameplay.CardPackage.Hero;
import ked.pts3g10.Gameplay.CardPackage.Spell;
import ked.pts3g10.Gameplay.Deck;
import ked.pts3g10.Gameplay.Player;
import ked.pts3g10.Interface.Case;
import ked.pts3g10.Network.packet.PacketEndGame;
import ked.pts3g10.Util.DeckManager;

public class GameActivity extends AppCompatActivity {

    private static Board board;
    private GameTouchEventMgr temgr;
    private static boolean normalFinish, finish;
    private static String message, messageToSend;
    private boolean quit;
    private Case clickedCase;

    public static Deck adversaryDeck = new Deck(new ArrayList<Card>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        String adversaryName = intent.getStringExtra("adversaryName");
        boolean starting = intent.getBooleanExtra("starting",true);
        int adversaryToken = intent.getIntExtra("adversaryToken",0);

        adversaryDeck = DeckManager.getPlayerDeck(adversaryToken,true);

        normalFinish = false;
        finish = false;

        clickedCase = null;

        board = new Board(this, new Player(this,ConnectionActivity.stringPseudo,false,LaunchActivity.playerDeck), new Player(this,adversaryName,true, adversaryDeck),starting);
        temgr = new GameTouchEventMgr(this);

        message = "";
        messageToSend = "";

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(finish) {
                            finish();
                        }
                    }

                });
            }

        }, 0, 500);
    }

    @Override
    protected void onDestroy() {
        WaitingForGameActivity.notifyFinish();
        if(!isNormalFinish()) {
            message = "Une erreur inconnue est arrivée";
            messageToSend = "Une erreur inconnue est arrivée";
        }
        LaunchActivity.initDeck();
        new PacketEndGame().call(messageToSend);
        LaunchActivity.displayEndGameMessage(message);
        board = null;
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
            normalFinish = true;
            messageToSend = "L'adversaire a quitté la partie";
            message = "Vous avez quitté la partie";
            quit = false;
            finish();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Case c = (Case)v;
        if(!c.isCardThumbnailEmpty()) {
            if(c.getCard() instanceof Hero){
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.case_menu_hero, menu);
                clickedCase = c;
            }else if(c.getCard() instanceof BoardCard) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.case_menu_boardcard, menu);
                clickedCase = c;
            }
        }
    }

    public void onInfoClick(MenuItem item){
        if(clickedCase != null){
            if(!clickedCase.isCardThumbnailEmpty()){
                getBoard().getPlayer().getPlayerAction().resetActionState();
                Intent intent = new Intent(this, DeckActivity.class);
                intent.putExtra("info",true);
                intent.putExtra("idCard",clickedCase.getCard().getId());
                startActivity(intent);
            }
        }
    }

    public void onUseSpellClick(MenuItem item) {
        if (clickedCase != null) {
            if (!clickedCase.isCardThumbnailEmpty() && clickedCase.getCard() instanceof Hero) {
                board.clearBoardActions();
                board.getPlayer().getPlayerAction().chooseSpellAimPoint(clickedCase.getCard());
            }
        }
    }

    // Get a MemoryInfo object for the device's current memory status.
    public ActivityManager.MemoryInfo getAvailableMemory() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }

    public void updateText(int id,String str){
        TextView txt = (TextView) findViewById(id);
        txt.setText(str);
    }
    public void updateText(int id,int i){
        TextView txt = (TextView) findViewById(id);
        txt.setText(i+"");
    }

    public static Board getBoard(){ return board;}

    public static void endGame(String msg, String msgToSend){
        message = msg;
        messageToSend = msgToSend;
        finish = true;
    }

    public boolean isNormalFinish() {
        return normalFinish;
    }

    public static void setNormalFinish(boolean normalFnsh) {
        normalFinish = normalFnsh;
    }
}
