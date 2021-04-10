package edu.lu.uni.serval.tbar.main;

import java.io.File;

import edu.lu.uni.serval.tbar.utils.*;

import java.util.Arrays;
import java.util.List;

/**
 * Fix bugs with Fault Localization results.
 * 
 * @author kui.liu
 *
 */
public class Main {
	
	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Arguments: \n" 
					+ "\t<Bug_Data_Path>: the directory of checking out Defects4J bugs. \n"
					+ "\t<Bug_ID>: bug id of each Defects4J bug, such as Chart_1. \n"
//					+ "\t<Suspicious_Code_Positions_File_Path>: \n"
//					 +"\t<Failed_Test_Cases_File_Path>: \n"
					+ "\t<defects4j_Home>: the directory of defects4j git repository.\n");
			System.exit(0);
		}
		String bugDataPath = args[0];// "../Defects4JData/"
		String bugId = args[1]; // "Chart_1"
		String defects4jHome = args[2]; // "../defects4j/"
		System.out.println(bugId);
		System.out.println("Hello");
		String output="";
		try {
			output = ShellUtils.shellRun(Arrays.asList("cd  astor \n", "git diff"), bugId, 1).trim();
			System.out.println(output);
        } catch (Exception e){
			
		}
		
	}

}
