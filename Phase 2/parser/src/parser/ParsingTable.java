package parser;

import java.util.HashMap;
import java.util.Map.Entry;

import javafx.util.Pair;

import java.util.ArrayList;

public class ParsingTable {
	
	//each entry in 2D array is a Pair<Symbol,Prodution>
	//rows -> Non-Terminal LHS symbols
	//cols -> Terminal symbols
	public static Pair <String, String> [][] ParsingTable;
		
	
	@SuppressWarnings("unchecked")
	public static void EvaluateParsingTable(HashMap<String,ArrayList<String>> CFG,
											ArrayList<String> terminals,
											ArrayList<String> nonTerminals,
											HashMap<String,ArrayList<String>> first,
											HashMap<String,ArrayList<String>> follow){
		///should remove '' in each first & follow value
		
		//initialise 2D array
		ParsingTable = new Pair[nonTerminals.size()][terminals.size()];
		
		///// iterate through all procutions in CFG
//		for (Entry<String, ArrayList<String>> entry : CFG.entrySet()) {
		for (String entry : nonTerminals) {
		    
			String key = entry;
			ArrayList<String> val = CFG.get(key);
			
			//initialize row and col indices for a production 
			int rowIndex = nonTerminals.indexOf(key);
			int colIndex;
			
			//iterate through all possible RHSs in 1 production (ORs)
			for (String s:val){
				//check for epsilon in RHS
				if(!s.equals("\\L")){
					//RHS not epsilon -> take first of RHS of the production
					String [] rhs = s.split(" ");
					int i = 0;
					ArrayList<String> firstArr;
					while(true){
//						System.out.println("in while "+rhs[i]);
						//if a char in RHS is terminal
						if(terminals.contains(rhs[i])){
							
							colIndex = terminals.indexOf(s.split(" ")[i]);
							
							if(ParsingTable[rowIndex][colIndex] != null){
								System.out.println("Grammar is not feasible");
								System.exit(0);
							}
								
							ParsingTable[rowIndex][colIndex] = new Pair<String, String>(key,s);
							
							break;
						
						}else{
							//if a char in RHS is not a terminal
							
							firstArr = first.get(rhs[i]);
//							if(firstArr == null)	break;				
//							System.out.println("fff: "+firstArr);
//							System.out.println("size: "+firstArr.size());
							
							for(int j=0;j<firstArr.size();j++){
																
								String firstElement = firstArr.get(j);
								if(firstElement == null)
									System.out.println("null");
								
								if(!firstElement.equals("\\L")){
									colIndex = terminals.indexOf(firstElement);
									
									if(ParsingTable[rowIndex][colIndex] != null){
										System.out.println("Grammar is not feasible");
										System.exit(0);
									}
									
									ParsingTable[rowIndex][colIndex] = new Pair<String, String>(key,s);
									
								}
								else{
									//if one of the first of a char is epsilon
									
									if((i+1) == rhs.length){
										//take follow of LHS of the production
										
										ArrayList<String> followArr = follow.get(key);
										for(String followElement:followArr){
											
											colIndex = terminals.indexOf(followElement);
											
											if(ParsingTable[rowIndex][colIndex] != null){
												System.out.println("Grammar is not feasible");
												System.exit(0);
											}
											
											ParsingTable[rowIndex][colIndex] = new Pair<String, String>(key,s);
										}
										
										break;
									}
									
									//if not the last character
									i++;
																			
								}
//								break;	
							
							}
							break;
						}
							
					}	
					
					
				}else{
					
					//RHS is epsilon -> take follow of LHS of the production
					ArrayList<String> followArr = follow.get(key);
					for(String followElement:followArr){
						
						colIndex = terminals.indexOf(followElement);
						
						if(ParsingTable[rowIndex][colIndex] != null){
							System.out.println("Grammar is not feasible");
							System.exit(0);
						}
						
						ParsingTable[rowIndex][colIndex] = new Pair<String, String>(key,s);
						
					}
					
				}		
		    
		}
		
		
			// add synch entry -> in follow of LHS
			
			ArrayList<String> followArr = follow.get(key);
			for(String followElement:followArr){
				
				colIndex = terminals.indexOf(followElement);
				
				if(ParsingTable[rowIndex][colIndex] == null)
					ParsingTable[rowIndex][colIndex] = new Pair<String, String>("Synch","Synch");
				
			}
			
			
			
			
		}
		
		
	}
	
	
	public static void ShowParsingTable(ArrayList<String> terminals,
										 ArrayList<String> nonTerminals){
		///more beautiful output by mainting the max length of a production
		String term ="\t";
		for(String s:terminals)
			term += s+"\t";
		System.out.println(term+"\n");
		
		for(int i=0; i<nonTerminals.size(); i++){
			String temp = nonTerminals.get(i)+"\t";
			for(int j=0; j<terminals.size();j++){
				
				if(ParsingTable[i][j] == null)
					temp += "none\t";
				else
					temp += ParsingTable[i][j] +"\t";
			}
			System.out.println(temp);
			System.out.println("\n");
		}
	}
	
	
	
	
}
