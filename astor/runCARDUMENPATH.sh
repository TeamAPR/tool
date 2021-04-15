#!/bin/bash

bugID=$1

work_dir=$2
build_dir_location=$3
test_dir_location=$4
dep_dir=$5
src_dir_location=$6
bug_name=$7
build_test_dir_location=$8


    
echo "$work_dir \n"$dep_dir
jenv local 1.8
echo "$build_test_dir_location "
    
java  -cp $(cat /tmp/astor-classpath.txt):bin fr.inria.main.evolution.AstorMain \
    -mode cardumen \
    -bugName $bug_name \
    -srcjavafolder $src_dir_location \
    -srctestfolder $test_dir_location \
    -binjavafolder $build_dir_location \
    -bintestfolder $build_test_dir_location \
    -location $work_dir \
    -dependencies $dep_dir > "$bug_name"_card_output.txt
