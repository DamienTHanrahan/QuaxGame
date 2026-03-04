package COMP20050.Group4QuaxGame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest
{
    Player player = new Player(PlayerColor.EMPTY)
    {
        // for testing purpose this always will return OCTA01
        @Override
        public String chooseMove()
        {
            return "OCTA01";
        }
    };

    // --------------------------- getPlayerColor tests ------------------------
    @Test
    void getPlayerColorBlack()
    {
        player.playerColor = PlayerColor.BLACK;

        assertEquals(PlayerColor.BLACK, player.getPlayerColor());
    }

    @Test
    void getPlayerColorWhite()
    {
        player.playerColor = PlayerColor.WHITE;

        assertEquals(PlayerColor.WHITE, player.getPlayerColor());
    }

    @Test
    void getPlayerColorEmpty()
    {
        player.playerColor = PlayerColor.EMPTY;

        assertEquals(PlayerColor.EMPTY, player.getPlayerColor());
    }


    // --------------------------- setPlayerColor tests ------------------------
    @Test
    void setPlayerColorBlack()
    {
        player.setPlayerColor(PlayerColor.BLACK);

        assertEquals(PlayerColor.BLACK, player.getPlayerColor());

    }

    @Test
    void setPlayerColorWhite()
    {
        player.setPlayerColor(PlayerColor.WHITE);

        assertEquals(PlayerColor.WHITE, player.getPlayerColor());

    }

    @Test
    void setPlayerColorEmpty()
    {
        player.setPlayerColor(PlayerColor.EMPTY);

        assertEquals(PlayerColor.EMPTY, player.getPlayerColor());

    }
}