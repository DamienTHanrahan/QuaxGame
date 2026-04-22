package COMP20050.Group4QuaxGame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BotTest
{
    Bot bot = new Bot(PlayerColour.WHITE);
    Board board;

    @BeforeEach
    void setupBoard()
    {
        board = new Board();
    }

    // -------------------- chooseMove function TEST -------------------------
    // test if the bot chooses a move always is either a rhombus or an octagon
    @Test
    void testChooseMove()
    {
        String move = bot.chooseMove(board, PlayerColour.WHITE);
        assertTrue(move.startsWith("Oct") || move.startsWith("Rho"));
    }

    // test if the bot always picks coordinates within the board A - K
    @Test
    void testChooseMoveHasValidXCoordinates()
    {
        String move = bot.chooseMove(board, PlayerColour.WHITE);
        assertTrue(move.contains("A") || move.contains("B") || move.contains("C") || move.contains("D") || move.contains("E")
                || move.contains("F") || move.contains("G") || move.contains("H") || move.contains("I") || move.contains("J")
                || move.contains("K"));

    }

    // test if the bot always picks coordinates within the board 1 - 11
    @Test
    void testChooseMoveHasValidYCoordinates()
    {
        String move = bot.chooseMove(board, PlayerColour.WHITE);
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


    // --------------------- usePieRule function TESTS -------------------------------

    // test if bot uses pie rule for any center tile
    @Test
    void usePieRuleCenterTile()
    {
        // simulate first move was OctF06
        board.getNode("OctF06").setOwner(PlayerColour.BLACK);

        assertTrue(bot.usePieRule(board));
    }

    // test if bot uses pie rule on non center tile
    @Test
    void usePieRuleNonCenterTile()
    {
        // simulate first move was OctC06 - outside of center tile range
        board.getNode("OctC06").setOwner(PlayerColour.BLACK);

        assertFalse(bot.usePieRule(board));
    }

    @Test
    void usePieRuleTopLeftCenterTile()
    {
        // simulate first move was OctD04
        board.getNode("OctD04").setOwner(PlayerColour.BLACK);

        assertTrue(bot.usePieRule(board));
    }
    @Test
    void usePieRuleTopRightCenterTile()
    {
        board.getNode("OctH04").setOwner(PlayerColour.BLACK);

        assertTrue(bot.usePieRule(board));
    }

    @Test
    void  usePieRuleBottomLeftCenterTile()
    {
        board.getNode("OctD08").setOwner(PlayerColour.BLACK);
        assertTrue(bot.usePieRule(board));
    }

    @Test
    void usePieRuleBottomRightCenterTile()
    {
        board.getNode("OctH08").setOwner(PlayerColour.BLACK);
        assertTrue(bot.usePieRule(board));
    }

}