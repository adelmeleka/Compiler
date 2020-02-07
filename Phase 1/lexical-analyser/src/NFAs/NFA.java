package NFAs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

import lexicalAnalyzer.SubGraph;

public  class NFA {
	
	private ArrayList <String> regixInput;
	private ArrayList <SubGraph> NonTerminalNFAsList;
	private ArrayList <SubGraph> finalNFAs;
	SubGraph returnNFA;
	public SubGraph getFinalNFA() {
		return returnNFA;
	}

	private Stack<String> stack;
	public  NFA(HashMap<String,ArrayList<String>> lablesHashMaps,ArrayList <String> terminalLabels,
															ArrayList <String> nonTerminalLabels){
		finalNFAs = new  ArrayList <SubGraph>();
		int count=0;
		for(String key :nonTerminalLabels){
			regixInput=null;
			stack = null;
			regixInput=lablesHashMaps.get(key);
			if(key.equals("keyWords")||key.equals("relop")){
				buildKeyWordsAndRelopNFA();
				System.out.println(("ep"+count)+" ->"+key);
				count++;
			}
			else{
				//all RE's of certain key
				for (String RE : regixInput){
					NonTerminalNFAsList = new ArrayList<SubGraph>();
					//Split RE in the stack
					stack = readRegixInputInRightFormat(RE);
					//Build NFA for each RE of specific key
					buildNonTerminalDigitNFA(stack,terminalLabels,nonTerminalLabels,lablesHashMaps);
				}
				System.out.println(("ep"+count)+" ->"+key);
				count++;
				finalNFAs.add(SubGraph.finalOR(NonTerminalNFAsList));
			}
		}
		returnNFA=SubGraph.finalOR(finalNFAs);
	}
	private void buildKeyWordsAndRelopNFA(){
		NonTerminalNFAsList = new ArrayList<SubGraph>();
		for(String key : regixInput){
			key = key.replaceAll("\\s*", "");
			SubGraph graph = SubGraph.createSubGraph(key.charAt(0)+"");
			for(int i=1;i<key.length();i++){
				if(i == key.length()-1){
					graph = SubGraph.concatinateAndSetGoal(graph, key.charAt(i)+"",key);
				}else{
					graph = SubGraph.concatinate(graph, key.charAt(i)+"");
				}
			}
			NonTerminalNFAsList.add(graph);
		} 
		finalNFAs.add(SubGraph.finalOR(NonTerminalNFAsList));
	}
	
	
	private Stack<String> readRegixInputInRightFormat(String key){
			stack = new Stack<String>();
			String re="";
			for(int i=0;i<key.length();i++){
				//space at the end of the RE
				if(i==key.length()-1&&key.charAt(i)==' ')
					continue;
				if(key.charAt(i)=='|'||key.charAt(i)==' '||key.charAt(i)=='&'||key.charAt(i)=='*'||key.charAt(i)=='+'
						||key.charAt(i)=='('||key.charAt(i)==')'){
					if(re.length()!=0){
						stack.push(re);
						re="";
					}
					if(key.charAt(i)==' '||key.charAt(i)=='&')
						stack.push("&");
					else
						stack.push(key.charAt(i)+"");
				}
				else{
					re+=key.charAt(i);
				}
			}
			if(re.length()!=0)
				stack.push(re);
			//reverse
			return stack;
		}
	
	private void buildNonTerminalDigitNFA(Stack<String> stack,ArrayList <String> terminalLabels,
																	ArrayList <String> nonTerminalLabels,
																	HashMap<String,ArrayList<String>> lablesHashMaps){
		SubGraph graph = null;
		String temp="";
		String operation = "";
		while(!stack.isEmpty()){
			temp=stack.pop();				
			//most be splited ex :digits
			if(nonTerminalLabels.contains(temp)){
				for (String RE : lablesHashMaps.get(temp)){
					Stack<String> tempStack1 = readRegixInputInRightFormat(RE);
					Stack <String>tempStack2 = new Stack<String>();
					while(!tempStack1.isEmpty())
						tempStack2.push(tempStack1.pop());
					while(!tempStack2.isEmpty())
						stack.push(tempStack2.pop());
				}
				continue;
			}
			if(temp.equals(")")||temp.equals("("))
				continue;
		
			 if(temp.equals("|")||temp.equals("&")||temp.equals("*")||temp.equals("+")){
				if(graph==null)
					operation=temp;
				else{
						switch(temp){
						case  "&":
							graph = SubGraph.concatinate(graph, stack.pop());
							break;
						case  "|":
							graph = SubGraph.or(new ArrayList<SubGraph> (Arrays.asList(graph,SubGraph.createSubGraph(stack.pop()))));
							break;
						case  "*":
							graph = SubGraph.cleanClosure(graph);
							break;
						case "+":
							graph = SubGraph.postiveClosure(graph);
							break;
						}
				}
			}
			else{
				if(terminalLabels.contains(temp)){
					if(graph==null)
						graph = SubGraph.createSubGraph(temp);
					//RE ends with a operation cleanclosure or +ve colsure
					if(!operation.equals("")){
						switch(operation){
						case  "*":
							graph = SubGraph.cleanClosure(graph);
							break;
						case "+":
							graph = SubGraph.postiveClosure(graph);
							break;
						}
						operation="";
					}
				}
			}
		}
		NonTerminalNFAsList.add(graph);
	}
	
	

}
