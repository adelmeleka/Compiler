package parser;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class TestParser {
	final static String filePath = "CFG grammer3.txt";
	public static void main(String[] args) {		
		
		//Step 1: Read CFG and find terminals and non-terminals
		CFGrammer dealWithGrammer = new CFGrammer(filePath);
		try {
			dealWithGrammer.dealWithFile();
		} catch (FileNotFoundException e) {
			System.out.println(e);
			System.exit(0);
		}
		
		// Note : \L indicates epsilons 
		LL1Grammar.terminals = dealWithGrammer.getTerminals();
		LL1Grammar.terminals.add("$");
		LL1Grammar.nonTerminals = dealWithGrammer.getNonTerminals();
		LL1Grammar.cfg = dealWithGrammer.getCfg();	
		System.out.println("\nTerminals:\n"+LL1Grammar.terminals);
		System.out.println("\nnon Terminals:\n"+LL1Grammar.nonTerminals);
		System.out.println("\ncfg:\n"+LL1Grammar.cfg);
		System.out.println("--------------------------------------------------------------------");
		
		
		//Step 2: Calculate Firsts for each symbol
		Firsts firsts = new Firsts();
		HashMap<String,ArrayList<String>> first = firsts.computeFirsts();
		System.out.println("\nFirst");
		System.out.println(first);
		System.out.println("--------------------------------------------------------------------");
		
		
		//Step 3: Calculate Follows for each symbol
		Follows follows = new Follows();
		HashMap<String,ArrayList<String>> follow = follows.computeFollows();
		System.out.println("\nFollow");
		System.out.println((follow));
		System.out.println("--------------------------------------------------------------------");
		
		//Step 4: check if grammar is LL(1) or not 
		if(LL1Grammar.isLL1==false){
			System.out.println("\n"+new Exception("This Grammar is not LL1"));
			System.exit(0);
		}
		System.out.println("--------------------------------------------------------------------");
		
		//Step 5: Construct Parsing table
		ParsingTable.EvaluateParsingTable(LL1Grammar.cfg, LL1Grammar.terminals,
										  LL1Grammar.nonTerminals, first, follow);
		System.out.println("\nParsing Table\n");
//		ParsingTable.ParsingTable
		ParsingTable.ShowParsingTable(LL1Grammar.terminals,LL1Grammar.nonTerminals);
		System.out.println("--------------------------------------------------------------------");
		
		//Step 6 : left most Derivation
		//token for grammar6
		ArrayList<String> tokens = new ArrayList<String>();
		tokens.add("'float'");
		tokens.add("'id'");
		tokens.add("';'");
//		tokens.add("'id'");
//		tokens.add("'='");
//		tokens.add("'EXPRESSION'");
//		tokens.add("';'");
		tokens.add("$");	
		System.out.println(tokens);
		LMDrivation.LMD(LL1Grammar.terminals,LL1Grammar.nonTerminals,ParsingTable.ParsingTable ,tokens);
		
		
		
	}
	


}
