package ked.pts3g10.Events;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;


import ked.pts3g10.DeckActivity;
import ked.pts3g10.GameActivity;
import ked.pts3g10.Gameplay.Deck;
import ked.pts3g10.R;

public class GameTouchEventMgr {

    private GameActivity context;
    private Button endRoundButton;
    private FrameLayout playerCardsFrame;

    public GameTouchEventMgr(final GameActivity context){
        this.context = context;

        endRoundButton = (Button) context.findViewById(R.id.EndButton);
        endRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               context.getBoard().onEndRoundButtonClick();
            }
        });

        playerCardsFrame = (FrameLayout) context.findViewById(R.id.playerCardsFrame);
        playerCardsFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Deck d = context.getBoard().getPlayer().getDeck();
                if(d.getCardList().size() > 0) {
                    context.getBoard().getPlayer().getPlayerAction().resetActionState();
                    Intent intent = new Intent(context, DeckActivity.class);
                    context.startActivity(intent);
                }else{
                    Toast t = Toast.makeText(context,R.string.game_deck_empty,Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }

}
