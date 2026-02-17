package COMP20050.Group4QuaxGame;

public class GameManager
{
    public GameManager()
    {

    }

    // implement in sprint 2
    private boolean validateMove()
    {
        return true;
    }

    protected boolean makeMove(String nodeID)
    {
        // find the node based on its id

        // check if valid
        if(validateMove())
        {
            // change node ownership
            return true;
        }
        return false;
    }

}