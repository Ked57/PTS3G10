package ked.pts3g10.Events;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import ked.pts3g10.DeckActivity;


public class DeckTouchEventMgr implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private DeckActivity context;
    private GestureDetector gestureDetector;

    public DeckTouchEventMgr(DeckActivity context){
        this.context = context;
        Log.i("deck","deck");
        gestureDetector = new GestureDetector(context,this);
        gestureDetector.setOnDoubleTapListener(this);
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
