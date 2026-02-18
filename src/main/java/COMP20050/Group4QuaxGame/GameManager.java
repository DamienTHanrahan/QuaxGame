package COMP20050.Group4QuaxGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import static COMP20050.Group4QuaxGame.BoardController.tiles;

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

    public Polygon botMove()
    {
        Polygon move;
        do
        {
            String moveID = bot.chooseMove();
            move = tiles.get(moveID);

        }while (move == null || move.getFill() == Color.BLACK || move.getFill() == Color.WHITE);

        playersTurn = true;
        return move;
    }

}