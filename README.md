# tool

Pre requisites
Have Jenv installed
install java 1.7 and 1.8 using jenv
Install maven

Run the following commands first
````````````
cd astor
jenv local 1.8
mvn dependency:build-classpath -B | egrep -v "(^\[INFO\]|^\[WARNING\])" | tee /tmp/astor-classpath.txt
cd..


````````````

Run the following command
````````````
bash ./toolRun.sh abc abc abc
````````````