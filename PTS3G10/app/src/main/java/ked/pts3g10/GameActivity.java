package ked.pts3g10;

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

import ked.pts3g10.Events.GameTouchEventMgr;
import ked.pts3g10.Gameplay.Board;
import ked.pts3g10.Gameplay.Deck;
import ked.pts3g10.Gameplay.Player;
import ked.pts3g10.Interface.Case;
import ked.pts3g10.Network.packet.PacketEndGame;
import ked.pts3g10.Network.packet.PacketEndRound;

public class GameActivity extends AppCompatActivity {

    private Board board;
    private GameTouchEventMgr temgr;

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

        board = new Board(this, new Player(this,ConnectionActivity.stringPseudo,false), new Player(this,adversaryName,true),b); //Valeurs d'exemple
        temgr = new GameTouchEventMgr(this);

        ActivityMgr.gameActivity = this;
    }

    @Override
    protected void onDestroy() {
        ActivityMgr.waitingForGameActivity.notifyFinish();
        new PacketEndGame().call();
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

    public void updateText(int id,String str){
        TextView txt = (TextView) findViewById(id);
        txt.setText(str);
    }
    public void updateText(int id,int i){
        TextView txt = (TextView) findViewById(id);
        txt.setText(i+"");
    }

    public Board getBoard(){ return board;}

    public void endGame(String message){
        ActivityMgr.launchActivity.displayEndGameMessage(message);
        finish();
    }
}
