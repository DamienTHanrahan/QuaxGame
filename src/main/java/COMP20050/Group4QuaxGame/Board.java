package COMP20050.Group4QuaxGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board
{
    static final int NUM_OF_ROWS = 11;
    protected final Map<String, Node> nodeMap = new HashMap<>();
    protected ArrayList<Node> topRow = new ArrayList<>();
    protected ArrayList<Node> bottomRow = new ArrayList<>();
    protected ArrayList<Node> leftRow = new ArrayList<>();
    protected ArrayList<Node> rightRow = new ArrayList<>();

    public Board()
    {
        constructNodeMap();
    }


    protected Node getNode(String id)
    {
        return nodeMap.get(id);
    }

    // build map of all nodes
    private void constructNodeMap()
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

                // add to array list of edge rows if needed
                if(row == 1)
                {
                    topRow.add(newNode);
                }
                if(row == NUM_OF_ROWS)
                {
                    bottomRow.add(newNode);
                }
                if(col == 'A')
                {
                    leftRow.add(newNode);
                }
                if(col == 'K')
                {
                    rightRow.add(newNode);
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
    private void constructNeighbours()
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
    private void linkNeighbour(Node node, String neighbourID)
    {
        Node neighbour = nodeMap.get(neighbourID);
        // if the neighbour exists add it to list
        if(neighbour != null)
        {
            node.addNeighbour(neighbour);
        }
    }

    // build string to match Octagon ID
    private String formatOctagonID(char col, int row)
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
    private String formatRhombusID(char col, int row)
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
    private ArrayList<String> getOctagonNodeNeighbours(char col, int row)
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
    private ArrayList<String> getRhombusNodeNeighbours(char col, int row)
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
