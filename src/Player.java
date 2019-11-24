public class Player {
    private PlayerName name;
    private GamePoint gamePoint;
    private int setPoint;
    private boolean isServing;


    public Player(PlayerName name) {
        this.name = name;
        gamePoint = GamePoint.LOVE;
        setPoint = 0;
    }

    public void setIsServing(boolean b) {
        isServing = b;
    }
}
