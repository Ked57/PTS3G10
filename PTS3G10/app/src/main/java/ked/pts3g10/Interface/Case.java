package ked.pts3g10.Interface;

/*

L'intérêt ici est d'extend FrameLayout et de construire notre interface procéduralement avec des objets case

 */


import android.app.ActivityManager;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.GameActivity;
import ked.pts3g10.Gameplay.CardPackage.BoardCard;
import ked.pts3g10.Gameplay.CardPackage.Hero;
import ked.pts3g10.Gameplay.CardPackage.Spell;
import ked.pts3g10.Gameplay.PlayerAction;
import ked.pts3g10.Network.packet.PacketPlayCard;
import ked.pts3g10.R;
import ked.pts3g10.Util.Pos;

public class Case extends FrameLayout {

    private ImageView grass;
    private ImageView effect;
    private ImageView cardThumbnail; //Pas inspiré pour ce nom, représente l'image de la carte sur la case
    private TextView healthPointsView;
    private BoardCard card;
    private GameActivity context;
    private boolean isActionable;
    private Pos pos;

    private boolean resetCard;
    private boolean updateHp;
    private int new_hp;


    public Case(GameActivity context,Pos pos){
        super(context);
        this.context = context;
        isActionable = false;
        this.pos = pos;

        resetCard = false;
        updateHp = false;
        new_hp = 0;

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

        effect = new ImageView(context);
        this.addView(effect);

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

    public Pos getPos(){return pos;}

    public boolean isHero(){ return card instanceof Hero;}

    public boolean isPlayersCastle(){ return pos.getPosX() == 2 && pos.getPosY()== 4;}
    public boolean isAdversaryCastle(){ return pos.getPosX() == 2 && pos.getPosY() == 0;}

    public void setCard(GameActivity context, BoardCard card){
        this.card = card;
        Log.i("card","SetCard("+pos.getPosY()+";"+pos.getPosY()+") card is null "+ (card == null));
        Log.i("card","SetCard("+pos.getPosY()+";"+pos.getPosY()+") thumbnail tag = "+ card.getThumbnail());
        Log.i("card","SetCard("+pos.getPosY()+";"+pos.getPosY()+") thumbnail empty = "+ isCardThumbnailEmpty());
        if(setCardThumbnail(card.getThumbnail()))
            Log.i("card","setCardThumbnailSuccessful");
        else Log.i("card","setCartThumbnail not sucessful");
        setHealthPointsView(context,card.getHealthPoints());
    }

    public void resetCard(){
        this.card = null;
        setCardThumbnail(0);
        this.removeView(healthPointsView);
        healthPointsView = null;
    }

    public void setHealthPointsView(GameActivity context, int hp){
        healthPointsView = new TextView(context);
        this.addView(healthPointsView);
        healthPointsView.setText(""+hp);
        healthPointsView.setTypeface(null,Typeface.BOLD);
        healthPointsView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
    }

    public boolean setCardThumbnail(int id){
        if(isCardThumbnailEmpty()){ // On ne veut pas superposer les cartes !
            cardThumbnail.setBackgroundResource(id);
            cardThumbnail.bringToFront();
            return true;
        }else if(id == 0) {
            cardThumbnail.setBackgroundResource(id);
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

    public int getXDistanceWith(Case c){
        int dist = c.pos.getPosX()-pos.getPosX();
        if(dist < 0)
            dist = -dist;
        return dist;
    }

    public int getYDistanceWith(Case c){
        int dist = c.pos.getPosY()-pos.getPosY();
        if(dist < 0)
            dist = -dist;
        return dist;
    }


    public BoardCard getCard() { return card; }

    public void updateHp(int hp){
        if(!isCardThumbnailEmpty()){
            healthPointsView.setText(""+hp);
        }
    }

    public void onClickAction(){
        Toast t;
        if(GameActivity.getBoard().isPlayersTurn()) {
            PlayerAction pa = GameActivity.getBoard().getPlayer().getPlayerAction();
            if(pa.getActionState() == 2 && pa.getMovingFrom().equals(this)){
                pa.resetActionState();
            }
            else if (isActionable && pa.getActionState() == 1) { // Choosing state
                if(isCardThumbnailEmpty() && pa.getCaseCard() instanceof BoardCard) {
                    new PacketPlayCard().call(ConnectionActivity.token,GameActivity.getBoard().getPlayer().getDeck().getCardList().indexOf(pa.getCaseCard()),pos);
                    pa.placeBoardCard(context, (BoardCard) pa.getCaseCard(), this);
                }
                else if(pa.getCaseCard() instanceof Spell)
                    pa.useSpellCard(this);
            } else if (!isCardThumbnailEmpty() && card != null && pa.getActionState() != 2) {
                if(!card.hasMovedThisRound() && !card.isAdversary())
                    pa.chooseCaseToGoTo(this);//On affiche les cases où on peut aller
                else if(card.isAdversary()){
                    t = Toast.makeText(context,context.getString(R.string.game_not_your_card),Toast.LENGTH_SHORT);
                    t.show();
                }
                else {
                    t = Toast.makeText(context,context.getResources().getString(R.string.game_already_moved),Toast.LENGTH_SHORT);
                    t.show();
                }
            } else if (isActionable && pa.getActionState() == 2) {
                if(isCardThumbnailEmpty())
                    pa.moveCard(context, (BoardCard)pa.getCaseCard(), this);
                else pa.attack(this);
            }
        }else{
            t = Toast.makeText(context,context.getResources().getString(R.string.deck_choice_wrong),Toast.LENGTH_SHORT);
            t.show();

        }
    }

    public void notifyUpdateHp(){
        if(updateHp) {
            updateHp(new_hp);
            updateHp = false;
        }
    }

    public void notifyResetCard(){
        if(resetCard){
            resetCard();
            resetCard = false;
        }
    }

    public void playHealAnimation(){
        ActivityManager.MemoryInfo memoryInfo = context.getAvailableMemory();
        if (!memoryInfo.lowMemory) {
            //effect.setBackground(BitmapDecoder.decodeSampledBitmapFromResource(getResources(), R.drawable.heal_effect, this.getWidth(), this.getHeight()));
            effect.setBackgroundResource(R.drawable.heal_effect);
            // Get the background, which has been compiled to an AnimationDrawable object.
            AnimationDrawable frameAnimation = (AnimationDrawable) effect.getBackground();
            // Start the animation
            frameAnimation.start();
        }
    }

    public void playFireAnimation(){
        ActivityManager.MemoryInfo memoryInfo = context.getAvailableMemory();
        if (!memoryInfo.lowMemory) {
            //effect.setBackground(BitmapDecoder.decodeSampledBitmapFromResource(getResources(), R.drawable.heal_effect, this.getWidth(), this.getHeight()));
            try {
                effect.setBackgroundResource(R.drawable.fire_effect);
            }catch(OutOfMemoryError e){
                Log.e("Animation",Log.getStackTraceString(e));
            }
            // Get the background, which has been compiled to an AnimationDrawable object.
            AnimationDrawable frameAnimation = (AnimationDrawable) effect.getBackground();
            // Start the animation
            frameAnimation.start();
        }
    }

    public boolean isResetCard() {
        return resetCard;
    }

    public void setResetCard(boolean resetCard) {
        this.resetCard = resetCard;
    }

    public boolean isUpdateHp() {
        return updateHp;
    }

    public void setUpdateHp(boolean updateHp) {
        this.updateHp = updateHp;
    }


    public void setNew_hp(int new_hp) {
        this.new_hp = new_hp;
    }
}
