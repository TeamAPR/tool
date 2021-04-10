#!/bin/bash

bugID=$1

work_dir="/Users/georgecherian/Desktop/TSE/bugs/chart/chart_$bugID"
build_dir=$work_dir"/build"
test_dir=$work_dir"/build-tests"
dep_dir=$work_dir"/lib:/Users/georgecherian/.m2/repository/junit/junit/4.11/junit-4.11.jar"
    
echo "$work_dir \n"$dep_dir
jenv local 1.8
    
java  -cp $(cat /tmp/astor-classpath.txt):bin fr.inria.main.evolution.AstorMain \
    -mode cardumen \
    -bugName Chart_"$bugID" \
    -srcjavafolder /source/ \
    -srctestfolder /tests/ \
    -binjavafolder /build/ \
    -bintestfolder /build-tests/ \
    -location /Users/georgecherian/Desktop/TSE/bugs/chart/chart_"$bugID" \
    -dependencies /Users/georgecherian/Desktop/TSE/bugs/chart/chart_"$bugID"/lib:/Users/georgecherian/.m2/repository/junit/junit/4.11/junit-4.11.jar:/Users/georgecherian/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar > "$bugID"_card_output.txt
