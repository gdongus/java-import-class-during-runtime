

# README #

## 1. Compile

javac  -Xlint:unchecked src/Test/TestClass.java

javac  -Xlint:unchecked src/MainClass.java

## 2. Create jar from class you want to load

jar -cf my2.jar src/Test/TestClass.class

## 3. Load jar with class and executes method with arguments

cd src/

java MainClass src/my2.jar Test.TestClass foo
