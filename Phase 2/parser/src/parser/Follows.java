package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class Follows extends LL1Grammar{
	
	
	public Follows(){
		follow =  new HashMap<String,ArrayList<String>>();
		initializeFollows();
		
	}
	private void initializeFollows(){
		//starting of the grammar always have no follows so its initialized with $
		follow.put(nonTerminals.get(0), new ArrayList<String>());
		follow.get(nonTerminals.get(0)).add("$");
	}
	
	public  HashMap<String,ArrayList<String>>  computeFollows(){ 
		ArrayList<String> temp;
		ArrayList<String> certainTerminalFollows;
		Stack <String>stack = null;
		for(int i=0;i<nonTerminals.size();i++){
			String nonTerminal = nonTerminals.get(i);
			if(i==0)
				certainTerminalFollows=follow.get(nonTerminal);
			else
				certainTerminalFollows = new ArrayList <String>();
			//loop using each terminal on all other terminals 
			for(int j=0;j<nonTerminals.size();j++){
				String currentnonTerminalChecking = nonTerminals.get(j);
				temp = cfg.get(currentnonTerminalChecking);
				//skip terminal itself RE
				/*if(nonTerminal.equals(currentnonTerminalChecking))
					continue;*/
			
				//check presence of terminal in RE's
				for(String ter: temp){
					int checkFirstToken=0;
					String [] arr =ter.split(" ");
					//terminal not found in this expression
					if(!Arrays.asList(arr).contains(nonTerminal))
						continue;
					//token found in this RE but at the end so follow are added
					if(Arrays.asList(arr).indexOf(nonTerminal)==arr.length-1){
						if(currentnonTerminalChecking.equals(nonTerminal))
							continue;
						for(String e:follow.get(currentnonTerminalChecking) ){
							if(!certainTerminalFollows.contains(e))
								certainTerminalFollows.add(e);
						}
						continue;
					}
					stack = new Stack<String>();
					stack.push("$");
					int indexOfLastPresent = 0;
					int numOfTimesOfPresence=0;
					//handle multiple presence of no terminal
					for(int q=arr.length-1;q>=0;q--)
						if(arr[q].equals(nonTerminal)){
							numOfTimesOfPresence++;
							indexOfLastPresent=q;
						}
					if(numOfTimesOfPresence==1)
						for(int k=arr.length-1;k>=indexOfLastPresent+1;k--)
							stack.push(arr[k]);
					else
						for(int k=arr.length-1;k>=indexOfLastPresent;k--)
							stack.push(arr[k]);
					
					while(!stack.peek().equals("$")){
						String token = stack.pop();
						//if starts with a terminal
						if(checkFirstToken==0&&isTerminal(token)){
							if(!certainTerminalFollows.contains(token))
								certainTerminalFollows.add(token);
							break;
						}	
						checkFirstToken=1;
						//if terminal token
						if(isTerminal(token)){
							dealingWithTerminalToken(certainTerminalFollows, token);
						}
						//non terminal token
						else if(isNonTerminal(token)){
							if(first.containsKey(token))
								dealingWithNonTerminalToken(stack, token);
							else
								break;
						}
						else if(stack.peek().equals("$")){
							for(String e:follow.get(currentnonTerminalChecking) ){
								if(!certainTerminalFollows.contains(e))
									certainTerminalFollows.add(e);
							}
						}
					}
				}
			}
			follow.put(nonTerminal, certainTerminalFollows);
		}
		return follow;
	}
	
	
}