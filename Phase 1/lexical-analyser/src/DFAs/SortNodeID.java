package DFAs;
import java.util.Comparator;

import lexicalAnalyzer.Node;

class SortNodeID implements Comparator<Node>{
	
    // Used for sorting  Nodes 
	//in ascending order of NodeID
    public int compare(Node a, Node b){ 
        return a.getNodeID() - b.getNodeID(); 
    } 
    
}
