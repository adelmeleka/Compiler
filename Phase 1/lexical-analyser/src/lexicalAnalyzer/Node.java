package lexicalAnalyzer;


import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import DFAs.DFAstate;

public  class Node {

	//unique for each node
	private boolean isGoalState;
	private boolean isEpsilonState;
	private HashMap<String,Node>  nextStates;
	int lastIndex = 0; // if node is EpsilonNode
	private int epsilonCount=0;
	private String type ;
	


	///////added by Adel
	private static int idCounter = 0;
	//unique Id for each created node
	private int NodeID;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////Constructors/////////////////////////////////////////////////////////
	
	public Node(){
		this.isGoalState = false;
		this.nextStates = new HashMap<>();
	}
	
	public Node(int id,boolean isGoalState , HashMap<String,Node>  nextStates ){
		this.isGoalState = isGoalState;
		this.nextStates = new HashMap<>();
		for (Map.Entry<String, Node> entry : nextStates.entrySet())
		{
			this.nextStates .put(entry.getKey(),entry.getValue());
		}
		isEpsilonState = false;
		
		this.NodeID = idCounter++;
	}

	public Node(boolean isGoalState , HashMap<String,Node>  nextStates ){
		this.isGoalState = isGoalState;
		this.nextStates = new HashMap<>();
		for (Map.Entry<String, Node> entry : nextStates.entrySet())
		{
			this.nextStates .put(entry.getKey(),entry.getValue());
		}
		isEpsilonState = false;
		
		this.NodeID = idCounter++;
	}
	
	public Node(boolean isGoalState,String type){
		this.isGoalState = isGoalState;
		this.nextStates = new HashMap<>();
		this.type = type;
	}

	public Node(boolean isEpsilonState){
		this.nextStates = new HashMap<>();
		this.isEpsilonState = isEpsilonState;
		
		this.NodeID = idCounter++;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////getters//////////////////////////////////////////////////////////////
	public boolean isGoalState() {
		return isGoalState;
	}

	public boolean isEpsilonState() {
		return isEpsilonState;
	}

	public HashMap<String, Node> getNextStates() {
		return nextStates;
	}
	
	public int getNodeID(){
		return this.NodeID;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////setters//////////////////////////////////////////////////////////////
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void setGoalState(boolean goalState) {
		isGoalState = goalState;
	}
	
	public void setNextStates(HashMap<String, Node> nextStates) {
		this.nextStates = nextStates;
	}


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setNextState (String s,Node node){
		if(s.equals("ep")){
			s+=epsilonCount+"";
			epsilonCount++;
		}
		nextStates.put(String.valueOf(s),node);
	}
	//more than one state
	public void setNextMultipleStates (HashMap <String,Node> s,Node node){
		for(String key : s.keySet()){
			if(key.equals("ep")){
				key.equals(key+epsilonCount+"");
				epsilonCount++;
			}
			nextStates.put(key,node);
		}
	}
	 
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Node getNextState(String input){
		return nextStates.get(input);
	}

	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Is Goal = " + String.valueOf(isGoalState)+"\n");
		for (Map.Entry<String,Node> entry : nextStates.entrySet()) {
			stringBuilder.append("Key = " + entry.getKey() +"\n");
		}
		stringBuilder.append("============================================================================================="+"\n");
		return String.valueOf(stringBuilder);
	}
	
	///Added by Adel
	public boolean equals(Node n){
		return this.NodeID == n.getNodeID();
	}


	
}

 
