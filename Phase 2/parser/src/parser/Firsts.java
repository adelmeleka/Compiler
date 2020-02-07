package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
public class Firsts extends LL1Grammar{
	
	public Firsts(){
		first =  new HashMap<String,ArrayList<String>>();
	}
	
	private void regularExperssionPath(Stack <String>stack,ArrayList<String> certainTerminalFirsts,String e,int i,String [] arr){
		int checkFirstToken=0;
		//check for recursion in token ex : Term = Term
		if(arr.length==1&&arr[0].equals(nonTerminals.get(i))){
			 if(!certainTerminalFirsts.contains("\\L"))
					 certainTerminalFirsts.add("\\L");
			 else{
				 certainTerminalFirsts.remove("\\L");
				 certainTerminalFirsts.add("\\L");
			 }
			return;
		}
		
		
		while(!stack.peek().equals("$")){
			String token = stack.pop();
			//if starts with a terminal
			if(checkFirstToken==0&&isTerminal(token)){
				if(!certainTerminalFirsts.contains(token))
					certainTerminalFirsts.add(token);
				break;
			}	
			checkFirstToken=1;
			
			
			//if terminal token
			if(isTerminal(token)){
				dealingWithTerminalToken(certainTerminalFirsts, token);
			}
			
			//non terminal token
			else if(isNonTerminal(token)){
				if(first.containsKey(token))
					dealingWithNonTerminalToken(stack, token);
				else
					break;
			}
			
			//epsilons
			else if(token.equals("\\L")&&stack.peek().equals("$")){
				if(!certainTerminalFirsts.contains(token)){
					certainTerminalFirsts.add(token);
				}
				else{
					certainTerminalFirsts.remove(token);
					certainTerminalFirsts.add(token);
				}
				break;
			}
			
		}
	}
	
	public  HashMap<String,ArrayList<String>> computeFirsts(){
		ArrayList<String> temp;
		ArrayList<String> certainTerminalFirsts;
		Stack <String>stack = null;
		
		for(int i=nonTerminals.size()-1;i>=0;i--){
			// ex: temp = [DECLARATION, IF, WHILE, ASSIGNMENT]
			temp = cfg.get(nonTerminals.get(i));
			certainTerminalFirsts = new ArrayList <String>();
			
			ArrayList <String>tempFirsts;
			//ex : e = DECLARATION
			for(String ter: temp){
				tempFirsts = new ArrayList <String>();
				stack = new Stack<String>();
				stack.push("$");
				//split each token e in case there are spaces 
				String [] arr =ter.split(" ");
				for(int j=arr.length-1;j>=0;j--)
					stack.push(arr[j]);
				regularExperssionPath(stack, tempFirsts, ter, i,arr);
				for(String e:tempFirsts){
					if(!certainTerminalFirsts.contains(e))
						certainTerminalFirsts.add(e);
					else 
						isLL1=false;
				}
			}
			first.put(nonTerminals.get(i), certainTerminalFirsts);
		}
		return first;
	}
	
}
