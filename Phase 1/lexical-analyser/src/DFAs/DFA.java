package DFAs;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.HashMap;

import lexicalAnalyzer.Node;

public class DFA {
	
	private static boolean exists(Node node, ArrayList<Node> arr){
		
		for(Node n : arr)
			if(node.equals(n))
				return true;
		
		return false;
		
	}
	 HashMap<String, Integer> priority = new HashMap<>(); 
	 
	//Eclosure computation
	//given array of nodes
	//return DFA state containing current array + its epsilon transitions 
	public static DFAstate Eclosure(ArrayList<Node> nodes){
		
		ArrayList<Node> closure = new ArrayList<Node>();
		
		Stack<Node> stack = new Stack<Node>();
		for(Node n: nodes){
			stack.push(n);
			closure.add(n);
		}
					
		Node temp,val;
		while(!stack.isEmpty()){
			temp = stack.pop();
			//get epsilon transitions for node temp
			//iterate over its hash map for any 'epX' key
			for (String name: temp.getNextStates().keySet()){
	            if(name.contains("ep")){
	            	val = temp.getNextStates().get(name);
	            	stack.push(val);
	            	if(!exists(val, closure))
	            		closure.add(val);
	            }
	            	    
			}
			
		}
		
		return new DFAstate(closure);
		
	}
	
	public static ArrayList<Node> getDirectStates(DFAstate state, String InputSymbol){
		ArrayList<Node> previousNode = state.getStateNodes();
		ArrayList<Node> DirectStates = new ArrayList<Node>();
		Node node ;
		for (Node n: previousNode){
			node = n.getNextState(InputSymbol);
			if(node != null)
			DirectStates.add(node);
		}
		return DirectStates;	
	}
	
	private static DFAstate isEqualStates(DFAstate src,ArrayList<DFAstate> lst){
		
		for (DFAstate e : lst ){
			if(src.isEqual(e))
				return e;
		}
		
		return null;
	}
	
	public static Map<DFAstate, ArrayList<DFAstate>> DFATransitionTable(Node startNode,ArrayList<String> inputs){
		
		// "2D" transition table
//		HashMap<DFAstate, ArrayList<DFAstate>> TransTable = new HashMap<DFAstate, ArrayList<DFAstate>>();
		Map<DFAstate, ArrayList<DFAstate>> TransTable = new LinkedHashMap<DFAstate, ArrayList<DFAstate>>();
		
		//construct first DFA state
		ArrayList<Node> t = new ArrayList<Node>();
		t.add(startNode);
		DFAstate startState = Eclosure(t);
		
		//Dstates array list
		ArrayList<DFAstate> D = new ArrayList<DFAstate>();
		D.add(startState);
		
		int i=0;
		DFAstate tempDFAstate;
		DFAstate resultDFAstate;
		DFAstate finalDFAstate;
		while(i<D.size()){
			
			tempDFAstate = D.get(i);
			//7ot hna new row in 2d array
			TransTable.put(tempDFAstate, new ArrayList<DFAstate>());
			System.out.println(tempDFAstate.getStateID());
			
			ArrayList<Node> dirStates;
			for(int j=0;j<inputs.size();j++){
				dirStates = getDirectStates(tempDFAstate,inputs.get(j));
				if(dirStates.size() !=0){
					resultDFAstate = Eclosure(dirStates);
					finalDFAstate = isEqualStates(resultDFAstate,D);
					if(finalDFAstate == null){
						D.add(resultDFAstate);
						finalDFAstate =  resultDFAstate;
					}else{
						char q = (char)(DFAstate.getch()-1);
						DFAstate.setch(q);
					}
					//add column(for a specific input) in specific row
					TransTable.get(tempDFAstate).add(finalDFAstate);
				}
				else{
					//putting phi (dead) state
					//add column(for a specific input) in specific row
					TransTable.get(tempDFAstate).add(new DFAstate('#'));
				}
				
			}
			
			i++;
		}
		
	return TransTable;	
	}
	
	public static Map<DFAstate, ArrayList<DFAstate>> minimization(Map<DFAstate, ArrayList<DFAstate>> map){
		Map<DFAstate, ArrayList<DFAstate>> minimized = new LinkedHashMap<DFAstate, ArrayList<DFAstate>>();
		ArrayList<DFAstate> nonTerminal = new ArrayList<DFAstate>();
		ArrayList<DFAstate> DeadState = new ArrayList<DFAstate>();
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<String> types = new ArrayList<String>();
		
		for (DFAstate name: minimized.keySet()){

			if(name.isGoalState()==false)
				nonTerminal.add(name);
			else{
			    nodes =  name.getStateNodes();
				for(Node e: nodes){
					if(e.isGoalState()==true)
						types.add(e.getType());
				}
				int maxPriority = 0;
				for(int i=0; i<types.size();i++){
					
					
				}
			}
				
			System.out.println("KEY: "+name.getStateID());
			System.out.println("GOAL STATE: "+name.isGoalState());
			ArrayList<DFAstate> val = map.get(name);
			System.out.println("VALUE");
			for(DFAstate e: val)
				System.out.println(e.getStateID());
			System.out.println("----------------------------");	
		}
		return minimized;
		
	} 
	
	
	
	
}
