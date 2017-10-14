package ked.pts3g10.Gameplay.CardPackage;


import android.widget.ImageView;

public abstract class Card {
    protected String name;
    protected int attactPoints;
    protected int rangePoints;
    protected ImageView background;// L'image qui s'affiche quand on regarde la carte
    protected ImageView thumbnail;// L'image affichée sur le board

    public Card(String name, int ap, int rp, ImageView bg, ImageView thmbn){
        this.name = name;
        attactPoints = ap;
        rangePoints = rp;
        background = bg;
        thumbnail = thmbn;
    }

    public String getName() {
        return name;
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
}
