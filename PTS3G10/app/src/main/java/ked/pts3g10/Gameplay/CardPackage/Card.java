package ked.pts3g10.Gameplay.CardPackage;

public abstract class Card {
    protected int id;
    protected String name;
    protected String description;
    protected int attactPoints;
    protected int crystalCost;
    protected int rangePoints;
    protected int background;// L'image qui s'affiche quand on regarde la carte
    protected int thumbnail;// L'image affichée sur le board
    protected boolean hasMovedThisRound;
    protected boolean adversary;

    public Card(int id, String name,String description,int crystalCost, int ap, int rp, int bg, int thmbn, boolean adversary){
        this.id = id;
        this.name = name;
        this.description = description;
        this.crystalCost = crystalCost;
        attactPoints = ap;
        rangePoints = rp;
        background = bg;
        hasMovedThisRound = false;
        this.adversary = adversary;
        thumbnail = thmbn;
    }

    public int getId() {
        return id;
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

    public int getBackground() {
        return background;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int resId) { thumbnail = resId;}

    public void setBackground(int resId) { background = resId;}

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", attactPoints=" + attactPoints +
                ", rangePoints=" + rangePoints +
                ", background=" + background +
                ", thumbnail=" + thumbnail+",";
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

    public Card clone(boolean adversary){
        return null;
    }
}
