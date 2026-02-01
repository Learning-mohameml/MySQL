


all : run

compile :
	javac -d ./bin/ -cp ./lib/ojdbc6.jar -sourcepath  ./src/ ./src/Main.java

run : compile 
	java -cp ./bin:./lib/ojdbc6.jar Main


clean :
	rm -r ./bin/*