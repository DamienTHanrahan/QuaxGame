package COMP20050.Group4QuaxGame;

public abstract class Player
{
    // each player has a color
    protected PlayerColor playerColor;

    public Player(PlayerColor playerColor)
    {
        this.playerColor = playerColor;
    }

    public PlayerColor getColor() {
        return playerColor;
    }

    public abstract String chooseMove();
}
