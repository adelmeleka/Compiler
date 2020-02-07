compile_lex_yacc:
	flex scanner.l
	bison -y -d syntax.y
	g++ -std=c++11 lex.yy.c y.tab.c

compile_and_run: compile_lex_yacc
	./a.out tests/test7
	java -jar ./jasmin-1.1/jasmin.jar output.j
	java test

run_only:
	./a.out tests/test7
	java -jar ./jasmin-1.1/jasmin.jar output.j
	java test
