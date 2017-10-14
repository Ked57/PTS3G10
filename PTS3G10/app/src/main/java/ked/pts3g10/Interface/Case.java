package ked.pts3g10.Interface;

/*

L'intérêt ici est d'extend FrameLayout et de construire notre interface procéduralement avec des objets case

 */

import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ked.pts3g10.GameActivity;
import ked.pts3g10.R;

public class Case extends FrameLayout{

    private ImageView grass;
    private ImageView cardThumbnail; //Pas inspiré pour ce nom, représente l'image de la carte sur la case
    private GameActivity context;
    private boolean isOccupied;

    public Case(GameActivity context){
        super(context);
        this.context = context;
        isOccupied=false;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,1);
        this.setLayoutParams(lp);


        /* setPadding n'utilise que les pixels
            on va donc faire une conversion grâce à la densité de l'écran
            pour avoir un équivalent dp mais en pixel
         */
        int paddingPixel = 2;
        float density = context.getResources().getDisplayMetrics().density;
        int paddingDp = (int)(paddingPixel * density);
        this.setPadding(paddingDp,paddingDp,paddingDp,paddingDp);

        grass = new ImageView(context);
        grass.setBackgroundResource(R.drawable.grass);
        this.addView(grass);

        cardThumbnail = new ImageView(context);
        this.addView(cardThumbnail);
    }

    public boolean setCardThumbnail(int id){
        if(!isOccupied){ // On ne veut pas supperposer les cartes !
            cardThumbnail.setBackgroundResource(id);
            cardThumbnail.bringToFront();
            isOccupied = true;
            return true;
        }else return false;
    }
}
