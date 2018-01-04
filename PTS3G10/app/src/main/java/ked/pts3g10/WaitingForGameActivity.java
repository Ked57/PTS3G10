package ked.pts3g10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import ked.pts3g10.Network.packet.PacketLeaveWaitingList;


public class WaitingForGameActivity extends AppCompatActivity {

    private static boolean quit,backQuit;
    private int second, minute;
    private Timer t, time;
    private TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargement);
        quit = false;
        backQuit = false;
        second = minute = 0;
        timer = (TextView) findViewById(R.id.textTimeWaiting);

        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(quit) {
                            finish();
                        }
                    }

                });
            }

        }, 0, 500);

        time = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        second++;
                        if(second == 60) {
                            second = 0;
                            minute++;
                        }
                        timer.setText((minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second);
                    }

                });
            }

        }, 0, 1000);
    }

    @Override
    public void onDestroy() {
        t.cancel();
        t.purge();
        time.cancel();
        time.purge();
        t = null;
        time = null;
        super.onDestroy();
    }

    @Override
    public void onBackPressed()
    {
        if(!backQuit){
            Toast t = Toast.makeText(this,R.string.toastQuitLaunch,Toast.LENGTH_SHORT);
            t.show();
            backQuit = true;
        }else{
            backQuit = false;
            new PacketLeaveWaitingList().call(ConnectionActivity.token);
            finish();
        }

    }

    public static void notifyFinish(){quit = true;}
}
