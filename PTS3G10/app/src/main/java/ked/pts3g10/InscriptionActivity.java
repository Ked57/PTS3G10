package ked.pts3g10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InscriptionActivity extends AppCompatActivity {

    private EditText pseudoIns,passwordIns,email,passwordConf;
    private Button creeCompte,annuler;
    private InscriptionActivity context;
    private String stringPseudo,stringPassword,stringEmail,stringPasswordconf;

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

        //affiche la fenetre d'inscription et envoie les donnée d'inscription a la bdd
        creeCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringPseudo=pseudoIns.getText().toString();
                stringPassword=passwordIns.getText().toString();
                stringPasswordconf=passwordConf.getText().toString();
                stringEmail=email.getText().toString();
                //Verifie que les champs ne soit pas vide
                if(stringPseudo.isEmpty()|stringPassword.isEmpty()|stringPasswordconf.isEmpty()|stringEmail.isEmpty()){
                    Toast toast = Toast.makeText(context,R.string.toastChampVide,Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    //verifie si le "password" correspond bien a "confirmed password"
                    if(stringPassword.equals(stringPasswordconf)){
                        //Envoie des informations a la base de donnée et verifie si elles existes
                        //
                        //
                        //
                        //Affiche un toast pour dire création du compte réussie
                        Toast toast = Toast.makeText(context,R.string.toastCreationDuCompte,Toast.LENGTH_LONG);
                        toast.show();
                        //Ferme la fenetre pour retourner sur l'ecran de connection
                        context.finish();

                    }else{
                        Toast toast = Toast.makeText(context,R.string.toastConfirmationPassword,Toast.LENGTH_LONG);
                        toast.show();
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
}
