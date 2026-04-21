package COMP20050.Group4QuaxGame;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest
{
    Board board = new Board();
    // ----------------- board constructor tests ----------------------
    // test if creating new board sets ghost node on top row to black
    @Test
    void boardTopRowBlack()
    {
        Board boardTop = new Board();
        assertEquals(PlayerColour.BLACK, boardTop.topRow.getOwner());
    }
    // test if creating new board sets ghost node on bottom row to black
    @Test
    void boardBottomRowBlack()
    {
        Board boardBottom = new Board();
        assertEquals(PlayerColour.BLACK, boardBottom.bottomRow.getOwner());
    }
    // test if creating new board sets ghost node on left row to white
    @Test
    void boardLeftRowWhite()
    {
        Board boardLeft = new Board();
        assertEquals(PlayerColour.WHITE, boardLeft.leftRow.getOwner());
    }
    // test if creating new board sets ghost node on right row to white
    @Test
    void boardRightRowWhite()
    {
        Board boardRight = new Board();
        assertEquals(PlayerColour.WHITE, boardRight.rightRow.getOwner());
    }


    // ----------------------- getNode tests ------------------------
    // test getNode works with octagon id
    @Test
    void getNodeOctagon()
    {
        assertNotNull(board.getNode("OctA01"));
    }
    // test getNode works with rhombus id
    @Test
    void getNodeRhombus()
    {
        assertNotNull(board.getNode("RhoA01"));
    }
    // test getNode returns null if the id does not exist
    @Test
    void getNodeThatDoesNotExist()
    {
        assertNull(board.getNode("OCTAGON"));
    }


    // -------------------- constructNodeMap --------------------------
    // test if it creates all octagons
    @Test
    void constructNodeMapCreatesAllOctagons()
    {
        int countOctagons = 0;

        board.constructNodeMap();
        for(Node node : board.nodeMap.values())
        {
            if(node.getId().contains("Oct"))
            {
                countOctagons++;
            }
        }
        assertEquals(121, countOctagons);
    }

    // test if it creates all rhombuses
    @Test
    void constructNodeMapCreatesAllRhombus()
    {
        int countRhombus = 0;

        board.constructNodeMap();
        for(Node node : board.nodeMap.values())
        {
            if(node.getId().contains("Rho"))
            {
                countRhombus++;
            }
        }
        assertEquals(100, countRhombus);
    }

    // test if it constructs all 221 nodes
    @Test
    void constructNodeMapCreatesAllNodes()
    {
        board.constructNodeMap();
        assertEquals(221, board.nodeMap.size());
    }

    // test if it connects all nodes on top row to the ghost "topRow" node
    @Test
    void constructTopRowLink()
    {
        board.constructNodeMap();

        // add each of the nodes from the top to list
        ArrayList<Node> topRowList = new ArrayList<>();
        for(char c = 'A'; c <= 'K'; c++)
        {
            topRowList.add(board.nodeMap.get("Oct" + c + "01"));
        }

        // check that each node in that list has the neighbour of "topRow" node
        for(Node node : topRowList)
        {
            assertTrue(node.getNeighbours().contains(board.topRow));
        }
    }

    // test if it connects all nodes on bottom row to the ghost "bottomRow" node
    @Test
    void constructBottomRowLink()
    {
        board.constructNodeMap();

        // add each of the nodes from the bottom to list
        ArrayList<Node> bottomRowList = new ArrayList<>();
        for(char c = 'A'; c <= 'K'; c++)
        {
            bottomRowList.add(board.nodeMap.get("Oct" + c + "11"));
        }

        // check that each node in that list has the neighbour of "topRow" node
        for(Node node : bottomRowList)
        {
            assertTrue(node.getNeighbours().contains(board.bottomRow));
        }
    }

    // test if it connects all nodes on left row to the ghost "leftRow" node
    @Test
    void constructLeftRowLink()
    {
        board.constructNodeMap();

        // add each of the nodes from the left to list
        ArrayList<Node> leftRowList = new ArrayList<>();
        for(int i = 1; i <= 11; i++)
        {
            String id;
            if(i < 10)
            {
                id = "OctA0" + i;
            }
            else
            {
                id = "OctA" + i;
            }
            leftRowList.add(board.nodeMap.get(id));
        }

        // check that each node in that list has the neighbour of "topRow" node
        for(Node node : leftRowList)
        {
            assertTrue(node.getNeighbours().contains(board.leftRow));
        }
    }

    // test if it connects all nodes on right row to the ghost "rightRow" node
    @Test
    void constructRightRowLink()
    {
        board.constructNodeMap();

        // add each of the nodes from the top to list
        ArrayList<Node> rightRowList = new ArrayList<>();
        for(int i = 1; i <= 11; i++)
        {
            String id;
            if(i < 10)
            {
                id = "OctK0" + i;
            }
            else
            {
                id = "OctK" + i;
            }
            rightRowList.add(board.nodeMap.get(id));
        }

        // check that each node in that list has the neighbour of "topRow" node
        for(Node node : rightRowList)
        {
            assertTrue(node.getNeighbours().contains(board.rightRow));
        }
    }



    // ----------------- test constructNeighbours ----------------
    // test that it correctly adds all neighbours for an octagon in the middle of the board
    @Test
    void constructNeighbourCenterOctagon()
    {
        board.constructNeighbours();
        Node node = board.getNode("OctF06");

        // manually make a list of all neighbours of OctF06 to test
        ArrayList<Node> OctF06Neighbours = new ArrayList<>();
        OctF06Neighbours.add(board.nodeMap.get("OctF05"));
        OctF06Neighbours.add(board.nodeMap.get("OctF07"));
        OctF06Neighbours.add(board.nodeMap.get("OctG06"));
        OctF06Neighbours.add(board.nodeMap.get("OctE06"));
        OctF06Neighbours.add(board.nodeMap.get("RhoE05"));
        OctF06Neighbours.add(board.nodeMap.get("RhoE06"));
        OctF06Neighbours.add(board.nodeMap.get("RhoF05"));
        OctF06Neighbours.add(board.nodeMap.get("RhoF06"));

        // check that each of these are contained in it
        for(Node neighbour : OctF06Neighbours)
        {
            assertTrue(node.getNeighbours().contains(neighbour));
        }
        // check the size is 8 as we found
        assertEquals(8, node.getNeighbours().size());
    }

    // test it constructs all neighbours for a rhombus
    @Test
    void constructNeighbourRhombus()
    {
        board.constructNeighbours();
        Node node = board.getNode("RhoF06");

        // manually make a list of all neighbours of RhoF06 to test
        ArrayList<Node> RhoF06Neighbours = new ArrayList<>();
        RhoF06Neighbours.add(board.nodeMap.get("OctF06"));
        RhoF06Neighbours.add(board.nodeMap.get("OctG06"));
        RhoF06Neighbours.add(board.nodeMap.get("OctF07"));
        RhoF06Neighbours.add(board.nodeMap.get("OctG07"));

        // check that each of these are contained in it
        for(Node neighbour : RhoF06Neighbours)
        {
            assertTrue(node.getNeighbours().contains(neighbour));
        }
        // check the size is 4
        assertEquals(4, node.getNeighbours().size());
    }

    // test it correctly constructs neighbour list for node at the edge of board
    @Test
    void constructNeighbourEdgeNode()
    {
        board.constructNeighbours();
        Node edgeNode = board.nodeMap.get("OctA06");

        // manually make a list of all neighbours of OctA06 to test
        ArrayList<Node> OctA06Neighbours = new ArrayList<>();
        OctA06Neighbours.add(board.nodeMap.get("OctB06"));
        OctA06Neighbours.add(board.nodeMap.get("OctA05"));
        OctA06Neighbours.add(board.nodeMap.get("OctA07"));
        OctA06Neighbours.add(board.nodeMap.get("RhoA05"));
        OctA06Neighbours.add(board.nodeMap.get("RhoA06"));
        // connected to ghost node
        OctA06Neighbours.add(board.leftRow);

        // check that each of these are contained in it
        for(Node neighbour : OctA06Neighbours)
        {
            assertTrue(edgeNode.getNeighbours().contains(neighbour));
        }
        // check the size is 6
        assertEquals(6, edgeNode.getNeighbours().size());
    }

    // test if it correctly added neighbours for a corner node
    @Test
    void constructNeighbourCornerNode()
    {
        board.constructNeighbours();
        Node cornerNode = board.nodeMap.get("OctA01");

        // manually make a list of all neighbours of OctA06 to test
        ArrayList<Node> OctA01Neighbours = new ArrayList<>();
        OctA01Neighbours.add(board.nodeMap.get("OctA02"));
        OctA01Neighbours.add(board.nodeMap.get("OctB01"));
        OctA01Neighbours.add(board.nodeMap.get("RhoA01"));
        // connected to ghost nodes
        OctA01Neighbours.add(board.leftRow);
        OctA01Neighbours.add(board.topRow);

        // check that each of these are contained in it
        for(Node neighbour : OctA01Neighbours)
        {
            assertTrue(cornerNode.getNeighbours().contains(neighbour));
        }
        // check the size is 6
        assertEquals(5, cornerNode.getNeighbours().size());
    }


    // --------------------- linkNeighbour tests ----------------------------
    // test if we can link an octagon as a neighbour
    @Test
    void linkNeighbourOctagon()
    {
        Node octagonNode = board.nodeMap.get("OctC01");
        board.linkNeighbour(octagonNode, "OctF06");
        Node addedNeighbour =  board.nodeMap.get("OctF06");

        // check if neighbour is now added to the list
        assertTrue(octagonNode.getNeighbours().contains(addedNeighbour));
    }
    // test if we can link a rhombus as a neighbour
    @Test
    void linkNeighbourRhombus()
    {
        Node rhombusNode = board.nodeMap.get("RhoD05");
        board.linkNeighbour(rhombusNode, "RhoJ01");
        Node addedNeighbour =  board.nodeMap.get("RhoJ01");

        // check if neighbour is now added to the list
        assertTrue(rhombusNode.getNeighbours().contains(addedNeighbour));
    }

    // test we cannot link to a node that doesn't exist
    @Test
    void linkNullNeighbour()
    {
        // get size of neighbour list
        Node node = board.nodeMap.get("OctA01");
        int oldSize = node.getNeighbours().size();

        // try to add a node that doesn't exist
        board.linkNeighbour(node, "NODE_NOT_REAL");
        int newSize = node.getNeighbours().size();

        // check node wasn't added i.e. size remains unchanged
        assertEquals(oldSize, newSize);
    }

    // test we can't add duplicates to the
    @Test
    void linkNeighbourDuplicateNotAdded()
    {
        Node node = board.nodeMap.get("OctI01");
        // add the node and store the size
        board.linkNeighbour(node, "RhoA01");
        int oldSize = node.getNeighbours().size();

        // try to add same neighbour again and store size again
        board.linkNeighbour(node, "RhoA01");
        int updatedSize = node.getNeighbours().size();

        // check size remains unchanged i.e. it was not added
        assertEquals(oldSize, updatedSize);

    }


    // -------------------- formatOctagonID tests ------------------------
    // test it puts a 0 before a number less than 10
    @Test
    void formatOctagonIDLessThan10()
    {
        assertEquals("OctA01",board.formatOctagonID('A',1));
    }
    // test it works for 10 or above
    @Test
    void formatOctagonIDEqual10()
    {
        assertEquals("OctA10",board.formatOctagonID('A',10));
    }
    // test it works for greater than 10
    @Test
    void formatOctagonIDGreaterThan10()
    {
        assertEquals("OctA11",board.formatOctagonID('A',11));
    }


    // -------------------- formatRhombusID tests ------------------------
    // test it puts a 0 before a number less than 10
    @Test
    void formatRhombusIDLessThan10()
    {
        assertEquals("RhoA01",board.formatRhombusID('A', 1));
    }
    // test it works for 10 or above
    @Test
    void formatRhombusIDEqual10()
    {
        assertEquals("RhoA10",board.formatRhombusID('A', 10));
    }
    // test it works for greater than 10
    @Test
    void formatRhombusIDGreaterThan10()
    {
        assertEquals("RhoA11",board.formatRhombusID('A', 11));
    }


    // ---------------------- getOctagonNodeNeighbours tests -----------------------
    // test it returns all theoretically neighbours as strings in array list even if some don't exist
    @Test
    void getOctagonNodeNeighboursCornerNode()
    {
        ArrayList<String> allNeighbours = board.getOctagonNodeNeighbours('A',1);

        // even tho it's a corner it should still have full list of 8 possible neighbours
        assertEquals(8, allNeighbours.size());
    }

    // test it returns all valid strings for a node in center
    @Test
    void getOctagonNodeNeighboursNormalNode()
    {
        ArrayList<String> allNeighbours = board.getOctagonNodeNeighbours('F',6);

        // manually added expected neighbours
        ArrayList<String> expectedNeighbours = new ArrayList<>();
        expectedNeighbours.add("OctF05");
        expectedNeighbours.add("OctF07");
        expectedNeighbours.add("OctG06");
        expectedNeighbours.add("OctE06");
        expectedNeighbours.add("RhoE05");
        expectedNeighbours.add("RhoE06");
        expectedNeighbours.add("RhoF05");
        expectedNeighbours.add("RhoF06");

        // check it contains what we expected
        for(String expectedNeighbour : expectedNeighbours)
        {
            assertTrue(allNeighbours.contains(expectedNeighbour));
        }
    }


    // ------------------------------- getRhombusNodeNeighbours tests --------------------------------
    // test rhombus always have 4 neighbours
    @Test
    void getRhombusNodeNeighboursAlwaysSize4()
    {
        ArrayList<String> allNeighbours = board.getRhombusNodeNeighbours('A',1);

        // it should always have 4 no matter what
        assertEquals(4, allNeighbours.size());
    }

    // test it returns all valid strings for a node in center
    @Test
    void getRhombusNodeNeighboursNormalNode()
    {
        ArrayList<String> allNeighbours = board.getRhombusNodeNeighbours('F',6);

        // manually added expected neighbours
        ArrayList<String> expectedNeighbours = new ArrayList<>();
        expectedNeighbours.add("OctF06");
        expectedNeighbours.add("OctF07");
        expectedNeighbours.add("OctG06");
        expectedNeighbours.add("OctG07");

        // check it contains what we expected
        for(String expectedNeighbour : expectedNeighbours)
        {
            assertTrue(allNeighbours.contains(expectedNeighbour));
        }
    }


}