#!/bin/bash

javac -cp "lib/*": -d bin $(find src -name '*.java')

bugDataPath=$1
bugID=$2
defects4jHome=$3

java -Xmx1g -cp "lib/*":bin edu.lu.uni.serval.tbar.main.Main $bugDataPath $bugID $defects4jHome