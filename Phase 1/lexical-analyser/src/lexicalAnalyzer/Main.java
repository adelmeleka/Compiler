package lexicalAnalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import NFAs.NFA;

public class Main {
    public static void main(String[] args) {

    	ReadRegExFile readRegExFile = new ReadRegExFile("regix.txt");
        RegexExtractor regexExtractor = new RegexExtractor(readRegExFile.readFile());
        HashMap<String,ArrayList<String>> lablesHashMaps = regexExtractor.getRegexFromFile(); 
     //   System.out.println(regexExtractor);
       // System.out.println("Terminal: "+regexExtractor.getTerminalLabels());
       // System.out.println("Non Terminal: "+regexExtractor.getNonTerminalLabels());
        
        
        NFA nfa = new NFA(lablesHashMaps,regexExtractor.getTerminalLabels(),regexExtractor.getNonTerminalLabels()); 
        SubGraph finalNFA =  nfa.getFinalNFA();
        System.out.println("=======================================================================================");
        System.out.println(finalNFA.getStartNode());
    }
}