package COMP20050.Group4QuaxGame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest
{
    Node octagon;
    Node rhombus;

    // create an octagon node and rhombus to test
    @BeforeEach
    void setUp()
    {
        octagon = new Node("OctA01");
        rhombus = new Node("RhoA01");
    }

    // ------------- getID() tests ---------------
    // test if octagon id starts with Oct
    @Test
    void getIdOctagon()
    {
        assertTrue(octagon.getId().startsWith("Oct"));
    }

    // test if Rhombus id starts with Rho
    @Test
    void getIdRhombus()
    {
        assertTrue(rhombus.getId().startsWith("Rho"));
    }



    // --------------- getOwner tests ---------------
    // test that new nodes always have no owner
    @Test
    void getOwnerNewTile()
    {
        Node newTile = new Node("OctB01");

        assertEquals(PlayerColor.EMPTY, newTile.getOwner());
    }

    // --------------- setOwner tests -----------------
    // test we can set owner to black
    @Test
    void setOwnerBlack()
    {
        octagon.setOwner(PlayerColor.BLACK);
        assertEquals(PlayerColor.BLACK, octagon.getOwner());
    }

    // test we can set owner to white
    @Test
    void setOwnerWhite()
    {
        rhombus.setOwner(PlayerColor.WHITE);
        assertEquals(PlayerColor.WHITE, rhombus.getOwner());
    }


    // -------------------- getNeighbours tests --------------------
    // check neighbour list is empty on (neighbours created in board.java)
    @Test
    void getNeighboursEmpty()
    {
        assertTrue(octagon.getNeighbours().isEmpty());
    }

    // ------------------- addNeighbours tests ---------------------
    // add neighbour correctly adds to total of list
    @Test
    void addNeighbourMakesListNotEmpty()
    {
        octagon.addNeighbour(rhombus);

        assertEquals(1, octagon.getNeighbours().size());
    }

    // test we can add an octagon as a neighbour
    @Test
    void addNeighbourOctagon()
    {
        rhombus.addNeighbour(octagon);
        assertEquals(octagon, rhombus.getNeighbours().getFirst());
    }

    // test we can add a rhombus as a neighbour
    @Test
    void addNeighbourRhombus()
    {
        octagon.addNeighbour(rhombus);
        assertEquals(rhombus, octagon.getNeighbours().getFirst());
    }
}