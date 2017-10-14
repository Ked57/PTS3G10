package ked.pts3g10.Gameplay;


import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ked.pts3g10.GameActivity;
import ked.pts3g10.Interface.Case;
import ked.pts3g10.R;

public class Board {

    private GameActivity context;
    private Player player;
    private Player adversary;

    private LinearLayout V1,V2,V3,V4,V5;//V pour verical, le nombre pour le numéro de colonne
    private ArrayList<Case> cases;

    public Board(GameActivity context, Player player, Player adversary){
        this.context = context;
        this.player = player;
        this.adversary = adversary;
        this.cases = new ArrayList<Case>();

        V1 = (LinearLayout) context.findViewById(R.id.V1);
        V2 = (LinearLayout) context.findViewById(R.id.V2);
        V3 = (LinearLayout) context.findViewById(R.id.V3);
        V4 = (LinearLayout) context.findViewById(R.id.V4);
        V5 = (LinearLayout) context.findViewById(R.id.V5);
        initLayout(V1);
        initLayout(V2);
        initLayout(V3);
        initLayout(V4);
        initLayout(V5);

        //On met les chateaux
        getCaseWithLinearLayoutNumber(2,0).setCardThumbnail(R.drawable.castle);
        getCaseWithLinearLayoutNumber(2,4).setCardThumbnail(R.drawable.castle);

        /*Init des text view */
        context.updateText(R.id.ADVCardsLeftText,adversary.getDeck().getCardList().size()); // Par défaut pour le moment
        context.updateText(R.id.ADVCrystalsText,adversary.getCrystals());
        context.updateText(R.id.ADVNick,adversary.getNickName());

        context.updateText(R.id.PlayerCardsLeftText,player.getDeck().getCardList().size());
        context.updateText(R.id.PlayerCrystalsText,player.getCrystals());
        context.updateText(R.id.PlayerNick,player.getNickName());
    }

    public void initLayout(LinearLayout V){
        for(int i = 0; i < 5; ++i){
            Case c = new Case(context);
            V.addView(c);
            cases.add(c);
        }
    }

    public Case getCaseWithLinearLayoutNumber(int linearLayoutNumber, int caseNumber){
        return cases.get(linearLayoutNumber*5+caseNumber);
    }
}
