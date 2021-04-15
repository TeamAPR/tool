import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.List;
import java.util.Map;

public class Distance {


    private final static String INCORRECT = "plausible";
    private final static String CORRECT = "correct";


    private static String readJSONFile(String fileName) throws IOException {

        StringBuffer buffer = new StringBuffer();

        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));

        String strLine;

        while ((strLine=reader.readLine())!=null){
            buffer.append(strLine);
        }

        reader.close();

        return buffer.toString();

    }



    private static Integer getLCS(String firstStr, String secondStr){

        int max = 0;
        int count = 0;
        int innerIndex = 0;
        int externalIndex = 0;
        for(int i=0; i<firstStr.length(); ++i){
            externalIndex = i;
            for(int j=0; j<secondStr.length(); ++j){
                count=0;
                innerIndex = j;
                while (externalIndex<firstStr.length() && innerIndex<secondStr.length()
                        && firstStr.charAt(externalIndex) == secondStr.charAt(innerIndex)){
                    innerIndex++;
                    externalIndex++;
                    count++;
                }
                if(count>max){
                    max = count;
                }
            }
        }

        return max;
    }


    private static double normalize(String firstStr, String secondStr){
        int lcs = getLCS(firstStr, secondStr);

        int maxLCS = firstStr.length()>secondStr.length()?secondStr.length():firstStr.length();

        return 1-lcs/maxLCS;
    }


    private static void writeFile(String fileName, String outputData) throws IOException{

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(outputData);
        writer.flush();
        writer.close();
    }

    public static String toPrettyFormat(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }



    private static void calculateDistance(String inputFileName, String outputFileName) throws IOException{
        /**
         * read data
         */
        Gson gson = new Gson();
        String jsonStr = readJSONFile(inputFileName);

        Map<String, List<InputJson>> patches = gson.fromJson(jsonStr, new TypeToken<Map<String, List<InputJson>>>() {}.getType());

        List<InputJson> inputList = patches.get("patches");


        /**
         * calculate distance
         */
        String diff, firstStr, secondStr;

        int plusIndex, minusIndex;

        double normal;

        for(int i=0; i<inputList.size(); ++i){
            plusIndex = -1;
            minusIndex = -1;
            diff = inputList.get(i).getPatchDiff();

            for(int j=0; j<diff.length(); ++j){
                if(diff.charAt(j) == '+' && plusIndex == -1){
                    plusIndex = j;
                }
                if(diff.charAt(j) == '-' && plusIndex != -1 && minusIndex == -1){
                    minusIndex = j;
                }
            }

            if(plusIndex != -1 && minusIndex != -1){
                firstStr = diff.substring(plusIndex+1, minusIndex);
                secondStr = diff.substring(minusIndex+1);

                normal = normalize(firstStr, secondStr);

                if(normal>=0.25){
                    System.out.println(INCORRECT);
                    inputList.get(i).setPatchCorrectness(INCORRECT);
                }else {
                    System.out.println(CORRECT);
                    inputList.get(i).setPatchCorrectness(CORRECT);
                }

            }else {
                System.out.println("format error");
            }


        }

        String outputStr = gson.toJson(patches);

        String prettyJson = toPrettyFormat(outputStr);

        writeFile(outputFileName, prettyJson);


    }


    public static void main(String[] args) throws IOException{

        String inputFileName = ".//src//main//java//Chart_1_ranked.json";
        //String inputFileName2 = ".//src//main//java//7025632976040690657chart_1_ranked.json";
        String outputFileName = "src\\main\\java\\output1.json";
        //String outputFileName2 = "src\\main\\java\\output2.json";

        calculateDistance(inputFileName, outputFileName);
        //calculateDistance(inputFileName2, outputFileName2);

    }



}
