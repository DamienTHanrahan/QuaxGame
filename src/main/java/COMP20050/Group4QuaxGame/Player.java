package COMP20050.Group4QuaxGame;

public abstract class Player
{
    // each player has a color
    protected PlayerColor playerColor;

    public Player(PlayerColor playerColor)
    {
        this.playerColor = playerColor;
    }

    public PlayerColor getPlayerColor()
    {
        return playerColor;
    }

    public void setPlayerColor(PlayerColor playerColor)
    {
        this.playerColor = playerColor;
    }

    // each player has its own way of choosing a move
    public abstract String chooseMove();
}
