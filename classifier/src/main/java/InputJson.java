public class InputJson {

    private int NumOfMut;
    private int NumOfDeleteMutations;
    private int NumOfInsertMutations;
    private int NumOfReplaceMutations;

    private String PatchDiff;

    private String Tool;

    private String PatchCorrectness;


    public int getNumOfMut() {
        return NumOfMut;
    }

    public void setNumOfMut(int numOfMut) {
        NumOfMut = numOfMut;
    }

    public int getNumOfDeleteMutations() {
        return NumOfDeleteMutations;
    }

    public void setNumOfDeleteMutations(int numOfDeleteMutations) {
        NumOfDeleteMutations = numOfDeleteMutations;
    }

    public int getNumOfInsertMutations() {
        return NumOfInsertMutations;
    }

    public void setNumOfInsertMutations(int numOfInsertMutations) {
        NumOfInsertMutations = numOfInsertMutations;
    }

    public int getNumOfReplaceMutations() {
        return NumOfReplaceMutations;
    }

    public void setNumOfReplaceMutations(int numOfReplaceMutations) {
        NumOfReplaceMutations = numOfReplaceMutations;
    }

    public String getPatchDiff() {
        return PatchDiff;
    }

    public void setPatchDiff(String patchDiff) {
        PatchDiff = patchDiff;
    }

    public String getTool() {
        return Tool;
    }

    public void setTool(String tool) {
        Tool = tool;
    }


    public String getPatchCorrectness() {
        return PatchCorrectness;
    }

    public void setPatchCorrectness(String patchCorrectness) {
        PatchCorrectness = patchCorrectness;
    }
}
