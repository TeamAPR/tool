package us.msu.cse.repair;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import us.msu.cse.repair.core.parser.LCNode;

/**
 * 
 * @author Matias Martinez
 *
 */
public class PatchJSONStandarOutput {
	String mode;
	JSONObject statsjsonRoot;
	JSONArray patchlistJson;
	String bugName;
	public PatchJSONStandarOutput(String mode,String bugName){
		this.mode = mode;
		this.statsjsonRoot = new JSONObject();
		this.patchlistJson = new JSONArray();
		this.bugName = bugName;
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

		System.out.println(patchjson.toJSONString());
		return statsjsonRoot;
	}
	public void finalWrite(String fileLocation){

		String absoluteFileName = "/arja_output.json";
		String interimLocation = "";
		if(fileLocation != null)
			interimLocation = fileLocation;
		System.out.println("Inside prod output");
		absoluteFileName = "./../Result"+interimLocation+"/"+mode+"_output";
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

    public Map<LCNode, Double> readJSONFromFile() 
    {
        //JSON parser object to parse read file
		Map<LCNode, Double> listOfSusp = new HashMap<LCNode, Double>();
        JSONParser jsonParser = new JSONParser();
         
		String absoluteFileName = "./../"+this.bugName+"/fault_localization/";
		System.out.println("===========");
		System.out.println(absoluteFileName);
		
		absoluteFileName = absoluteFileName+"/output.json";

        try (FileReader reader = new FileReader(absoluteFileName))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
			JSONObject employeeObject = (JSONObject) obj ;
            JSONArray employeeList = (JSONArray) employeeObject.get("suspiciousCode");
			for(Object susp: employeeList){
				if ( susp instanceof JSONObject ) {
					JSONObject SuspiciousStatements = (JSONObject) susp;
					LCNode sc = new LCNode((String)SuspiciousStatements.get("className") , new Integer((String)SuspiciousStatements.get("lineNumber")));
					Double suspiciousValue = new Double((String) SuspiciousStatements.get("suspiciousValue"));  
					listOfSusp.put(sc,suspiciousValue);
				}
			}
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return listOfSusp;
    }
}
