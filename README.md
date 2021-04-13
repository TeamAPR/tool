# GYMPaG

An APR tool to generate patches , rank them and classify them


I. Pre requisites
--------------
 - JEnv
 - Defects4J
 - Java 1.7
 - Java 1.8
 - Maven

II. Run the tool
---------------------------
 1. Download and Install All prerequisites and link jenv with java 1.7 and 1.8.
 2. Checkout and compile the Defects4J bugs and pick up the location of the files
 3. Make sure you replace the path files for the bug you want to check in the ./astor/cardumenRun.sh,./astor/astorRun.sh, ./astor/runFaultLocalization.sh and ./arja/run_arja.sh
 4. Run the following commands in the root dir
    ````````````
    cd astor
    jenv local 1.8
    mvn dependency:build-classpath -B | egrep -v "(^\[INFO\]|^\[WARNING\])" | tee /tmp/astor-classpath.txt
    cd..
    ````````````

  5.Run the following command to run the tool (if you're using a set of defects4J bugs then provide start and end number
    ````````````
    bash ./toolRun.sh 2 5
    ````````````
 
 
 III. Structure of the Directories
 -------------------------------
 ```powershell
  |--- README.md               :  user guidance
  |--- D4J                     :  Defects4J information
  |--- FailedTestCases         :  Failed test cases of each Defects4J bug
  |--- lib                     :  GZoltar jar files
  |--- Chart_*                 :  Generated patches
  |------ fault_localization          :  
  |------ PerfectFL            :  
  |------ NormalFL             :  
  |--- src                     :  source code
  |--- ranking :  
  |--- astor                  :  
  |--- arja                  :  
```

