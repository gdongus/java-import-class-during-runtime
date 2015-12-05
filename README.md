

# README #

## 1. Compile
javac  -Xlint:unchecked src/Test/TestClass.java
javac  -Xlint:unchecked src/MainClass.java

## Create jar from class you want to load
jar -cf my2.jar src/Test/TestClass.class

## invoke main method of class that loads jar, class and executes method with arguments: jarfile, class, methodname
cd src/
java MainClass src/my2.jar Test.TestClass foo
