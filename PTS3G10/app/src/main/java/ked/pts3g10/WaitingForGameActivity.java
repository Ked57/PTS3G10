package ked.pts3g10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ked.pts3g10.R;

public class WaitingForGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargement);
        ActivityMgr.waitingForGameActivity = this;
    }

    public void startGame(String adversaryName, String starting){
        Intent intent = new Intent(WaitingForGameActivity.this, GameActivity.class);
        intent.putExtra("adversaryName",adversaryName);
        intent.putExtra("starting",""+starting);
        startActivity(intent);
    }
}
