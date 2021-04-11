package fr.inria.astor.core.output;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fr.inria.astor.core.setup.ConfigurationProperties;
import fr.inria.astor.core.stats.PatchHunkStats;
import fr.inria.astor.core.stats.PatchStat;
import fr.inria.astor.core.stats.PatchStat.HunkStatEnum;
import fr.inria.astor.core.stats.PatchStat.PatchStatEnum;
import fr.inria.astor.core.stats.Stats;
import fr.inria.astor.core.stats.Stats.GeneralStatEnum;
import fr.inria.main.AstorOutputStatus;
import fr.inria.main.ExecutionMode;

/**
 * 
 * @author Matias Martinez
 *
 */
public class PatchJSONStandarOutput implements ReportResults {
	String mode;
	String bugName;
	public PatchJSONStandarOutput(String mode,String bugName){
		this.mode = mode;
		this.bugName = bugName;
	}
	private static Logger log = Logger.getLogger(Stats.class.getName());

	@SuppressWarnings("unchecked")
	public Object produceOutput(List<PatchStat> statsForPatches, Map<GeneralStatEnum, Object> generalStats,
			String output) {

		JSONObject statsjsonRoot = new JSONObject();
		JSONArray patchlistJson = new JSONArray();
		statsjsonRoot.put("patches", patchlistJson);
		JSONObject generalStatsjson = new JSONObject();
		statsjsonRoot.put("general", generalStatsjson);
		JSONParser parser = new JSONParser();
		for (GeneralStatEnum generalStat : GeneralStatEnum.values()) {
			Object vStat = generalStats.get(generalStat);
			if (vStat == null)
				generalStatsjson.put(generalStat.name(), null);
			else {
				try {
					Object value = null;
					if (vStat instanceof AstorOutputStatus || vStat instanceof String)
						value = parser.parse("\"" + vStat + "\"");
					else
						value = parser.parse(vStat.toString());
					generalStatsjson.put(generalStat.name(), value);
				} catch (ParseException e) {
					log.error(e);
				}
			}

		}

		for (PatchStat patchStat : statsForPatches) {

			JSONObject patchjson = new JSONObject();
			patchlistJson.add(patchjson);
			Map<PatchStatEnum, Object> stats = patchStat.getStats();
			for (PatchStatEnum statKey : PatchStatEnum.values()) {
				int modificationCount = 0;
				int insertedCount = 0;
				int replacedCount = 0;
				int deletedCount = 0;
				if (statKey.equals(PatchStatEnum.HUNKS)) {
					List<PatchHunkStats> hunks = (List<PatchHunkStats>) stats.get(statKey);
					JSONArray hunksListJson = new JSONArray();
					patchjson.put("patchhunks", hunksListJson);

					for (PatchHunkStats patchHunkStats : hunks) {
						modificationCount++;
						Map<HunkStatEnum, Object> statshunk = patchHunkStats.getStats();

						JSONObject hunkjson = new JSONObject();
						hunksListJson.add(hunkjson);
						for (HunkStatEnum hs : HunkStatEnum.values()) {
							if (statshunk.containsKey(hs)){
								if(hs.name()=="OPERATOR"){
									if(statshunk.get(hs).toString().toLowerCase().contains("insert")){
										insertedCount++;
									}else if (statshunk.get(hs).toString().toLowerCase().contains("delete")){
										deletedCount++;
									}else{
										replacedCount++;
									}
								}
								hunkjson.put(hs.name(), JSONObject.escape(statshunk.get(hs).toString()));
							}
						}
					}
					patchjson.put("NumOfMut", JSONObject.escape(String.valueOf(modificationCount)) );
					patchjson.put("NumOfInsertMutations", JSONObject.escape(String.valueOf(insertedCount)) );
					patchjson.put("NumOfDeleteMutations", JSONObject.escape(String.valueOf(replacedCount)) );
					patchjson.put("NumOfReplaceMutations", JSONObject.escape(String.valueOf(deletedCount)) );

				} else {
					try {
						if (stats.containsKey(statKey)){
							if(statKey.name()=="PATCH_DIFF_ORIG"){
								patchjson.put("PatchDiff", JSONObject.escape(stats.get(statKey).toString()));
						
							}
							patchjson.put(statKey.name(), JSONObject.escape(stats.get(statKey).toString()));
						}
							
					} catch (Exception e) {
						log.error(e);
						log.error("problems with key " + statKey.name());
					}
				}
						

			}
			System.out.println("===============================");

			patchjson.put("Tool", JSONObject.escape(mode));

		}
		String filename = ConfigurationProperties.getProperty("jsonoutputname");

		String absoluteFileName = output + "/" + filename + ".json";
		try (FileWriter file = new FileWriter(absoluteFileName)) {

			file.write(statsjsonRoot.toJSONString());
			file.flush();
			log.info("Storing ing JSON at " + absoluteFileName);
			log.info(filename + ":\n" + statsjsonRoot.toJSONString());

		} catch (IOException e) {
			e.printStackTrace();
			log.error("Problem storing ing json file" + e.toString());
		}

		absoluteFileName = "./../"+this.bugName+"/"+mode+"_output/";
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
			log.info("Storing ing JSON at " + absoluteFileName);
			log.info(filename + ":\n" + statsjsonRoot.toJSONString());

		} catch (IOException e) {
			e.printStackTrace();
			log.error("Problem storing ing json file" + e.toString());
		}
		return statsjsonRoot;
	}

}
