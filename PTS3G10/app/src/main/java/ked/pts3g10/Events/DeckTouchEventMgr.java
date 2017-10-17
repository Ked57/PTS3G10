package ked.pts3g10.Events;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ked.pts3g10.ActivityMgr;
import ked.pts3g10.DeckActivity;
import ked.pts3g10.Gameplay.Board;
import ked.pts3g10.Gameplay.CardPackage.BoardCard;
import ked.pts3g10.Gameplay.Player;
import ked.pts3g10.R;


public class DeckTouchEventMgr implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private DeckActivity context;
    private GestureDetector gestureDetector;
    private Button deckChoiceButton;

    public DeckTouchEventMgr(final DeckActivity context){
        this.context = context;
        Log.i("deck","deck");
        gestureDetector = new GestureDetector(context,this);
        gestureDetector.setOnDoubleTapListener(this);

        deckChoiceButton = (Button) context.findViewById(R.id.deckChoiceButton);
        deckChoiceButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Player player = ActivityMgr.gameActivity.getBoard().getPlayer();
                Board board = ActivityMgr.gameActivity.getBoard();
                if(board.isPlayersTurn() && player.getCrystals() > player.getDeck().getCardList().get(context.getCurrIndex()).getCrystalCost()) {
                    player.getPlayerAction().chooseInitialCase(player.getDeck().getCardList().get(context.getCurrIndex()));
                    context.finish();
                }else {
                    Toast t = Toast.makeText(context,context.getResources().getString(R.string.deck_choice_wrong), Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }

    public void setOnTouchEvent(MotionEvent event){
        gestureDetector.onTouchEvent(event);
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float dir = e2.getX() - e1.getX();
        if(dir < 0)
            context.swipeRight();
        else context.swipeLeft();
        return false;
    }
}
