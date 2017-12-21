package ked.pts3g10;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.Gameplay.Player;

public class ConstructionDeckActivity extends AppCompatActivity {
    private TextView indiceCarte,nbCarteDeck;
    private Button selectionner;
    private ImageButton left_arrow, right_arrow, terminer;
    private ImageView carteVue;
    private int indice=0;
    private List<Integer> carteList = new ArrayList<>();
    private int carte;
    private ConstructionDeckActivity context;
    private int nbCartes=0,nbCarteDeckMax=10;
    private List<Integer> deck = new ArrayList<>();



    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_construction_deck);
        //Liason éléments interface avec variables
        indiceCarte =(TextView) findViewById(R.id.indiceCarte);
        left_arrow = (ImageButton) findViewById(R.id.left_arrow);
        carteVue = (ImageView) findViewById(R.id.carteVue);
        right_arrow = (ImageButton) findViewById(R.id.right_arrow);
        selectionner = (Button) findViewById(R.id.selectionner);
        terminer = (ImageButton) findViewById(R.id.terminer);
        nbCarteDeck= (TextView) findViewById(R.id.NbCarteDeck);
        context=this;


        //Initialisation de l'ensemble des cartes dans la liste
        carteList.add(R.drawable.z_carte_legion);
        //carteList.add(R.drawable.carte_archer);


        //Initialisation affichage de l'indice de la carte actuelle
        String affichageIndice = (indice+1)+"/"+(carteList.size());
        indiceCarte.setText(affichageIndice);

        //Initialisation affichage du nb de carte selectionner
        nbCarteDeck.setText(""+nbCartes);



        //Action Fleche de gauche
        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(indice==0){
                    //Fin de la liste côté gauche
                }else{
                    indice--;
                    //Actualisation de l'affichage en fonction de l'indice
                    String affichageIndice = (indice+1)+"/"+(carteList.size());
                    indiceCarte.setText(affichageIndice);
                    //On récupére l'image présent a l'indice et l'affiche
                    carte=carteList.get(indice);
                    carteVue.setBackgroundResource(carte);
                }
            }
        });


        //Action fleche de droite
        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("indice",""+indice);
                if(indice+1==carteList.size()){
                    //Fin de la liste côté droit
                }else{
                    indice++;
                    //Actualisation de l'affichage en fonction de l'indice
                    String affichageIndice = (indice+1)+"/"+(carteList.size()-1);
                    indiceCarte.setText(affichageIndice);
                    //On récupére l'image présent a l'indice et l'affiche
                    carte=carteList.get(indice);
                    carteVue.setBackgroundResource(carte);
                }
            }
        });


        //Action bouton selectionner
        selectionner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nbCartes==nbCarteDeckMax){
                    //Afficher une alerte
                }else{
                    carte=carteList.get(indice);
                    deck.add(carte);
                    nbCartes++;
                    nbCarteDeck.setText(""+nbCartes);
                }

            }
        });



        //Action bouton terminer
        terminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nbCartes< nbCarteDeckMax){
                    AlertDialog.Builder aBuilder = new AlertDialog.Builder(ConstructionDeckActivity.this);
                    aBuilder.setMessage(R.string.alertNbCartes).setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            remplissageRandom();

                        }
                    }).setNegativeButton("NON", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    AlertDialog alert = aBuilder.create();


                    alert.show();
                }
                else{
                   context.finish();
                }

                //Envoie du deck au serveur
                //
                //

            }
        });


    }


    /***
     *
     * @param player
     */
    public void afficher( Player player){



    }

    public void remplissageRandom(){
        //a implementer

    }



}
