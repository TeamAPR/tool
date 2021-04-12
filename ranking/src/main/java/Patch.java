public class Patch {
    public int NumOfMut;
    public int NumOfInsertMutations;
    public int NumOfDeleteMutations;
    public int NumOfReplaceMutations;
    public String PatchDiff;
    public String Tool;

    Patch(String NumOfMut,  String NumOfInsertMutations, String NumOfDeleteMutations, String NumOfReplaceMutations, String PatchDiff, String Tool){
    	this.NumOfMut = Integer.parseInt(NumOfMut);
    	this.NumOfInsertMutations = Integer.parseInt(NumOfInsertMutations);
    	this.NumOfDeleteMutations = Integer.parseInt(NumOfDeleteMutations);
    	this.NumOfReplaceMutations = Integer.parseInt(NumOfReplaceMutations);
    	this.PatchDiff = PatchDiff;
    	this.Tool = Tool;
    }
    
    /** For if we decide to use private fields */
//    public int getNumOfMut() {
//    	return this.NumOfMut;
//    }
//    
//    public int getNumOfInsertMutations() {
//    	return this.NumOfInsertMutations;
//    }
//    
//    public int getNumOfDeleteMutations() {
//    	return this.NumOfDeleteMutations;
//    }
//    
//    public int getNumOfReplaceMutations() {
//    	return this.NumOfReplaceMutations;
//    }
//    
//    public String getPatchDiff() {
//    	return this.PatchDiff;
//    }
//    
//    public String getTool() {
//    	return this.Tool;
//    }

}