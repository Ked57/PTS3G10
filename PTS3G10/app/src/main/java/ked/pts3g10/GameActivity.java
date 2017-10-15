package ked.pts3g10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ked.pts3g10.DB.CardDB;
import ked.pts3g10.Events.GameTouchEventMgr;
import ked.pts3g10.Gameplay.Board;
import ked.pts3g10.Gameplay.Deck;
import ked.pts3g10.Gameplay.Player;

public class GameActivity extends AppCompatActivity {

    private Board board;
    private GameTouchEventMgr temgr;

    private CardDB cardDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        cardDb = new CardDB(this);


        board = new Board(this, new Player(this,"Ked",cardDb.select()), new Player(this,"Shyndard",cardDb.select())); //Valeurs d'exemple
        temgr = new GameTouchEventMgr(this);


    }

    @Override
    protected void onDestroy() {
        cardDb.disconnect();
        super.onDestroy();
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
