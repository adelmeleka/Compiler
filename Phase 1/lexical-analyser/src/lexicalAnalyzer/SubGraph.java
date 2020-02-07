package lexicalAnalyzer;
import java.util.ArrayList;
public class SubGraph {


    private Node startNode;
    private Node endeNode;
    
    public Node getStartNode() {
        return startNode;
    }

    public Node getEndeNode() {
        return endeNode;
    }

    public SubGraph(Node startNode ){
        this.startNode = startNode;
        this.endeNode = new Node();
    }

    public SubGraph(){
        this.startNode = new Node();
        this.endeNode = new Node();
    }


    public static SubGraph createSubGraph (String transition){
        SubGraph graph = new SubGraph();
        graph.startNode.setNextState(transition,graph.endeNode);
        return graph;
    }


    public static SubGraph concatinate (SubGraph subGraph1,SubGraph subGraph2){
        //SubGraph graph = new SubGraph();
        //graph.startNode = subGraph1.startNode;
       // graph.endeNode = subGraph1.endeNode;
        //graph.endeNode.setNextMultipleStates(subGraph2.getStartNode().getNextStates(),subGraph2.getStartNode());
        
        subGraph1.endeNode.setNextStates(subGraph2.startNode.getNextStates());
        return subGraph1;
    }

    public static SubGraph concatinate (SubGraph subGraph1,String transition) {
        Node node = new Node();
        subGraph1.endeNode.setNextState(transition, node);
        subGraph1.endeNode = node;
        return subGraph1;
    }

    public static SubGraph concatinateAndSetGoal (SubGraph subGraph1,String transition,String type) {
        Node node = new Node(true,type);
        subGraph1.endeNode.setNextState(transition, node);
        subGraph1.endeNode = node;
        return subGraph1;
    }

    public static SubGraph or (ArrayList<SubGraph> subGraphs){
        SubGraph graph = new SubGraph();
        //start of or NFA
        String s = "ep";
        
        for (int i = 0 ; i<subGraphs.size() ; i++ ){
        	graph.startNode.setNextState(s, subGraphs.get(i).getStartNode());
        	subGraphs.get(i).getEndeNode().setNextState(s, graph.endeNode);
        }
        return graph;
    }

    public static SubGraph finalOR (ArrayList<SubGraph> subGraphs){
        SubGraph graph = new SubGraph();
        //start of or NFA
        String s = "ep";

        for (int i = 0 ; i<subGraphs.size() ; i++ ){
            graph.startNode.setNextState(s, subGraphs.get(i).getStartNode());
        }
        return graph;
    }

    public static SubGraph cleanClosure(SubGraph subGraph){
    	subGraph.getEndeNode().setNextState("ep", subGraph.getStartNode());
    	subGraph.getStartNode().setNextState("ep", subGraph.getEndeNode());
    	return subGraph;
    }
    
    public static SubGraph postiveClosure(SubGraph subGraph){
    	subGraph.endeNode.setNextState("ep", subGraph.getStartNode());
    	return subGraph;
    }
    
}
