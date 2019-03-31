JFLAGS = -g
JC = javac

default: Interface.class

Interface.class: Interface.java
	$(JC) $(JFLAGS) Interface.java

clean:
	rm -rf *.class *.txt
