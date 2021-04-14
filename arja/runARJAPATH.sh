#!/usr/bin/env bash

i=$1
work_dir = $2
build_dir_location = $3
test_dir_location = $4
dep_dir = $5
src_dir_location = $6
bug_name = $7
build_test_dir_location = $8

    echo "hello $i"
    build_dir=$work_dir"$build_dir_location"
    test_dir=$work_dir"$test_dir_location"
    #dep_dir=$work_dir"/lib:/Users/georgecherian/Desktop/junit-4.11.jar"
    work_dir=$work_dir"$src_dir_location"
    echo "$work_dir "

    echo "$build_dir"
    echo "$test_dir"
    echo "$dep_dir"
    jenv local 1.7
    java -cp "lib/*":bin us.msu.cse.repair.Main Arja -DsrcJavaDir $work_dir \
                                                 -DbinJavaDir $build_dir \
                                                 -DbinTestDir $test_dir \
                                                 -Ddependences $dep_dir \
                                                 -DBugName $bug_name > $bug_name"test_output.txt"

