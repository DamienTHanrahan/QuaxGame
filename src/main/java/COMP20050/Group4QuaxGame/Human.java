package COMP20050.Group4QuaxGame;

public class Human extends Player
{
    public Human(PlayerColour playerColour)
    {
        super(playerColour);
    }

    // human move is done through GUI so no need for this method
    @Override
    public String chooseMove()
    {
        return null;
    }
}
