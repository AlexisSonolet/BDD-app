all:
	javac -d bin -classpath bin/ojdbc6.jar -sourcepath src src/javaApp.java

clean:
	rm -rf bin/*.class
