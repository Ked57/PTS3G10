package ked.pts3g10;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import ked.pts3g10.Events.GameTouchEventMgr;
import ked.pts3g10.Gameplay.Board;
import ked.pts3g10.Gameplay.Deck;
import ked.pts3g10.Gameplay.Player;
import ked.pts3g10.Interface.Case;
import ked.pts3g10.Network.packet.PacketEndGame;
import ked.pts3g10.Network.packet.PacketEndRound;
import ked.pts3g10.Network.packet.PacketSendImStillHere;

public class GameActivity extends AppCompatActivity {

    private static Board board;
    private GameTouchEventMgr temgr;
    private static boolean normalFinish, finish;
    private static String message, messageToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        String adversaryName = intent.getStringExtra("adversaryName");
        String starting = intent.getStringExtra("starting");
        boolean b;
        if(starting.equals("true")) b = true;
        else b = false;
        normalFinish = false;
        finish = false;

        board = new Board(this, new Player(this,ConnectionActivity.stringPseudo,false), new Player(this,adversaryName,true),b); //Valeurs d'exemple
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
        new PacketEndGame().call(messageToSend);
        LaunchActivity.displayEndGameMessage(message);
        board = null;
        super.onDestroy();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(((Case)v).isHero()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.case_menu, menu);
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
