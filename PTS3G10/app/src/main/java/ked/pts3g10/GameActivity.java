package ked.pts3g10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ked.pts3g10.Gameplay.Board;
import ked.pts3g10.Gameplay.Player;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Board board = new Board(this, new Player(this,"Ked"), new Player(this,"Shyndard")); //Valeurs d'exemple
    }

    public void updateText(int id,String str){
        TextView txt = (TextView) findViewById(id);
        txt.setText(str);
    }
    public void updateText(int id,int i){
        TextView txt = (TextView) findViewById(id);
        txt.setText(i+"");
    }
}
