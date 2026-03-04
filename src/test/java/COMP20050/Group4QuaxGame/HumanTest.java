package COMP20050.Group4QuaxGame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HumanTest
{
    Human human = new Human(PlayerColor.BLACK);

    // Player chooses move through UI so this method is unused and should always return null
    @Test
    void chooseMoveAlwaysNull()
    {
        assertNull(human.chooseMove());
    }

    // test if human can change color
    @Test
    void changePlayerColor()
    {
        human.setPlayerColor(PlayerColor.WHITE);
        assertEquals(PlayerColor.WHITE, human.getPlayerColor());
    }
}