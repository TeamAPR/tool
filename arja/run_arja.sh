#!/usr/bin/env bash
################################################################################
#
# This script tests the tutorial as described in Defects4J's README file.
#
################################################################################
# Import helper subroutines and variables, and init Defects4J

echo "hi"
for i in {1..26}
do
    echo "hello $i"
    work_dir="/Users/georgecherian/Desktop/TSE/bugs/chart/chart_$i"
    work_dir=$work_dir"_buggy"
    build_dir=$work_dir"/build"
    test_dir=$work_dir"/build-tests"
    dep_dir=$work_dir"/lib:/Users/georgecherian/.m2/repository/junit/junit/4.11/junit-4.11.jar"
    
    echo "$work_dir \n"$dep_dir
    jenv local 1.8
    java -cp "lib/*":bin us.msu.cse.repair.Main Arja -DsrcJavaDir $work_dir \
                                                 -DbinJavaDir $build_dir \
                                                 -DbinTestDir $test_dir \
                                                 -Ddependences $dep_dir > $i"_output.txt"
done
