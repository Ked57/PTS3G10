package ked.pts3g10.Gameplay.CardPackage;


import android.util.Log;
import android.widget.ImageView;

import ked.pts3g10.R;

public abstract class Card {
    protected String name;
    protected String description;
    protected int attactPoints;
    protected int crystalCost;
    protected int rangePoints;
    protected ImageView background;// L'image qui s'affiche quand on regarde la carte
    protected ImageView thumbnail;// L'image affichée sur le board
    protected boolean hasMovedThisRound;
    protected boolean adversary;

    public Card(String name,String description,int crystalCost, int ap, int rp, ImageView bg, ImageView thmbn, boolean adversary){
        this.name = name;
        this.description = description;
        this.crystalCost = crystalCost;
        attactPoints = ap;
        rangePoints = rp;
        background = bg;
        hasMovedThisRound = false;
        this.adversary = adversary;
        thumbnail = thmbn;
        if(adversary){
           switch(name){
               case "Archers":
                   thmbn.setBackgroundResource(R.drawable.bowred);
                   thmbn.setTag(R.drawable.bowred);
                   break;
               case "Légion":
                   thmbn.setBackgroundResource(R.drawable.swordred);
                   thmbn.setTag(R.drawable.swordred);
           }
        }

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCrystalCost() {
        return crystalCost;
    }

    public int getAttactPoints() {
        return attactPoints;
    }

    public int getRangePoints() {
        return rangePoints;
    }

    public ImageView getBackground() {
        return background;
    }

    public ImageView getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int resId) { thumbnail.setImageResource(resId);}

    public void setBackground(int resId) { background.setImageResource(resId);}

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", attactPoints=" + attactPoints +
                ", rangePoints=" + rangePoints +
                ", background=" + background.getTag().toString() +
                ", thumbnail=" + thumbnail.getTag().toString()+",";
    }

    public boolean hasMovedThisRound() {
        return hasMovedThisRound;
    }

    public void setHasMovedThisRound(boolean hasMovedThisRound) {
        this.hasMovedThisRound = hasMovedThisRound;
    }

    public boolean isAdversary() {
        return adversary;
    }
}
