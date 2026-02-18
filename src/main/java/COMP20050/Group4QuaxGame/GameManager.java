package COMP20050.Group4QuaxGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import static COMP20050.Group4QuaxGame.BoardController.tiles;

public class GameManager
{
    private final Bot bot = new Bot();
    private boolean playersTurn = true;

    protected boolean playerMove(Polygon node)
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

    protected Polygon botMove()
    {
        if(isBoardFull())
        {
            return null;
        }

        Polygon move;
        do
        {
            String moveID = bot.chooseMove();
            move = tiles.get(moveID);

        }while (move == null || move.getFill() == Color.BLACK || move.getFill() == Color.WHITE);

        playersTurn = true;
        return move;
    }

    // method to check if board is full to prevent bot infinite loop
    protected boolean isBoardFull()
    {
        for(Polygon p : tiles.values())
        {
            if (p.getFill() != Color.WHITE && p.getFill() != Color.BLACK)
            {
                return false;
            }
        }
        return true;
    }


}