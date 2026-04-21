package COMP20050.Group4QuaxGame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest
{
    Player player = new Player(PlayerColour.EMPTY)
    {
        // for testing purpose this always will return OCTA01
        
        public String chooseMove(Board board, PlayerColour colour)
        {
            return "OCTA01";
        }
    };

    // --------------------------- getPlayerColour tests ------------------------
    @Test
    void getPlayerColourBlack()
    {
        player.playerColour = PlayerColour.BLACK;

        assertEquals(PlayerColour.BLACK, player.getPlayerColour());
    }

    @Test
    void getPlayerColourWhite()
    {
        player.playerColour = PlayerColour.WHITE;

        assertEquals(PlayerColour.WHITE, player.getPlayerColour());
    }

    @Test
    void getPlayerColourEmpty()
    {
        player.playerColour = PlayerColour.EMPTY;

        assertEquals(PlayerColour.EMPTY, player.getPlayerColour());
    }


    // --------------------------- setPlayerColour tests ------------------------
    @Test
    void setPlayerColourBlack()
    {
        player.setPlayerColour(PlayerColour.BLACK);

        assertEquals(PlayerColour.BLACK, player.getPlayerColour());

    }

    @Test
    void setPlayerColourWhite()
    {
        player.setPlayerColour(PlayerColour.WHITE);

        assertEquals(PlayerColour.WHITE, player.getPlayerColour());

    }

    @Test
    void setPlayerColourEmpty()
    {
        player.setPlayerColour(PlayerColour.EMPTY);

        assertEquals(PlayerColour.EMPTY, player.getPlayerColour());

    }
}