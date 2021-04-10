package us.msu.cse.repair;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
	public PatchJSONStandarOutput(String mode){
		this.mode = mode;
	}

	@SuppressWarnings("unchecked")
	public Object produceOutput(String patchOutput,int NumOfEdits,String fileLocation) {
		
		System.out.println("============");
		System.out.println(patchOutput);
		System.out.println(NumOfEdits);
		System.out.println(fileLocation);
		System.out.println("============");

		JSONObject statsjsonRoot = new JSONObject();
		JSONArray patchlistJson = new JSONArray();
		statsjsonRoot.put("patches", patchlistJson);
		JSONObject generalStatsjson = new JSONObject();
		statsjsonRoot.put("general", generalStatsjson);
		JSONObject patchjson = new JSONObject();
		patchlistJson.add(patchjson);
		patchjson.put("NumOfMut", JSONObject.escape(String.valueOf(NumOfEdits)) );
		patchjson.put("PatchDiff", JSONObject.escape(patchOutput) );
		patchjson.put("Tool", JSONObject.escape(mode));

		String absoluteFileName = fileLocation +"/arja_output.json";

		absoluteFileName = fileLocation + "/../../"+mode+"/output.json";
		try (FileWriter file = new FileWriter(absoluteFileName)) {

			file.write(statsjsonRoot.toJSONString());
			file.flush();
			System.out.println("Storing ing JSON at " + absoluteFileName);
			System.out.println(absoluteFileName + ":\n" + statsjsonRoot.toJSONString());

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Problem storing ing json file" + e.toString());
		}

		return statsjsonRoot;
	}

}
