package ked.pts3g10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConnectionActivity extends AppCompatActivity {

    private EditText pseudo,password;
    private Button connection, inscription;
    private String stringPseudo,stringPassword;
    private Boolean connecter=true;//<<<<<<<<<<<<<<<-----------------------Variable pour la connection sur vrais pour le test
    private ConnectionActivity context;

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

        //Ouvre l'activite du jeux et envoie les informations de connection a la bdd
        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringPseudo=pseudo.getText().toString();
                stringPassword=password.getText().toString();
                //Verifie que les champs ne soit pas vide
                if(stringPassword.isEmpty()|stringPseudo.isEmpty()){
                    Toast toast =Toast.makeText(context,R.string.toastChampVide,Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    // envoyer les info vers la bdd
                    //
                    //
                    //Ajout de la condition que doit être identifier
                    if(connecter){
                        Intent launch = new Intent(context,LaunchActivity.class);
                        //Envoie de variable a l'autre fenetre ?
                        //
                        //
                        startActivity(launch);
                        //Empeche le retour en arrière car on ferme l'activité après connection
                        context.finish();
                    }
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



}
