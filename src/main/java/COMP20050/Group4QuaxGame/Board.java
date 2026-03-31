package COMP20050.Group4QuaxGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board
{
    static final int NUM_OF_ROWS = 11;
    protected final Map<String, Node> nodeMap = new HashMap<>();
    protected Node topRow = new Node("topRow");
    protected Node bottomRow = new Node("bottomRow");
    protected Node leftRow = new Node("leftRow");
    protected Node rightRow = new Node("rightRow");

    public Board()
    {
        topRow.setOwner(PlayerColor.BLACK);
        bottomRow.setOwner(PlayerColor.BLACK);
        leftRow.setOwner(PlayerColor.WHITE);
        rightRow.setOwner(PlayerColor.WHITE);
        constructNodeMap();
    }


    protected Node getNode(String id)
    {
        return nodeMap.get(id);
    }

    // build map of all nodes
    protected void constructNodeMap()
    {
        // construct octagon nodes
        for (char col = 'A'; col <= 'K'; col++)
        {
            for (int row = 1; row <= NUM_OF_ROWS; row++)
            {
                // Get String to match Octagon ID
                String id = formatOctagonID(col, row);

                // make the node with id and add it to map
                Node newNode = new Node(id);
                nodeMap.put(id, newNode);

                // if it's an outer row/col node connect to our ghost nodes for win detection
                if(row == 1)
                {
                    topRow.addNeighbour(newNode);
                    newNode.addNeighbour(topRow);
                }
                if(row == NUM_OF_ROWS)
                {
                    bottomRow.addNeighbour(newNode);
                    newNode.addNeighbour(bottomRow);
                }
                if(col == 'A')
                {
                    leftRow.addNeighbour(newNode);
                    newNode.addNeighbour(leftRow);
                }
                if(col == 'K')
                {
                    rightRow.addNeighbour(newNode);
                    newNode.addNeighbour(rightRow);
                }

            }
        }

        // construct rhombus nodes
        for (char col = 'A'; col <= 'J'; col++)
        {
            for (int row = 1; row <= NUM_OF_ROWS - 1; row++)
            {
                // get String to match Rhombus ID
                String id = formatRhombusID(col, row);

                // make the node with id and add it to map
                Node newNode = new Node(id);
                nodeMap.put(id, newNode);
            }
        }

        // call method to construct the neighbour array list
        constructNeighbours();
    }

    // construct neighbours for each node
    protected void constructNeighbours()
    {
        // construct octagon nodes neighbours
        for (char col = 'A'; col <= 'K'; col++)
        {
            for (int row = 1; row <= NUM_OF_ROWS; row++)
            {
                // Get String to match Octagon ID
                String id = formatOctagonID(col, row);
                // get the node that matches the string
                Node node = nodeMap.get(id);
                // get list of all neighbours for octagon
                ArrayList<String> neighbourIDList = getOctagonNodeNeighbours(col, row);

                // add each node to neighbour array list
                for(String neighbourID : neighbourIDList)
                {
                    linkNeighbour(node, neighbourID);
                }
            }
        }

        // construct rhombus nodes
        for (char col = 'A'; col <= 'J'; col++)
        {
            for (int row = 1; row <= NUM_OF_ROWS - 1; row++)
            {
                // Get String to match Rhombus ID
                String id = formatRhombusID(col, row);
                // get the node that matches string
                Node node = nodeMap.get(id);
                // get list of all neighbours for rhombus
                ArrayList<String> neighbourIDList = getRhombusNodeNeighbours(col, row);

                // add each node to neighbour array list
                for(String neighbourID : neighbourIDList)
                {
                    linkNeighbour(node, neighbourID);
                }
            }
        }
    }

    // add neighbour to Array list based on it's ID
    protected void linkNeighbour(Node node, String neighbourID)
    {
        Node neighbour = nodeMap.get(neighbourID);
        // if the neighbour exists add it to list
        if(neighbour != null)
        {
            node.addNeighbour(neighbour);
        }
    }

    // build string to match Octagon ID
    protected String formatOctagonID(char col, int row)
    {
        String id = "Oct" + col;
        if(row < 10)
        {
            id += "0" + row;
        }
        else
        {
            id += row;
        }
        return id;
    }

    // build string to match Rhombus ID
    protected String formatRhombusID(char col, int row)
    {
        String id = "Rho" + col;
        if(row < 10)
        {
            id += "0" + row;
        }
        else
        {
            id += row;
        }
        return id;
    }

    // get list of all neighbours for an octagon node
    protected ArrayList<String> getOctagonNodeNeighbours(char col, int row)
    {
        ArrayList<String> neighbourIDList = new ArrayList<>();

        // Octagons (ORDER): Above, Below, Left, Right
        neighbourIDList.add(formatOctagonID(col, row - 1));
        neighbourIDList.add(formatOctagonID(col, row + 1));
        neighbourIDList.add(formatOctagonID((char)(col - 1), row));
        neighbourIDList.add(formatOctagonID((char)(col + 1), row));

        // Rhombuses (ORDER): Top right, Bottom right, top left, bottom left
        neighbourIDList.add(formatRhombusID(col, row - 1));
        neighbourIDList.add(formatRhombusID(col, row));
        neighbourIDList.add(formatRhombusID((char)(col - 1), row - 1));
        neighbourIDList.add(formatRhombusID((char)(col - 1), row));

        return neighbourIDList;
    }

    // get list of all neighbours for a rhombus node
    protected ArrayList<String> getRhombusNodeNeighbours(char col, int row)
    {
        ArrayList<String> neighbourIDList = new ArrayList<>();

        // Octagons: (Top left, Bottom left, Top right, Bottom Right
        neighbourIDList.add(formatOctagonID(col, row));
        neighbourIDList.add(formatOctagonID(col, row + 1));
        neighbourIDList.add(formatOctagonID((char)(col + 1), row));
        neighbourIDList.add(formatOctagonID((char)(col + 1), row + 1));

        return neighbourIDList;
    }

}
