package ked.pts3g10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ked.pts3g10.Network.packet.PacketLeaveWaitingList;


public class WaitingForGameActivity extends AppCompatActivity {

    private boolean quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargement);
        ActivityMgr.waitingForGameActivity = this;
        quit = false;
    }

    @Override
    public void onBackPressed()
    {
        if(!quit){
            Toast t = Toast.makeText(this,R.string.toastQuitLaunch,Toast.LENGTH_SHORT);
            t.show();
            quit = true;
        }else{
            quit = false;
            new PacketLeaveWaitingList().call(ConnectionActivity.token);
            finish();
        }

    }

    public void notifyFinish(){finish();}
}
