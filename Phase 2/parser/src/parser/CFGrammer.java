package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class CFGrammer {
	private String path;	

	private ArrayList <String> terminals;
	private ArrayList <String> nonTerminals;
	private HashMap<String,ArrayList<String>> cfg;
	
	public CFGrammer(String path){
		this.path=path;
		this.terminals = new ArrayList<String>();
		this.nonTerminals = new ArrayList<String>();
		this.cfg = new HashMap<String,ArrayList<String>>();
	}
	public ArrayList<String> getTerminals() {
		return terminals;
	}

	public ArrayList<String> getNonTerminals() {
		return nonTerminals;
	}

	public HashMap<String, ArrayList<String>> getCfg() {
		return cfg;
	}
	
	@SuppressWarnings({"resource"})
	public void dealWithFile() throws FileNotFoundException{
		File file = new File(path);
		Scanner input = new Scanner(file);
		ArrayList <String> splitRHS;
		while(input.hasNext()){
			 String line=null;
			 String []splitLHSandRHS = new String[2];
			 line = input.nextLine();
			 splitLHSandRHS=line.split(" ::= ");
			 //Neglect # symbol
			 splitLHSandRHS[0] = splitLHSandRHS[0].substring(2);
			 //add to terminals
			 nonTerminals.add(splitLHSandRHS[0]);
			 //determine non terminals
			 splitRHS = new ArrayList <String>(Arrays.asList(splitLHSandRHS[1].split(" ")));
			 for(String e :splitRHS){
				 //check presence of single quotations
				 if(e.indexOf('\'') >= 0)
					 if(!terminals.contains(e))
						 terminals.add(e);
						 
			 }
			 
			 //if terminal label contain more than one expression
			 if(splitLHSandRHS[1].contains(" | ")){
				 splitRHS = new ArrayList <String>(Arrays.asList(splitLHSandRHS[1].split(" \\| ")));
				 cfg.put(splitLHSandRHS[0], splitRHS);
			 }	
			 else{
				 cfg.put(splitLHSandRHS[0], new ArrayList <String>());
				 cfg.get(splitLHSandRHS[0]).add(splitLHSandRHS[1]);
			 }
		}
	}

}
