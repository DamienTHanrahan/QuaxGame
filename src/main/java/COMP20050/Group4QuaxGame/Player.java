package COMP20050.Group4QuaxGame;

public abstract class Player
{
    // each player has a colour
    protected PlayerColour playerColour;

    public Player(PlayerColour playerColour)
    {
        this.playerColour = playerColour;
    }

    // getter and setter for the player colour
    public PlayerColour getPlayerColour()
    {
        return playerColour;
    }

    public void setPlayerColour(PlayerColour playerColour)
    {
        this.playerColour = playerColour;
    }

    // each player has its own way of choosing a move
    public abstract String chooseMove();
}
