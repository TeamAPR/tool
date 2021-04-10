#!/bin/bash

mkdir -p CARDUMEN
mkdir -p jGenProg
mkdir -p ARJA

#compile astor
cd astor
jenv local 1.8
#mvn dependency:build-classpath -B | egrep -v "(^\[INFO\]|^\[WARNING\])" | tee /tmp/astor-classpath.txt


mkdir -p bin
javac -cp $(cat /tmp/astor-classpath.txt): -d bin $(find src -name '*.java')

cd target/classes

cp astor.properties ../../bin/astor.properties

cd ../..

cd ..
#compile ARJA
cd ARJA
jenv local 1.7
mkdir -p bin
javac -cp "lib/*": -d bin $(find src -name '*.java')
cd ..

#run actual code
jenv local 1.8
javac -cp "lib/*": -d bin $(find src -name '*.java')

bugDataPath=$1
bugID=$2
defects4jHome=$3

java -Xmx1g -cp "lib/*":bin edu.lu.uni.serval.tbar.main.Main $bugDataPath $bugID $defects4jHome