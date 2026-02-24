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
    protected boolean pieRuleUsed = false;
    int turnCount = 0;

    protected Move humanMove(Polygon node)
    {
        if(currentPlayer != human)
        {
            return null;
        }

        Move move = new Move(currentPlayer, node);
        if(!validateMove(move))
        {
            return null;
        }
        switchTurns();
        return move;
    }

    protected Move botMove()
    {
        if(currentPlayer != bot || isBoardFull())
        {
            return null;
        }

        if(turnCount == 1 && bot.usePieRule())
        {
            activatePieRule();
            return null;
        }

        Polygon tile;
        do
        {
            String moveID = bot.chooseMove();
            tile = tiles.get(moveID);

        }while (!validateMove(new Move(bot, tile)));

        Move move = new Move(currentPlayer, tile);
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
        turnCount++;

        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;
    }

    // swap player colors for pie rule
    protected void activatePieRule()
    {
        if(pieRuleUsed)
        {
            return;
        }
        pieRuleUsed = true;
        PlayerColor temp = currentPlayer.getPlayerColor();
        currentPlayer.setPlayerColor(otherPlayer.getPlayerColor());
        otherPlayer.setPlayerColor(temp);

        switchTurns();
    }

    // check if tile is occupied
    private boolean validateMove(Move move)
    {
        if(move.getTile() == null)
        {
            return false;
        }

        return move.getTile().getFill() != Color.BLACK && move.getTile().getFill() != Color.WHITE;
    }

    protected Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    protected int getTurnCount()
    {
        return turnCount;
    }


}