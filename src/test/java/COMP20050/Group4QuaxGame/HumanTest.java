package COMP20050.Group4QuaxGame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HumanTest
{
    Human human = new Human(PlayerColour.BLACK);

    // Player chooses move through UI so this method is unused and should always return null
    @Test
    void chooseMoveAlwaysNull()
    {
        assertNull(human.chooseMove());
    }

    // test if human can change colour
    @Test
    void changePlayerColour()
    {
        human.setPlayerColour(PlayerColour.WHITE);
        assertEquals(PlayerColour.WHITE, human.getPlayerColour());
    }
}