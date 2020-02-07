package lexicalAnalyzer;

import java.util.ArrayList;
import java.util.HashMap;

//adjacency list representation for graph data structure

public class Graph {

	private static final String NEWLINE = System.getProperty("line.separator");

    private ArrayList<Node> graphNodes;
    private  HashMap<String,Node> graphNodesHash;
    private Node startSate;
    private Node EndState;
    private HashMap<String,Node> terminalStates ;


	public Graph(HashMap<String,Node> terminalStates) {
    	graphNodes = new ArrayList<>();
	    this.terminalStates = new HashMap<>();
        this.terminalStates = terminalStates;
        startSate = new Node(graphNodes.size(),false,terminalStates);
        EndState = new Node(graphNodes.size(),false,terminalStates);
        graphNodes.add(startSate);
        graphNodes.add(EndState);
	}


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0 ; i <graphNodes.size();i++){
            stringBuilder.append(i+"  "+graphNodes.get(i).toString());
        }
        return stringBuilder.toString();
    }
}