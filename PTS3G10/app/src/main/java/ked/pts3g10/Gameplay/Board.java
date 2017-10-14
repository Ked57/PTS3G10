package ked.pts3g10.Gameplay;


import android.widget.TextView;

import ked.pts3g10.GameActivity;
import ked.pts3g10.R;

public class Board {

    private GameActivity context;
    private Player player;
    private Player adversary;

    public Board(GameActivity context, Player player, Player adversary){
        this.context = context;
        this.player = player;
        this.adversary = adversary;

        /*Init des text view */
        context.updateText(R.id.ADVCardsLeftText,adversary.getDeck().getCardList().size()); // Par défaut pour le moment
        context.updateText(R.id.ADVCrystalsText,adversary.getCrystals());
        context.updateText(R.id.ADVNick,adversary.getNickName());

        context.updateText(R.id.PlayerCardsLeftText,player.getDeck().getCardList().size());
        context.updateText(R.id.PlayerCrystalsText,player.getCrystals());
        context.updateText(R.id.PlayerNick,player.getNickName());
    }
}
