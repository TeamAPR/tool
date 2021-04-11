package us.msu.cse.repair;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.io.File;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * 
 * @author Matias Martinez
 *
 */
public class PatchJSONStandarOutput {
	String mode;
	JSONObject statsjsonRoot;
	JSONArray patchlistJson;
	public PatchJSONStandarOutput(String mode){
		this.mode = mode;
		this.statsjsonRoot = new JSONObject();
		this.patchlistJson = new JSONArray();
		this.statsjsonRoot.put("patches", this.patchlistJson);
	}

	@SuppressWarnings("unchecked")
	public Object produceOutput(String patchOutput,int NumOfEdits,String fileLocation,int numOfInsert,int numOfReplace,int numOfDelete) {
		
		System.out.println("Inside prod output");
		System.out.println("============");
		System.out.println(patchOutput);
		System.out.println(NumOfEdits);
		System.out.println(fileLocation);
		System.out.println("============");

		JSONObject patchjson = new JSONObject();
		patchlistJson.add(patchjson);
		patchjson.put("NumOfMut", JSONObject.escape(String.valueOf(NumOfEdits)) );
		patchjson.put("PatchDiff", JSONObject.escape(patchOutput) );
		patchjson.put("Tool", JSONObject.escape(mode));

		patchjson.put("NumOfInsertMutations", JSONObject.escape(String.valueOf(numOfInsert)) );
		patchjson.put("NumOfDeleteMutations", JSONObject.escape(String.valueOf(numOfReplace)) );
		patchjson.put("NumOfReplaceMutations", JSONObject.escape(String.valueOf(numOfDelete)) );

		return statsjsonRoot;
	}
	public void finalWrite(String fileLocation){

		String absoluteFileName = "/arja_output.json";
		String interimLocation = "";
		if(fileLocation != null)
			interimLocation = fileLocation;
		System.out.println("Inside prod output");
		absoluteFileName = "./../"+interimLocation+"/"+mode+"_output";
		System.out.println("===========");
		System.out.println(absoluteFileName);
		
		try{
			File filejson = new File(absoluteFileName);		
			filejson.mkdirs();
			filejson.createNewFile();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		absoluteFileName = absoluteFileName+"/output.json";

		try (FileWriter file = new FileWriter(absoluteFileName)) {

			file.write(statsjsonRoot.toJSONString());
			file.flush();
			System.out.println("Storing JSON at " + absoluteFileName);
			System.out.println(absoluteFileName + ":\n" + statsjsonRoot.toJSONString());

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Problem storing ing json file" + e.toString());
		}

	}

}
