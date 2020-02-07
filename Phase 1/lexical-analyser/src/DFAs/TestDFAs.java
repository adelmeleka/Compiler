package DFAs;
import lexicalAnalyzer.Node;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.ArrayList;


public class TestDFAs {
	
public static ArrayList<Node> NFAGraph(){
		
		ArrayList<Node> t = new ArrayList<Node>();
		
		////NFA Graph example
		Node node0 = new Node(true);
		
		Node node1 = new Node(true);
		
		Node node2 = new Node(false);
		
		Node node3 = new Node(true);
		
		Node node4 = new Node(false);
		
		Node node5 = new Node(true);
		
		Node node6 = new Node(true);
		
		Node node7 = new Node(false);
		
		Node node8 = new Node(false);
		
		Node node9 = new Node(false);
		
		Node node10 = new Node(false);
		node10.setGoalState(true);		
		
		
		node0.setNextState("ep0", node1);
		node0.setNextState("ep1", node7);
		
		node1.setNextState("ep0", node2);
		node1.setNextState("ep1", node4);
		
		node2.setNextState("a", node3);
		
		node3.setNextState("ep0", node6);
		
		node4.setNextState("b", node5);
		
		node5.setNextState("ep0", node6);
		
		node6.setNextState("ep0", node1);
		node6.setNextState("ep1", node7);
		
		node7.setNextState("a", node8);
		
		node8.setNextState("b", node9);
		
		node9.setNextState("b", node10);
		
		t.add(node0);
		t.add(node1);
		t.add(node2);
		t.add(node3);
		t.add(node4);
		t.add(node5);
		t.add(node6);
		t.add(node7);
		t.add(node8);
		t.add(node9);
		t.add(node10);
		
		return t;
	}

public static ArrayList<Node> NFAGraph1(){
	
	ArrayList<Node> t = new ArrayList<Node>();
	
	////NFA Graph example
	Node node0 = new Node(true);
	
	Node node1 = new Node(false);
	
	Node node2 = new Node(false);
	node2.setGoalState(true);
	node2.setType("a");
	
	Node node3 = new Node(false);
	
	Node node4 = new Node(false);
	
	Node node5 = new Node(false);
	
	Node node6 = new Node(false);
	node6.setGoalState(true);
	node6.setType("abb");
	
	Node node7 = new Node(false);
	
	Node node8 = new Node(false);
	node8.setGoalState(true);
	node8.setType("a*b+");
		
	
	node0.setNextState("ep0", node1);
	node0.setNextState("ep1", node3);
	node0.setNextState("ep3", node7);
	
	node1.setNextState("a", node2);
	
	node3.setNextState("a", node4);
	
	node4.setNextState("b", node5);
	
	node5.setNextState("b", node6);
	
	node7.setNextState("b", node8);
	node7.setNextState("a", node7);
	
	node8.setNextState("b", node8);
	
	
	t.add(node0);
	t.add(node1);
	t.add(node2);
	t.add(node3);
	t.add(node4);
	t.add(node5);
	t.add(node6);
	t.add(node7);
	t.add(node8);
	
	return t;
}
	
	public static void main(String[] args) {
		
		ArrayList<Node> arrlst = NFAGraph1();
		
		ArrayList<String> inputs = new ArrayList<String>();
		inputs.add("a");
		inputs.add("b");
		
		Map<DFAstate, ArrayList<DFAstate>> map = DFA.DFATransitionTable(arrlst.get(0),inputs);
		
		for (DFAstate name: map.keySet()){
			System.out.println("----------------------------");
			System.out.println("KEY: "+name.getStateID());
			System.out.println("GOAL STATE: "+name.isGoalState());
			ArrayList<DFAstate> val = map.get(name);
			System.out.println("VALUE");
			for(DFAstate e: val)
				System.out.println(e.getStateID());
			System.out.println("----------------------------");	
		}
          
//		DFAstate [] s;
		
		//ArrayList<Node> t = new ArrayList<Node>();
		//t.add(arrlst.get(0));
		
		
		//DFAstate state = DFA.Eclosure(t);
		
		//for(Node n :state.getStateNodes())
		//	System.out.println(n.getNodeID());
		
		
//	    HashMap<Integer,Integer> capitalCities = new HashMap<Integer,Integer>();
//
//	    // Add keys and values (Country, City)
//	    capitalCities.put(1,3);
//	    capitalCities.put(2,10);
//	    capitalCities.put(50,8);
//	    capitalCities.put(6,5);
//	    for (int name: capitalCities.keySet()){
//			System.out.println("----------------------------");
//			System.out.println("KEY: "+name);
//			System.out.println("VALUE");
//				System.out.println(capitalCities.get(name));
//			System.out.println("----------------------------");	
//		}
            	
	  
		

	}

}
