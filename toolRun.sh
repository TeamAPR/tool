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
    cd external
    jenv local 1.7
    rm -r bin
    mkdir bin
    javac -cp "lib/*": -d bin $(find src -name '*.java')
    cd ..
cd ..

#run actual code
jenv local 1.8
javac -cp "lib/*": -d bin $(find src -name '*.java')

bugID=$1
bugTill=$2
defects4jHome="abc"

while [ $bugID -le $bugTill ]
do
    now="$(date)"
	echo "Welcome $bugID times. $now"
    java -Xmx1g -cp "lib/*":bin edu.lu.uni.serval.tbar.main.Main $bugTill $bugID $defects4jHome

    now="$(date)"
	echo "Welcome $bugID times. $now"

	bugID=$bugID+1	 # increments $n
done
