package ked.pts3g10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import ked.pts3g10.Network.packet.PacketSendRegister;

public class InscriptionActivity extends AppCompatActivity {

    private EditText pseudoIns,passwordIns,email,passwordConf;
    private Button creeCompte,annuler;
    private InscriptionActivity context;
    private String stringPseudo,stringPassword,stringEmail,stringPasswordconf;

    public static boolean registered;
    public static String registerMessage;

    private Timer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        //Lie les elements de l'activity inscription au objet java
        pseudoIns = (EditText)findViewById(R.id.pseudoIns);
        passwordIns = (EditText)findViewById(R.id.passwordIns);
        email = (EditText)findViewById(R.id.email);
        passwordConf = (EditText)findViewById(R.id.mdpconf);
        creeCompte = (Button) findViewById(R.id.buttoncreationcompte);
        annuler = (Button) findViewById(R.id.buttonannuler);
        context=this;

        registered = false;
        registerMessage = "";

        //affiche la fenetre d'inscription et envoie les donnée d'inscription a la bdd
        creeCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stringPseudo=pseudoIns.getText().toString();
                stringPassword=passwordIns.getText().toString();
                stringPasswordconf=passwordConf.getText().toString();
                stringEmail=email.getText().toString();
                //Verifie que les champs ne soit pas vide
                if(stringPseudo.isEmpty()|stringPassword.isEmpty()|stringPasswordconf.isEmpty()|stringEmail.isEmpty()||stringEmail.isEmpty()){
                    Toast toast = Toast.makeText(context,R.string.toastChampVide,Toast.LENGTH_LONG);
                    toast.show();
                }else if(stringPseudo.contains(":") || stringPassword.contains(":") || stringPasswordconf.contains(":") || stringEmail.contains(":")){
                    Toast toast = Toast.makeText(context,R.string.toastCaractereInvalide,Toast.LENGTH_LONG);
                    toast.show();
                }else if(!stringEmail.contains("@")){
                    Toast toast = Toast.makeText(context,R.string.toastEmailInvalide,Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    //verifie si le "password" correspond bien a "confirmed password"
                    if(stringPassword.equals(stringPasswordconf)){
                        //Envoie des informations a la base de donnée et verifie si elles existes
                        new PacketSendRegister().call(stringPseudo,stringEmail,stringPassword);
                        //Check toutes les 500ms si réponse
                        t = new Timer();
                        t.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                      if (registered){
                                          Intent intent = new Intent(InscriptionActivity.this, LaunchActivity.class);
                                          startActivity(intent);
                                          registered = false;
                                          finish();
                                      }else if(!registerMessage.equals("")){
                                          Toast toast = Toast.makeText(context,registerMessage,Toast.LENGTH_LONG);
                                          toast.show();
                                          registerMessage = "";
                                          t.purge();
                                          t.cancel();
                                          t = null;
                                      }
                                    }

                                });
                            }

                        }, 0, 500);

                    }else{
                        Toast toast = Toast.makeText(context,R.string.toastConfirmationPassword,Toast.LENGTH_LONG);
                        toast.show();
                        if(Integer.valueOf(android.os.Build.VERSION.SDK)>21) {
                            passwordConf.getBackground().setTint(getResources().getColor(R.color.colorRed));
                        }
                    }
                }


            }
        });

        //affiche la fenetre d'inscription
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ferme la fenetre pour retourner sur l'ecran de connection
                context.finish();
            }
        });

    }

    @Override
    public void onDestroy() {
        if(t != null){
            t.purge();
            t.cancel();
            t = null;
        }
        super.onDestroy();
    }
}
