package lexicalAnalyzer;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExtractor {

    private ArrayList<String> regexInFile;
    private ArrayList<String> patternList ;
	private ArrayList<String> keywords ;
    private ArrayList<String> punctuation ;
    private ArrayList<String> terminalLabels ;
    private ArrayList<String> nonTerminalLabels ;
    private ArrayList<String> terminalSymbols ;
    private ArrayList<String> labels ;
    private ArrayList<String> values ;
    private HashMap<String,ArrayList<String>> lablesHashMaps ;


    public ArrayList<String> getNonTerminalLabels() {
        return nonTerminalLabels;
    }

    public ArrayList<String> getTerminalLabels() {
        return terminalLabels;
    }

    public ArrayList<String> getTerminalSymbols() {
        return terminalSymbols;
    }

    private RegexExtractor(){ }

    public RegexExtractor(String fileName) {
        ReadRegExFile readRegExFile = new ReadRegExFile(fileName);
        prepare(readRegExFile.readFile());
    }

    private void prepare(ArrayList<String> regexInFile) {
        this.regexInFile = regexInFile;
        this.patternList = new ArrayList<>();
        this.keywords = new ArrayList<>();
        this.punctuation = new ArrayList<>();
        this.terminalLabels = new ArrayList<>();
        this.nonTerminalLabels = new ArrayList<>();
        this.terminalSymbols = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.values = new ArrayList<>();
        this.lablesHashMaps = new HashMap<>();

        String charecter = "(.*)([a-zA-Z](\\s)*\\|(\\s)*[a-zA-Z])*(.*)";
        String smallLettersPatterns = "(.*)[a-z][\\s]*-[\\s]*[a-z](.*)";
        String cabitalLettersPattern = "(.*)[A-Z][\\s]*-[\\s]*[A-Z](.*)";
        String digitPatter = "(.*)[0-9][\\s]*-[\\s]*[0-9](.*)";

        patternList.add(smallLettersPatterns);
        patternList.add(cabitalLettersPattern);
        patternList.add(digitPatter);
    }


    public RegexExtractor(ArrayList<String> regexInFile) {
        prepare(regexInFile);
    }

    public HashMap<String,ArrayList<String>> getRegexFromFile(){
        Pattern pattern;
        Matcher matcher ;
        boolean matches = false ;

        int index =0 ;
        for (String line : regexInFile){
            values.clear();
            if (line.charAt(0) == '{'){                                     //read Keywords
                keywords.addAll(getKeyWords(line));
            }

            else if (line.charAt(0) == '['){                                // read punctuation
                punctuation.addAll(getPunctuations(line));
            }
            else{
                index = getLabelIndex(line);
                labels.add(line.substring(0,index).replaceAll(" ",""));
                values.add(line.substring(index+1,line.length()));
            }
            //get terminal char
            for (String regex : patternList){
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(line);
                matches = matcher.matches();
                if (matches){
                    String[] temp = values.get(values.size()-1).replaceAll(" ","").split("\\|");
                    ArrayList<String> arrayToArrayList = new ArrayList<String>(Arrays.asList(temp));
                    ArrayList<String> valuesList = new ArrayList<>();
                    terminalLabels.add(labels.get(labels.size()-1));
                    lablesHashMaps.put(labels.get(labels.size()-1), arrayToArrayList);
                    for (String s : arrayToArrayList){
                        valuesList.addAll(regexWithRangeToTerminaValues(s));
                    }
                    lablesHashMaps.put(labels.get(labels.size()-1), valuesList);
                    break;
                }
            }
            if (!matches && values.size() > 0){
                ArrayList<String> NonTerminalRegex = getNonTerminalRegeOrOperators(values.get(values.size()-1));
                lablesHashMaps.put(labels.get(labels.size()-1), NonTerminalRegex);
            }
        }
        lablesHashMaps.put("keyWords" , keywords);
        lablesHashMaps.put("punctuations" , punctuation);
        identifyTerminalAndNonTerminal();
        for (String key : lablesHashMaps.keySet()) {
        	ArrayList<String> arrayList = lablesHashMaps.get(key);
        	ArrayList<String> newArrayList = new ArrayList<String>();
        	for (String s : arrayList){
        		s = s.replaceFirst("\\s*", "");
        		newArrayList.add(s);
        	}
        	lablesHashMaps.put(key, newArrayList);
        }
        return lablesHashMaps;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (lablesHashMaps.isEmpty()){
            throw new IllegalArgumentException("you must run getRegexFromFile() function first.");
        }
        for (String key : lablesHashMaps.keySet()) {
            stringBuilder.append(key + "  "+ lablesHashMaps.get(key) +"\n");
        }
        return stringBuilder.toString();
    }



    private void identifyTerminalAndNonTerminal(){
        String [] strings ;
        boolean isTerminal ;
        for (String key : lablesHashMaps.keySet()) {
            isTerminal = true;
            for (String s :lablesHashMaps.get(key)){
                s = s.replaceAll("\\s+","");
                strings = s.split("[^\\w']+");
                for(String st : strings){
                    if (lablesHashMaps.get(st) != null){
                        isTerminal = false;
                        break;
                    }
                }
                if (s.length() >= 2){
                    isTerminal = false;
                    break;
                }
            }
            if(isTerminal){
                if(terminalLabels.contains(key)==false ){
                    terminalLabels.add(key);
                }
            }
            else{
                if (nonTerminalLabels.contains(key) == false){
                    nonTerminalLabels.add(key);
                }
            }
        }
        terminalLabels.remove("keyWords");
        for (String s : terminalLabels){
            terminalSymbols.addAll(lablesHashMaps.get(s));
        }
    }

    private ArrayList<String> getKeyWords(String line){
        String[] words;
        ArrayList<String> keywords = new ArrayList<>();
        line  = line.replaceAll("(\\{)(\\s)*","");
        line  = line.replaceAll("(\\s)*\\}","");
        words = line.split(" ");
        for (String word : words){
            keywords.add(word);
        }
        return keywords;
    }

    private ArrayList<String> getPunctuations(String line){
        String[] words;
        ArrayList<String> punctuation = new ArrayList<>();
        line  = line.replaceAll("(\\[)(\\s)*","");
        line  = line.replaceAll("(\\s)*\\]","");
        words = line.split(" ");
        for (String word : words){
            if (word.length()>=2){
                punctuation.add(String.valueOf(word.charAt(1)));
            }else {
                punctuation.add(word);
            }
        }
        return punctuation;
    }

    private int getLabelIndex(String line){
        int index = 0;
        index = line.indexOf('=');
        if (line.indexOf('=') == -1 || line.indexOf('=')>line.indexOf(":") && line.indexOf(":") !=-1){
            index = line.indexOf(':');
        }
        return index;
    }

    private ArrayList<String> regexWithRangeToTerminaValues(String line){
        ArrayList<String> strings = new ArrayList<>();
        if (line.charAt(1) == '-'){
            int i = (int)line.charAt(0);
            int j = (int)line.charAt(2);
            for (int z = i ; z <= j ; z++){
                strings.add(String.valueOf((char)z));
            }
        }
        return strings;
    }

    private ArrayList<String> getNonTerminalRegeOrOperators(String line){

        Pattern  pattern = Pattern.compile("(.*)\\((.*)");
        Matcher matcher = pattern.matcher(line);
        boolean matches = matcher.matches();
        if (matches){
            int index = 0;
            Stack<Integer> stack = new Stack<>();
            String string =  line;
            ArrayList<String> arrayListOfregex = new ArrayList<>();
            for (int i = 0; i<string.length() ; i++){
                if (string.charAt(i) == '('){
                    stack.push(i);
                }
                if(string.charAt(i) == ')' && stack.size() != 0 ){
                    stack.pop();
                }
                if(string.charAt(i) == '|' && stack.empty() ){
                    arrayListOfregex.add(line.substring(index,i));
                    index = i+1;
                }
                if (i == string.length()-1)
                {
                    arrayListOfregex.add(line.substring(index,i+1));
                }
            }
            return arrayListOfregex;
        }
        // get operators
        else{
            String currentString = line.replaceAll( "\\\\", "" );
            String[] temp = currentString.split("\\|");
            ArrayList<String> arrayListOfregex = new ArrayList<String>(Arrays.asList(temp));
            return arrayListOfregex;
        }
    }

}
