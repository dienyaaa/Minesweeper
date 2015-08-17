JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		Minesweeper.java \
		Gamewindow.java \
		ControlPanel.java \
		TimerPanel.java \
		BoardPanel.java \
		GameData.java \
		Utils.java \
		Cell.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class