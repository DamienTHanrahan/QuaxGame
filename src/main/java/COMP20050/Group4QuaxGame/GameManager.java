package COMP20050.Group4QuaxGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class GameManager
{
    private final Bot bot = new Bot();
    private Boolean playersTurn = true;

    protected boolean makeMove(Polygon node)
    {
        if(!playersTurn)
        {
            return false;
        }
        if(node.getFill() == Color.BLACK || node.getFill() == Color.WHITE)
        {
            return false;
        }
        playersTurn = false;
        return true;
    }

    public String botMove()
    {
        String id = bot.chooseMove();
        playersTurn = true;
        return id;
    }

}