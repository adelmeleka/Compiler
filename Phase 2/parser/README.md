# parser
Compiler phase 2

Projects steps:

1- reading grammar file and specify if the tokens are terminals or non terminls 
              ->Implementation(✔)     Testing (✔)<-

2-Compute first of each terminal 
              ->Implementation(✔)     Testing (✔)<-

3- compute follow of each terminal
              ->Implementation(✔)     Testing (✔)<-

4-check if grammar is LL(1) or not, in case its not LL(1) an error message should appears and the program ends 
              ->Implementation(✔)     Testing (✔)<-

5-start constructing the parsing table
              ->Implementation(❌)     Testing (❌)<-

6-Deal with panic mode error recovery in left mmost derivation for certain test I/P 
              ->Implementation(❌)     Testing (❌)<-

7-allow predictive parser (recursive descent parser) 
              ->Implementation(❌)     Testing (❌)<-

Bonus:

8-apply left factoring and left recursion on the grammar before parsing step


Notes:
-you will find attached 3 test grammars,
first 2 for easy testing so you can solve it manually to check the output is true in certain step,
that's for the first and second files,
the third file is the required grammar in the PDF.

Choose the program you wanna test by just changing the number in the file name found in the static variable in Test class by (1,2,3)


-All what you need to work on any of the above steps of the project are
the (terminals and non terminal) arraylists,
in addition to the cfg
hashmap which contains all the grammar.

**** VI: we will need the lexical analyzer output to test on, also its noticed in the pdf to make both 2 phases workk togther at the end
