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
		Thread thread1 = new Thread(() -> {
			int cardumenTotal = 0;
			if(cardumenTotal <=26){
				System.out.println("Cardumen :"+bugId);
				cardumenTotal++;
				try {
					ShellUtils s= new ShellUtils();
					String output = s.shellRun(Arrays.asList("cd  astor \n", "bash ./cardumenRun.sh "+bugId), "Cardumen"+bugId, 1).trim();
					System.out.println(output);
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		});
		Thread thread2 = new Thread(() -> {
			int jGenProgTotal = 0;
			if(jGenProgTotal <=26){
				System.out.println("jGenProgTotal :"+bugId);
				jGenProgTotal++;
				try {
					ShellUtils s= new ShellUtils();
					String output = s.shellRun(Arrays.asList("cd  astor \n", "bash ./astorRun.sh "+bugId), "jGenProgTotal"+bugId, 1).trim();
					System.out.println(output);
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		});
		Thread thread3 = new Thread(() -> {
			int ARJATotal = 0;
			if(ARJATotal <=26){
				System.out.println("ARJATotal :"+bugId);
				ARJATotal++;
				try {
					ShellUtils s= new ShellUtils();
					String output = s.shellRun(Arrays.asList("cd  arja \n", "bash ./run_arja.sh "+bugId), "ARJATotal"+bugId, 1).trim();
					System.out.println(output);
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		});
		thread1.start();
		thread2.start();
		thread3.start();
		try{
			thread1.join();
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			thread2.join();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			thread3.join();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
