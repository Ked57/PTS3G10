package ked.pts3g10;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import ked.pts3g10.Network.Communication;
import ked.pts3g10.Network.packet.PacketAuth;

public class ConnectionActivity extends AppCompatActivity {

    private EditText pseudo,password;
    private Button connection, inscription, dev;
    private String stringPassword;
    public static String stringPseudo;
    private ConnectionActivity context;
    public static Communication com;
    public static boolean connected;
    public static int token;

    private boolean emptyError, unvalidCharError;

    public Timer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        //On lie les élèments de l'interface a nos objets
        pseudo = (EditText)findViewById(R.id.pseudo);
        password =(EditText)findViewById(R.id.password);
        connection = (Button) findViewById(R.id.buttonconnection);
        inscription = (Button) findViewById(R.id.buttoninscription);
        context=this;

        token = 0;
        connected = false;

        emptyError = false;
        unvalidCharError = false;

        StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                .permitNetwork()
                .build());

        if(com == null){
            com = new Communication();
            com.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        String str = getIntent().getStringExtra("timeout_message");
        if(str != null){
            Toast t = Toast.makeText(this,str,Toast.LENGTH_LONG);
            t.show();
        }


        //Ouvre l'activite du jeux et envoie les informations de connection a la bdd
        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextNormal(pseudo);
                editTextNormal(password);


                stringPseudo=pseudo.getText().toString();
                stringPassword=password.getText().toString();
                //Verifie que les champs ne soit pas vide
                if(stringPassword.isEmpty()){
                    emptyError = true;
                    editTextError(password);
                }
                if(stringPseudo.isEmpty()) {
                    emptyError = true;
                    editTextError(pseudo);
                }
                if(stringPseudo.contains(":")){
                    unvalidCharError = true;
                    editTextError(pseudo);
                }
                if(stringPassword.contains(":")){
                    unvalidCharError = true;
                    editTextError(password);
                }
                if(emptyError){
                    Toast toast = Toast.makeText(context,R.string.toastChampVide,Toast.LENGTH_LONG);
                    toast.show();
                    emptyError = false;
                }else if(unvalidCharError){
                    Toast t = Toast.makeText(context,R.string.toastCaractereInvalide,Toast.LENGTH_LONG);
                    t.show();
                    unvalidCharError = false;
                }
                else{
                    // envoyer les info vers la bdd
                    new PacketAuth().call(stringPseudo,stringPassword);
                    //Ouvre la popup

                    //Check toutes les 500ms si réponse
                    t = new Timer();
                    t.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(connected && token > 0){
                                        toLauncher();
                                    }else if(token == -1){
                                        connectionFailed();
                                    }
                                }

                            });
                        }

                    }, 0, 500);
                }
            }
        });

        //Button inscription ouvre l'activitie inscription
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ouvre la fenetre d'inscription
                Intent inscription = new Intent(context,InscriptionActivity.class);
                startActivity(inscription);
                //On ne ferme pas cet activity pour eviter de la rapeller après l'inscription
            }
        });

    }


    public static Communication getCom() {
        return com;
    }

    public static String getSeparator() {
        return ":";
    }

    public static void connectionCallBack(int tokenReceived){
        if(tokenReceived < 0){
            //Connexion a échouée
            token = -1;
        }else{
            //Connexion réussie
            connected = true;
            token = tokenReceived;
        }
    }

    public void toLauncher(){
        if(t != null) {
            t.purge();
            t.cancel();
            t = null;
        }
        connected = false;
        //Ajout de la condition que doit être identifié
        Intent launch = new Intent(context,LaunchActivity.class);
        //Envoie de variable a l'autre fenetre ?

        startActivity(launch);
        //Empeche le retour en arrière car on ferme l'activité après connection
        finish();
    }

    public void connectionFailed(){
        t.purge();
        t.cancel();
        t = null;

        Toast t = Toast.makeText(context,R.string.toastConnectionFailed ,Toast.LENGTH_LONG);
        t.show();
        token = 0;
        editTextError(pseudo);
        editTextError(password);
    }

    public void editTextError(EditText e){
        e.getBackground().setTint(getResources().getColor(R.color.colorRed));
    }
    public void editTextNormal(EditText e){
        e.getBackground().setTint(getResources().getColor(R.color.colorPrimary));
    }

}
