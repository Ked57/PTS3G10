package ked.pts3g10;

public class ActivityMgr {
    public static DeckActivity deckActivity;
    public static LaunchActivity launchActivity;
    public static GameActivity gameActivity;

    public ActivityMgr(){}

    public void setDeckActivity(DeckActivity deckActivity){
        this.deckActivity = deckActivity;
    }
    public void setLaunchActivity(LaunchActivity launchActivity){
        this.launchActivity = launchActivity;
    }
    public void setGameActivity(GameActivity gameActivity){
        this.gameActivity = gameActivity;
    }
}
