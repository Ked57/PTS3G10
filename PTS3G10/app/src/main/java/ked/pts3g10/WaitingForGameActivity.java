package ked.pts3g10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class WaitingForGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargement);
        ActivityMgr.waitingForGameActivity = this;
    }

    public void notifyFinish(){finish();}
}
