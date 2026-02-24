package COMP20050.Group4QuaxGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import static COMP20050.Group4QuaxGame.BoardController.tiles;

public class GameManager
{
    protected final Human human = new Human(PlayerColor.BLACK);
    protected final Bot bot = new Bot(PlayerColor.WHITE);
    protected Player currentPlayer = human;
    protected Player otherPlayer = bot;

    protected boolean humanMove(Polygon node)
    {
        if(currentPlayer != human)
        {
            return false;
        }
        if(node.getFill() == Color.BLACK || node.getFill() == Color.WHITE)
        {
            return false;
        }
        switchTurns();
        return true;
    }

    protected Polygon botMove()
    {
        if(currentPlayer != bot || isBoardFull())
        {
            return null;
        }

        Polygon move;
        do
        {
            String moveID = bot.chooseMove();
            move = tiles.get(moveID);

        }while (move == null || move.getFill() == Color.BLACK || move.getFill() == Color.WHITE);

        switchTurns();
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

    // switches turns
    private void switchTurns()
    {
        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;
    }

}