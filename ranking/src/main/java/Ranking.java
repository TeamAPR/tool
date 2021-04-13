import com.google.gson.Gson;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.File;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Ranking {
	
	public List<Patch> rankedPatches;

	public List<Patch> getAllPatches(String bugName) {
		List<Patch>allPatches = new ArrayList<Patch>();
		
		String filePath = new File("").getAbsolutePath();
		filePath = filePath.substring(0, filePath.length() - 22);
		
		String arjaFilePath = filePath + "/" + bugName + "/ARJA_output/output.json";
		String cardumenFilePath = filePath + "/" + bugName + "/CARDUMEN_output/output.json";
		String jgenprogFilePath = filePath + "/" + bugName + "/jgenprog_output/output.json";

		File a_tempFile = new File(arjaFilePath);
		if (a_tempFile.exists()){
			System.out.println("ARJA patches are available");
			List<Patch> arjaPatches = this.getPatches(arjaFilePath);
			allPatches.addAll(arjaPatches);
		}

		File c_tempFile = new File(cardumenFilePath);
		if (c_tempFile.exists()){
			System.out.println("Cardumen patches are available");
			List<Patch> cardumenPatches = this.getPatches(cardumenFilePath);
			allPatches.addAll(cardumenPatches);
		}

		File j_tempFile = new File(jgenprogFilePath);
		if (j_tempFile.exists()){
			System.out.println("jgenprog patches are available");
			List<Patch> jgenprogPatches = this.getPatches(jgenprogFilePath);
    		allPatches.addAll(jgenprogPatches);
		}
		    	
    	return allPatches;
    	
    }
	
	
    public List<Patch> getPatches(String filepath){
        List<Patch> currPatches = new ArrayList<Patch>();
        Gson gson = new Gson();
        JSONParser parser = new JSONParser();

        try {
	        Reader reader = new FileReader(filepath);
	        JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray rawPatches = (JSONArray) jsonObject.get("patches");

            Iterator<JSONObject> iterator = rawPatches.iterator();
            while (iterator.hasNext()) {
				JSONObject obj = iterator.next();
				String NumOfMut = (String) obj.get("NumOfMut");
				String NumOfInsertMutations = (String)  obj.get("NumOfInsertMutations");
				String NumOfDeleteMutations = (String)  obj.get("NumOfDeleteMutations");
				String NumOfReplaceMutations = (String)  obj.get("NumOfReplaceMutations");
				String PatchDiff = (String) obj.get("PatchDiff");
				String Tool = (String) obj.get("Tool");
				Patch curr = new Patch(NumOfMut, NumOfInsertMutations, NumOfDeleteMutations, NumOfReplaceMutations, PatchDiff, Tool);
                currPatches.add(curr);
            }
	         
        } catch(IOException e) {
        	e.printStackTrace();
        } catch (ParseException e) {
        	e.printStackTrace();
        }
        
        return currPatches;
        
    }
    
    
    public List<Patch> rankPatches(List<Patch> unrankedPatches){
    	
    	List<Patch>ranked = new ArrayList<Patch>();
    	
    	Comparator<Patch> compareNumOfMut = new Comparator<Patch>() {
    	    @Override
    	    public int compare(Patch p1, Patch p2) {
    	        int c = p1.NumOfMut - p2.NumOfMut;
				if (c == 0)
					c = p1.NumOfDeleteMutations - p2.NumOfDeleteMutations;
				if (c == 0)
					c = p1.NumOfInsertMutations - p2.NumOfInsertMutations;
				if (c == 0)
					c = p1.NumOfReplaceMutations - p2.NumOfReplaceMutations;
				return c;
    	    }
    	};
    	
    	Collections.sort(unrankedPatches, compareNumOfMut); 	
    	ranked = unrankedPatches;

		for (int i = 0; i < ranked.size(); i++){
			ranked.get(i).Rank = i + 1;
		}

        return ranked;
        
    }
    
    public void writeToFile(List<Patch> rankedPatches, String bugName) {
    	Gson gson = new Gson();
    	JSONParser parser = new JSONParser();
    	JSONObject obj = new JSONObject();
    	JSONArray list = new JSONArray();
    	
    	
    	try {
    		for (int i = 0; i < rankedPatches.size(); i++) {
        		String patchString = gson.toJson(rankedPatches.get(i));
        		JSONObject jsonObject = (JSONObject) parser.parse(patchString);
        		list.add(jsonObject);
        	}
        	
        	obj.put("patches", list);    	   		
    		
    		String filePath = new File("").getAbsolutePath();
			filePath = filePath.substring(0, filePath.length() - 22);

    		String writeFilePath = filePath + "/" + bugName + "/" + bugName + "_ranked.json";
    		FileWriter writer = new FileWriter(writeFilePath);
    		writer.write(obj.toJSONString());
    		writer.flush();
    	    writer.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (ParseException e) {
        	e.printStackTrace();
        }
    	
    }
    
    public void doRank(String bugName) {
    	List<Patch> allPatches = getAllPatches(bugName);
    	this.rankedPatches = rankPatches(allPatches);
    	writeToFile(rankedPatches, bugName);
    }


    public static void main(String[] args) {
    	if (args.length != 1) {
    		System.out.println("Missing/Invalid arguments");
			return;
    	}
    	
    	String bugName = args[0];
    	
    	Ranking r = new Ranking();
    	r.doRank(bugName);
    }
}
