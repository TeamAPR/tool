# tool

cd astor
jenv local 1.8

mvn dependency:build-classpath -B | egrep -v "(^\[INFO\]|^\[WARNING\])" | tee /tmp/astor-classpath.txt
 cat /tmp/astor-classpath.txt

 java -cp $(cat /tmp/astor-classpath.txt):target/classes fr.inria.main.evolution.AstorMain -mode jgenprog -srcjavafolder /src/java/ -srctestfolder /src/test/  -binjavafolder /target/classes/ -bintestfolder  /target/test-classes/ -location /home/user/astor/examples/Math-issue-280/ -dependencies examples/Math-issue-280/lib

