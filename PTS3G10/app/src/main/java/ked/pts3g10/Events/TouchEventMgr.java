package ked.pts3g10.Events;

import android.view.View;
import android.widget.Button;

import ked.pts3g10.GameActivity;
import ked.pts3g10.R;

public class TouchEventMgr {

    private GameActivity context;
    private Button endRoundButton;

    public TouchEventMgr(final GameActivity context){
        this.context = context;
        endRoundButton = (Button) context.findViewById(R.id.EndButton);
        endRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               context.getBoard().onEndRoundButtonClick();
            }
        });
    }


}
