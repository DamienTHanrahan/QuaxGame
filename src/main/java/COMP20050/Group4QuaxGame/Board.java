package COMP20050.Group4QuaxGame;

import java.util.*;

public class Board
{
    static final int NUM_OF_ROWS = 11;
    protected final Map<String, Node> nodeMap = new HashMap<>();
    protected Node topRow = new Node("topRow");
    protected Node bottomRow = new Node("bottomRow");
    protected Node leftRow = new Node("leftRow");
    protected Node rightRow = new Node("rightRow");
    protected Stack<Node> blackPath = new Stack<>();
    protected Stack<Node> whitePath = new Stack<>();

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

    
    protected void constructNodeMap()
    {
        
        for (char col = 'A'; col <= 'K'; col++)
        {
            for (int row = 1; row <= NUM_OF_ROWS; row++)
            {
                
                String id = formatOctagonID(col, row);
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
        for (char col = 'A'; col <= 'J'; col++)
        {
            for (int row = 1; row <= NUM_OF_ROWS - 1; row++)
            {
                String id = formatRhombusID(col, row);
                Node newNode = new Node(id);
                nodeMap.put(id, newNode);
            }
        }
        
        constructNeighbours();
    }
    
    protected void constructNeighbours()
    {
        // construct octagon nodes neighbours
        for (char col = 'A'; col <= 'K'; col++)
        {
            for (int row = 1; row <= NUM_OF_ROWS; row++)
            {
                String id = formatOctagonID(col, row);
                Node node = nodeMap.get(id);
                ArrayList<String> neighbourIDList = getOctagonNodeNeighbours(col, row);
                
                for(String neighbourID : neighbourIDList)
                {
                    linkNeighbour(node, neighbourID);
                }
            }
        }
        
        for (char col = 'A'; col <= 'J'; col++)
        {
            for (int row = 1; row <= NUM_OF_ROWS - 1; row++)
            {
                String id = formatRhombusID(col, row);
                Node node = nodeMap.get(id);
                ArrayList<String> neighbourIDList = getRhombusNodeNeighbours(col, row);
                
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

   
    protected ArrayList<String> getRhombusNodeNeighbours(char col, int row) {
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

    protected ArrayList<Node> getAllTileNodes(){

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
        Map<Node, Node> parentMap = new HashMap<>();
        HashSet<Node> evaluated = new HashSet<>();
        PlayerColour opponentColour = getOpponentColour(colour);
        Node startNode = getStartNode(colour);
        Node endNode = getEndNode(colour);
        Node currentMin = null;
        
        setNodePrice(price, colour);
        initalisePathPrice(pathPrice, startNode);
        toEvaluate.put(startNode, pathPrice.get(startNode));

        // evaluating a path will add its neighbours to be evaluated. once evaluated remove and add to evaluated
        while (!toEvaluate.isEmpty()) {

            // each time evaluate the neighbours of path in toEvaluate with lowest current price cost
            currentMin = getCurrentMin(toEvaluate);
            for (Node neighbour : currentMin.getNeighbours()){

                if (neighbour.getOwner() == opponentColour){
                    continue;
                }
                if (pathPrice.get(neighbour) > pathPrice.get(currentMin) + price.get(neighbour)){
                    pathPrice.put(neighbour, pathPrice.get(currentMin) + price.get(neighbour));
                    parentMap.put(neighbour, currentMin);
                }
                
                if (!evaluated.contains(neighbour)) {
                    toEvaluate.put(neighbour, pathPrice.get(neighbour));
                }
            }
            
            evaluated.add(currentMin);
            toEvaluate.remove(currentMin);
        }
        setPath(colour, parentMap, endNode);
        return pathPrice.get(endNode);
    }


    private Node getCurrentMin (Map <Node, Integer> toEvaluate) {


        return Collections.min(toEvaluate.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public Stack<Node> getPath(PlayerColour colour){
        if (colour == PlayerColour.BLACK){
            return blackPath;
        }
        else{
            return whitePath;
        }
    }

    private PlayerColour getOpponentColour(PlayerColour colour) {

        if (colour == PlayerColour.WHITE){
            
            return PlayerColour.BLACK;

            
        }
        if (colour == PlayerColour.BLACK){
            
            return PlayerColour.WHITE;
          
        }

        return null;
    }

    protected Node getStartNode(PlayerColour colour) {

        if (colour == PlayerColour.WHITE){
            
            Node startNode = leftRow;

            return startNode;
        }
        else if (colour == PlayerColour.BLACK){

            Node startNode = topRow;

            return startNode;
            
           
        }

        return null;
    }

      protected Node getEndNode(PlayerColour colour) {

        if (colour == PlayerColour.WHITE){
            
            Node endNode = rightRow;

            return endNode;
        }
        else if (colour == PlayerColour.BLACK){

            Node endNode = bottomRow;

            return endNode;
            
           
        }

        return null;
    }


    private void setNodePrice(Map<Node, Integer> price, PlayerColour colour){

        for (Node node : getAllNodes()){

            if (node.getOwner() == colour) {
                
                price.put(node, 0);
            }
            else if (node.getOwner() == PlayerColour.EMPTY) {
                price.put(node, 1);
            }


        }
    }    

    private void initalisePathPrice(Map<Node, Integer> pathPrice, Node startNode){

        for (Node node : getAllNodes()){

            if (node == startNode) {
                pathPrice.put(node, 0);
            }
            else {

                pathPrice.put(node, Integer.MAX_VALUE);

            }
        }
    }


    private void setPath(PlayerColour colour, Map<Node,Node> parentMap, Node endNode){

        Stack<Node> path = getPath(colour); 
        path.clear();
        Node currentNode = parentMap.get(endNode);
            while (currentNode != null) {
                path.add(currentNode);
                currentNode = parentMap.get(currentNode);
            }



    }


}
