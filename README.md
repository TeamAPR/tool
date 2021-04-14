# GYMPaG

An APR tool to generate patches , rank them and classify them
This repo contains the source code as well as the experiment results for running the app on Defects4J Charts bug


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
 3. Only applies if you directly want to run Defects4J bugs for a large set if you want to run for a particular program Skip to Step 4
      Make sure you replace the path files for the bug you want to check in the ./astor/cardumenRun.sh,./astor/astorRun.sh, ./astor/runFaultLocalization.sh and ./arja/run_arja.sh ()
 4. Run the following commands in the root dir
    ````````````
    cd astor
    jenv local 1.8
    mvn dependency:build-classpath -B | egrep -v "(^\[INFO\]|^\[WARNING\])" | tee /tmp/astor-classpath.txt
    cd..
    ````````````
  5. If you are running for Defects 4J Bugs then use this step
    Run the following command to run the tool (if you're using a set of defects4J bugs then provide start and end number)
    ````````````
    bash ./toolRun.sh 2 5
    ````````````
   6. If you are running for a generic program then use this step
    Run the following command to run the tool 
    ````````````
    bash ./runTOOLPATH.sh $bugName $bugID $work_dir $build_dir_location $test_dir_location $dep_dir $src_dir_location $build_test_dir_location
    ````````````
     ```powershell
      |--- bugName                        :  Name of the bug Can be anything e.g. Chart1
      |--- bugID                          :  an Id associated with the bug can be anything e.g. 1
      |--- work_dir                       :  The location of all the directories for the bug e.g. "/Users/george/Desktop/TSE/Chart_1"
      |--- build_dir_location             :  The location of the class files for the program e.g. "build/"
      |--- test_dir_location              :  The location of the test cases for the program e.g. "tests/"
      |--- src_dir_location               :  The location of the source code for the program e.g.  "source/"
      |--- dep_dir                        :  The location of the dpeendencies for the program e.g. "lib/"
      |--- build_test_dir_location        :  The location of the dpeendencies for the program e.g. "build-tests/"
    ```

 
 III. Structure of the Directories
 -------------------------------
 ```powershell
  |--- README.md                      :  Readme for help
  |--- toolRun.sh                     :  Shell script to run the code
  |--- lib                            :  JAR files needed
  |--- Results                        :  The final results of the runs made by the system
  |--- Project Assignments            :  Assignments for TSE
  |--- src                            :  Source code for orchestrator
  |--- ranking                        :  Source code for ranking
  |--- astor                          :  Modified Source code for Cardumen, jGenProg and Fault localization
  |--- arja                           :  Modified Source code for ARJA
```


This is the folder structure for results
 ```powershell
  |--- Chart_xx                       :  Generated patches for specific bugs
  |------ fault_localization          :  fault Localization output
  |------ ARJA_output                 :  ARJA output
  |------ CARDUMEN_output             :  Cardumen output
  |------ jGenProg_output             :  jGenProg output
  |------ Chart_xx_ranked.json        :  Ranking of output from the three tools
```