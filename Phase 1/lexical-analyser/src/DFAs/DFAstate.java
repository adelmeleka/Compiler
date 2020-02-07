package DFAs;
import lexicalAnalyzer.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class DFAstate {
	
	private static char ch = 'A';	
	private char stateID;

	private ArrayList<Node> stateNodes;
	private boolean isGoalState;
	
	public DFAstate(ArrayList<Node> nodes){
		this.stateID = ch++;
		this.isGoalState = false;
		this.stateNodes = new ArrayList<Node>();
		for (Node n:nodes){
			if(n.isGoalState()) this.isGoalState = true;
			this.stateNodes.add(n);	
		}
	}
	
	//for definining a dead state ONLY
	public DFAstate(char c){
		this.stateID = c;
		this.isGoalState = false;
		this.stateNodes = new ArrayList<Node>();
	}
	
	public char getStateID() {
		return stateID;
	}
	
	public static char getch(){
		return ch;
	}
	
	public static void setch(char x){
		ch = x;
	}
	

	public void setStateID(char stateID) {
		this.stateID = stateID;
	}

	public ArrayList<Node> getStateNodes() {
		return stateNodes;
	}

	public void setStateNodes(ArrayList<Node> stateNodes) {
		this.stateNodes = stateNodes;
	}

	public boolean isGoalState() {
		return isGoalState;
	}

	public void setGoalState(boolean isGoalState) {
		this.isGoalState = isGoalState;
	}
	public boolean isEqual(DFAstate state){
		
		ArrayList<Node> src = this.getStateNodes();
		Collections.sort(src, new SortNodeID()); 
		
		ArrayList<Node> dst = state.getStateNodes();
		Collections.sort(dst, new SortNodeID());
		
		return src.equals(dst);
	}
	
}
