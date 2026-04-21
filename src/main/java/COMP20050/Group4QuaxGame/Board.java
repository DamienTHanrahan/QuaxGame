package COMP20050.Group4QuaxGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;

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
        topRow.setOwner(PlayerColour.BLACK);
        bottomRow.setOwner(PlayerColour.BLACK);
        leftRow.setOwner(PlayerColour.WHITE);
        rightRow.setOwner(PlayerColour.WHITE);
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


    protected ArrayList<Node> getAllNodes(){

        ArrayList<Node> allNodes = new ArrayList<>(nodeMap.values());
        allNodes.add(bottomRow);
        allNodes.add(topRow);
        allNodes.add(leftRow);
        allNodes.add(rightRow);

        return allNodes;


    }

    protected ArrayList<Node> getEmptyNodes(){

        ArrayList<Node> emptyNodes = new ArrayList<>();

        for (Node node : nodeMap.values()){

            if (node.getOwner() == PlayerColour.EMPTY){
                emptyNodes.add(node);
            }
        }

        return emptyNodes;
    }


    protected int getCheapestPath (PlayerColour colour){

        Map<Node, Integer> price = new HashMap<>();
        Map<Node, Integer> pathPrice = new HashMap<>();
        Map<Node, Integer> toEvaluate = new HashMap<>();
        HashSet<Node> evaluated = new HashSet<>();
        PlayerColour opponentColour = null;
        Node startNode = null;
        Node endNode = null;
        Node currentMin = null;
        
        
        if (colour == PlayerColour.WHITE){
            startNode = leftRow;
            endNode = rightRow;
            opponentColour = PlayerColour.BLACK;
        }
        else if (colour == PlayerColour.BLACK){
            startNode = topRow;
            endNode = bottomRow;
            opponentColour = PlayerColour.WHITE;
        }


        // set each distance to a max value so that initially all will be correctly updated
        // set cost of bot colour to 0, empty to 1, and opponent colour to a large value
        for (Node node : getAllNodes()){

            if (node.getOwner() == colour) {
                
                price.put(node, 0);
            }
            else if (node.getOwner() == PlayerColour.EMPTY) {
                price.put(node, 1);
            }
            

            // initialise the path prices, costs 0 to reach startNode and max values to be altered to reach all other nodes
            if (node == startNode) {
                pathPrice.put(node, 0);
            }
            else {

                pathPrice.put(node, Integer.MAX_VALUE);

            }



            
        }

        toEvaluate.put(startNode, pathPrice.get(startNode));

        // evaluating a path will add its neighbours to be evaluated. once evaluated remove and add to evaluated
        // so we use while loop while toEvaluate isn't empty 

        while (!toEvaluate.isEmpty()) {

            // each time evaluate the neighbours of path in toEvaluate with lowest current price cost

            currentMin = getCurrentMin(toEvaluate);
            for (Node neighbour : currentMin.getNeighbours()){

                
                if (neighbour.getOwner() == opponentColour){
                    continue;
                }
                
                
                if (pathPrice.get(neighbour) > pathPrice.get(currentMin) + price.get(neighbour)){

                    pathPrice.put(neighbour, pathPrice.get(currentMin) + price.get(neighbour));
                }
            // once all neighbours path prices have been checked or altered, check if each neighbour has been evaluated already
            //  If not in evaluated don't check if already in toEvaluate as we want most updated value in toEvaluate,
            // just update it


                if (!evaluated.contains(neighbour)) {
                    toEvaluate.put(neighbour, pathPrice.get(neighbour));
                }
            }

            // when done checking all neighbours, currentMin need not be evaluated again. remove from toEvaluate and add to
            // evaluated

            evaluated.add(currentMin);
            toEvaluate.remove(currentMin);

            
            



        }

        return pathPrice.get(endNode);

    }

    // return the key in a hashmap that has the smallest value, helper method for getCheapestPath

    private Node getCurrentMin (Map <Node, Integer> toEvaluate) {


        return Collections.min(toEvaluate.entrySet(), Map.Entry.comparingByValue()).getKey();
    }



    

}
