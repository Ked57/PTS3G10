package ked.pts3g10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import ked.pts3g10.R;

public class WaitingForGameActivity extends AppCompatActivity {

    private static Intent game;
    private static boolean rdy;
    private Timer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargement);
        ActivityMgr.waitingForGameActivity = this;
    }

    public void notifyStartGame(String adversaryName, String starting){
        Log.i("Network","Starting a new game with "+adversaryName+", "+starting);
        Intent intent = new Intent(ActivityMgr.waitingForGameActivity, GameActivity.class);
        intent.putExtra("adversaryName",adversaryName);
        intent.putExtra("starting",starting);
        startActivity(intent);
    }
}
