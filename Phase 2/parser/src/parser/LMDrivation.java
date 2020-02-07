package parser;

import java.util.ArrayList;
import java.util.Stack;

import javafx.util.Pair;

public class LMDrivation {
	public static void LMD(ArrayList<String> terminals, 
			ArrayList<String> nonterminals ,Pair <String, String> [][] parsingTable, ArrayList<String> tokens){ 
		    int ptrTokens=0;
		    Stack<String> stack = new Stack<String>(); 
		    stack.push("$");
		    stack.push(nonterminals.get(0));
		    ArrayList<Pair<String , String>> Derivation = new  ArrayList<Pair<String , String>>();
		    for(int j = 0; j < terminals.size(); j++) {   
		        System.out.print(terminals.get(j));
		    }  
//		    while (!stack.isEmpty())
//		    {
//		        System.out.println("pop"+stack.pop());
//		    }
		    boolean terminalFound =false;
		    boolean nonterminalFound = false;
		    while((String)stack.peek()!="$"){
		    	 terminalFound = terminals.contains((String)stack.peek());
		    	  nonterminalFound = nonterminals.contains((String)stack.peek());
		    	 int currentIp;
		    	 int currentNonterminal ;
				if(terminalFound == true && tokens.get(ptrTokens).equals((String)stack.peek())){
					//System.out.println("7mada");
		    		stack.pop();
		    		System.out.println("match "+tokens.get(ptrTokens));
		    		ptrTokens++;
		    		terminalFound = false;
		    	}
				else if(terminalFound == true && !(tokens.get(ptrTokens).equals((String)stack.peek()))){
					//error;
					System.out.println("Error: unmatched symbol is inserted , missing: "+ tokens.get(ptrTokens));
					stack.pop();
					terminalFound = false;
					
				}
		    	else if(nonterminalFound == true){
		    		 currentIp = terminals.indexOf(tokens.get(ptrTokens));
		    		 currentNonterminal = nonterminals.indexOf((String)stack.peek());
		    		
		    		 if( parsingTable[currentNonterminal][currentIp]!=null && parsingTable[currentNonterminal][currentIp].getKey()!= "Synch" ){
		    			 //System.out.println("loulou");
		    			stack.pop();
		    		
		    			String RHS[] = parsingTable[currentNonterminal][currentIp].getValue().split(" ");
		    			if(!parsingTable[currentNonterminal][currentIp].getValue().contains("\\L")){
		    				for(int i = RHS.length-1 ; i>=0; i-- ){
		    					stack.push(RHS[i]);
		    				}
		    			}
		    			Derivation.add(new Pair<String, String>(parsingTable[currentNonterminal][currentIp].getKey(),parsingTable[currentNonterminal][currentIp].getValue()));
		    			System.out.println("Output "+ parsingTable[currentNonterminal][currentIp].getKey() + "--> "+ parsingTable[currentNonterminal][currentIp].getValue() );
		    			}
		    
		    		 else if(( parsingTable[currentNonterminal][currentIp]==null) || ( (parsingTable[currentNonterminal][currentIp].getKey()== "Synch") &&(stack.peek().equals(nonterminals.get(0)))) ){
		    			 //error;
		    			 System.out.println("Error: illegal "+(String)stack.peek() + " -discard "+tokens.get(ptrTokens)); 
		    			 ptrTokens++;
		    			 
		    		 }
		    		 else if(parsingTable[currentNonterminal][currentIp].getKey()== "Synch"){
		    			 //error;
		    			 System.out.println("Error: M [ " + (String)stack.peek() +" , "+tokens.get(ptrTokens) +"] = synch so "+(String)stack.peek()+"has been popped");
		    			 stack.pop();
		    		 }
		    		 nonterminalFound = false;
		    	}
		    	else{
		    		System.out.println("error");
		    	}
		    }
		    if(stack.peek()=="$" && tokens.get(ptrTokens)=="$")
		    System.out.println("Successful Completion :D");		
	}

}
