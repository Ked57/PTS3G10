package ked.pts3g10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import ked.pts3g10.DB.CardDB;
import ked.pts3g10.Events.GameTouchEventMgr;
import ked.pts3g10.Gameplay.Board;
import ked.pts3g10.Gameplay.Deck;
import ked.pts3g10.Gameplay.Player;

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

        board = new Board(this, new Player(this,"Ked",false), new Player(this,adversaryName,true),b); //Valeurs d'exemple
        temgr = new GameTouchEventMgr(this);

        ActivityMgr.gameActivity = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.newRound:
                board.newRound();
                return true;
            case R.id.populate:
                board.populate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
}
