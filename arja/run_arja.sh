#!/usr/bin/env bash

i=$1

    echo "hello $i"
    work_dir="/Users/georgecherian/Desktop/TSE/bugs/chart/chart_$i"
    build_dir=$work_dir"/build"
    test_dir=$work_dir"/build-tests"
    dep_dir=$work_dir"/lib:/Users/georgecherian/Desktop/junit-4.11.jar"
    work_dir=$work_dir"/source"
    echo "$work_dir "

    echo "$build_dir"
    echo "$test_dir"
    echo "$dep_dir"
    jenv local 1.7
    java -cp "lib/*":bin us.msu.cse.repair.Main Arja -DsrcJavaDir $work_dir \
                                                 -DbinJavaDir $build_dir \
                                                 -DbinTestDir $test_dir \
                                                 -Ddependences $dep_dir \
                                                 -DBugName Chart_"$i" > $i"test_output.txt"

