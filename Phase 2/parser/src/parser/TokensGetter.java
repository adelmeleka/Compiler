package parser;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.*;

public class TokensGetter {
    private Stack<String> tokenStack = new Stack<>();
    private Stack<String> typeStack  = new Stack<>();
    private ArrayList<Pair<String,String>> inputTokens = new ArrayList<>();
    private String path;

    public TokensGetter( String codeFile) {
        ProcessBuilder processBuilder = new ProcessBuilder();

        // -- Linux --

        // Run a shell command
        //  processBuilder.command("bash", "-c", "./a.out");

        // Run a shell script
//        processBuilder.command("./lex.out");

        // -- Windows --

        // Run a command
        //processBuilder.command("cmd.exe", "/c", "dir C:\\Users\\mkyong");

        // Run a bat file
        //processBuilder.command("C:\\Users\\mkyong\\hello.bat");
//
//        File oldfile =new File(codeFile);
//        File newfile =new File("TestProgram.txt");
//        if(oldfile.renameTo(newfile)){
//            try {
//
//                Process process = processBuilder.start();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }else{
//            System.out.println("Rename failed");
//        }


        String path = "tmp.txt";
        File file = new File(path);
        Scanner input = null;
        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> splitRHS;
        while(input.hasNext()){
            String[] lineStrings = input.nextLine().split(" ");
            tokenStack.push(lineStrings[0]);
            typeStack.push(lineStrings[1]);
        }
        while (!tokenStack.isEmpty()){
            inputTokens.add(new Pair<>(tokenStack.pop(),typeStack.pop()));
        }
    }

    public ArrayList<String>  getTokens(){
        ArrayList<String> tokens = new ArrayList<>();
        for (Pair<String,String> pair : inputTokens ){
            if (pair.getValue().equals("punctuation")||pair.getValue().equals("keyword")||pair.getValue().equals("assign")){
                tokens.add(pair.getKey());
            }
            else{
                tokens.add(pair.getValue());
            }
        }
        return tokens;
    }

    public static void main (String[] Args){
        TokensGetter tokensGetter = new TokensGetter("TestProgram.txt");
        System.out.println(tokensGetter.getTokens());
    }
}