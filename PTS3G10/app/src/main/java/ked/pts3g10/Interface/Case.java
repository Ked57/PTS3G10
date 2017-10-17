package ked.pts3g10.Interface;

/*

L'intérêt ici est d'extend FrameLayout et de construire notre interface procéduralement avec des objets case

 */

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.GameActivity;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.Gameplay.PlayerAction;
import ked.pts3g10.R;

public class Case extends FrameLayout {

    private ImageView grass;
    private ImageView cardThumbnail; //Pas inspiré pour ce nom, représente l'image de la carte sur la case
    private Card card;
    private GameActivity context;
    private boolean isActionable;

    public Case(GameActivity context){
        super(context);
        this.context = context;
        isActionable = false;

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

        card = null;

        this.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onClickAction();
            }
        });
    }

    public void setCard(Card card){
        this.card = card;
        setCardThumbnail((int) card.getThumbnail().getTag());
    }

    public void resetCard(){
        this.card = null;
        setCardThumbnail(0);
    }

    public boolean setCardThumbnail(int id){
        if(isCardThumbnailEmpty()){ // On ne veut pas supperposer les cartes !
            cardThumbnail.setBackgroundResource(id);
            cardThumbnail.bringToFront();
            return true;
        }else return false;
    }

    public boolean isCardThumbnailEmpty(){
        if(cardThumbnail.getBackground() == null){
            return true;
        }else return false;
    }

    public void setCaseActionable(int color){
        this.isActionable = true;
        this.setBackgroundResource(color);
    }
    public void setCaseNonActionable(){
        this.isActionable = false;
        setBackgroundResource(0);
    }

    public void onClickAction(){

        PlayerAction pa = ActivityMgr.gameActivity.getBoard().getPlayer().getPlayerAction();
        if(isActionable && isCardThumbnailEmpty() && pa.getActionState() == 1){ // Choosing state
            Log.i("card",pa.getChooseInitialCaseCard().getThumbnail().getTag()+"");
            pa.placeBoardCard(pa.getChooseInitialCaseCard(),this);
        }else if(isActionable && isCardThumbnailEmpty() && pa.getActionState() == 2){
            /* TODO: Move or attack */
        }
    }
}
