package ked.pts3g10.Gameplay.CardPackage;


import android.widget.ImageView;

public abstract class Card {
    protected String name;
    protected String description;
    protected int attactPoints;
    protected int crystalCost;
    protected int rangePoints;
    protected ImageView background;// L'image qui s'affiche quand on regarde la carte
    protected ImageView thumbnail;// L'image affichée sur le board

    public Card(String name,String description,int crystalCost, int ap, int rp, ImageView bg, ImageView thmbn){
        this.name = name;
        this.description = description;
        this.crystalCost = crystalCost;
        attactPoints = ap;
        rangePoints = rp;
        background = bg;
        thumbnail = thmbn;
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

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", attactPoints=" + attactPoints +
                ", rangePoints=" + rangePoints +
                ", background=" + background.getTag().toString() +
                ", thumbnail=" + thumbnail.getTag().toString()+",";
    }
}
