#PatchClassifier 
 
The classifier is used to calculate the distance between two sequences divided in the specified format in the diff item in a given JSON file

- The InputJson class corresponds to the entities in the JSON file
- The Distance.java is the main class of the execution
- Run the tool 
	- copy the json file that needs to be processed to the src/main/java folder of the project
	- modify the variable in the main method of the Distance class to specify the file name that needs to be processed and the file name that needs to be output
	- pass two file names into the calculateDistance method and execute