package COMP20050.Group4QuaxGame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BotTest
{
    Bot bot = new Bot();



    // -------------------- chooseMove function TEST -------------------------
    // test if the bot chooses a move always is either a rhombus or an octagon
    @Test
    void testChooseMove()
    {
        String move = bot.chooseMove();
        assertTrue(move.startsWith("Oct") || move.startsWith("Rho"));
    }

    // test if the bot always picks coordinates within the board A - K
    @Test
    void testChooseMoveHasValidXCoordinates()
    {
        String move = bot.chooseMove();
        assertTrue(move.contains("A") || move.contains("B") || move.contains("C") || move.contains("D") || move.contains("E")
                || move.contains("F") || move.contains("G") || move.contains("H") || move.contains("I") || move.contains("J")
                || move.contains("K"));

    }

    // test if the bot always picks coordinates within the board 1 - 11
    @Test
    void testChooseMoveHasValidYCoordinates()
    {
        String move = bot.chooseMove();
        assertTrue(move.endsWith("01") || move.endsWith("02") || move.endsWith("03") || move.endsWith("04") || move.endsWith("05")
                || move.endsWith("06") || move.endsWith("07") || move.endsWith("08") || move.endsWith("09") || move.endsWith("10")
                || move.endsWith("11"));

    }



    // -------------------- getRandomOctagon function TESTS -------------------------
    // test if the selected move is always an Octagon
    @Test
    void testGetRandomOctagon()
    {
        String octagon = bot.getRandomOctagon();
        assertTrue(octagon.startsWith("Oct"));
    }



    // -------------------- getRandomRhombus function TESTS -------------------------
    // test if the selected move is always a Rhombus
    @Test
    void testGetRandomRhombus()
    {
        String rhombus = bot.getRandomRhombus();
        assertTrue(rhombus.startsWith("Rho"));
    }
}