import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.Scanner;
import java.io.FileReader;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.*;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;
//ICsvBeanReader;

//import java.io.BufferedReader;
//import supercsv.*;



public class main {
	
	static HashSet<myDataClass> campaigns;
	
	//Entire data set
	static int numElements = 0;
	static double meanPledged = 0;
	static double meanGoal = 0;
	static double meanDuration = 0;
	
	//Only Successful campaigns
	static int numElementsSuccessful = 0;
	static double meanPledgedSuccessful = 0;
	static double meanGoalSuccessful = 0;
	static double meanDurationSuccessful = 0;
	
	//Only Failed campaigns
	static int numElementsFailed = 0;
	static double meanPledgedFailed = 0;
	static double meanGoalFailed = 0;
	static double meanDurationFailed = 0;

	//Only ended campaigns
	static int numElementsEnded = 0;
	static double meanPledgedEnded = 0;
	static double meanGoalEnded = 0;
	static double meanDurationEnded = 0;

	static HashMap<Integer, Integer> histogram;
	
	public static void main(String[] args) {

		initHash();
		
		try {
			
			readData(new File(args[0]));
			
		} catch (Exception e) {
			System.out.println("Main catch. " + e.toString());
			e.printStackTrace();
		}

		
		printAnalytics();
		
		//System.out.println("End main file");

	}
	
	//sets up 20 buckets of size 20i per bucket, along with an 21st bucket for spillage
	static void initHash() {	
		
		histogram = new HashMap<Integer, Integer>();
		int q = 0;
		for(int i = 0; i < 20;i++) {
			q+=20;
			histogram.put(q,0);
			
		}
		
		histogram.put(401,0);
		
	}
	
	static void printAnalytics() {
		
		System.out.println("\nStart Analytics\n");
		
		//Number of campaigns
		System.out.println("Number of campaigns: " + numElements);
		System.out.println("Number of successful campaigns: " + numElementsSuccessful);
		System.out.println("Number of failed campaigns: " + numElementsFailed);
		System.out.println("Number of ended campaigns: " + numElementsEnded);
		System.out.println("Number of live campaigns: " + (numElements - numElementsEnded));
		
		//Means of campaigns
		System.out.println("");
		System.out.println("All Campaigns - Mean Pledged: " + (meanPledged/numElements));
		System.out.println("All Campaigns - Mean Goal: " + (meanGoal/numElements));
		System.out.println("All Campaigns - Mean Duration (Days): " + (meanDuration/numElements));
		
		//Means of successful campaigns
		System.out.println("");
		System.out.println("Successful Campaigns - Mean Pledged: " + (meanPledgedSuccessful/numElementsSuccessful));
		System.out.println("Successful Campaigns - Mean Goal: " + (meanGoalSuccessful/numElementsSuccessful));
		System.out.println("Successful Campaigns - Mean Duration (Days): " + (meanDurationSuccessful/numElementsSuccessful));
		
		//Means of failed campaigns
		System.out.println("");
		System.out.println("Failed Campaigns - Mean Pledged: " + (meanPledgedFailed/numElementsFailed));
		System.out.println("Failed Campaigns - Mean Goal: " + (meanGoalFailed/numElementsFailed));
		System.out.println("Failed Campaigns - Mean Duration (Days): " + (meanDurationFailed/numElementsFailed));
		
		//Means of ended campaigns
		System.out.println("");
		System.out.println("Ended Campaigns - Mean Pledged: " + (meanPledgedEnded/numElementsEnded));
		System.out.println("Ended Campaigns - Mean Goal: " + (meanGoalEnded/numElementsEnded));
		System.out.println("Ended Campaigns - Mean Duration (Days): " + (meanDurationEnded/numElementsEnded));
		
		System.out.println("\nEnd Analytics\n");
	}
	
	
	
	static void readData(File file) throws Exception{
		
		//Scanner reading in file
		/*Scanner scanner = new Scanner(file);
		scanner.useDelimiter(",");
		while(scanner.hasNext()) {
			String data = scanner.next();
			System.out.println(data);
		}
		
		scanner.close();*/
		
		
		//FileReader reading in line by line for future regular expression breakdown
		/*FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line;
		
		while((line = bufferedReader.readLine())!=null) {
			System.out.println(line);
		}
		bufferedReader.close();
		fileReader.close();*/
		
		 
		//initiate the campaigns variable with all of the data from the CSV
		 
		 ICsvBeanReader beanReader = null;
		 campaigns = new HashSet<myDataClass>();
		 
		 
		 try {
                beanReader = new CsvBeanReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);
                
                // the header elements are used to map the values to the bean (names must match)
                final String[] header = beanReader.getHeader(true);
                final CellProcessor[] processors = getProcessors();
                
                myDataClass campaign;
                while( (campaign = beanReader.read(myDataClass.class, header, processors)) != null ) {
                        System.out.println(String.format("lineNo=%s, rowNo=%s, campaign=%s", beanReader.getLineNumber(),
                                beanReader.getRowNumber(), campaign));
                        //campaigns.add(campaign);
                        
                        //MEAN
                        numElements++;
                        meanPledged += campaign.getPledged();
                        meanGoal += campaign.getGoal();
                        meanDuration += campaign.getDuration();
                        
                        if(campaign.getStatus().equals("successful")) {
                        	numElementsSuccessful++;
                        	meanPledgedSuccessful += campaign.getPledged();
                            meanGoalSuccessful += campaign.getGoal();
                            meanDurationSuccessful += campaign.getDuration();
                        } else if(campaign.getStatus().equals("failed")) {
                        	numElementsFailed++;
                        	meanPledgedFailed += campaign.getPledged();
                            meanGoalFailed += campaign.getGoal();
                            meanDurationFailed += campaign.getDuration();
                        }
                        
                        if(!campaign.getStatus().equals("live")) {
                        	numElementsEnded++;
                        	meanPledgedEnded += campaign.getPledged();
                        	meanGoalEnded += campaign.getGoal();
                        	meanDurationEnded += campaign.getDuration();
                        }
                        
                        
                        
                        
                }
                
        } catch (Exception e) {
        	System.out.println("ReadData Catch. " + e.toString());
			e.printStackTrace();
        }
        finally {
                if( beanReader != null ) {
                        beanReader.close();
                }
        }
	}
	
	
	
	private static CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { 
                new UniqueHashCode(), //Project ID 
                new NotNull(), //name
                new NotNull(), //url
                new NotNull(), //category
                new NotNull(), //sub category
                new NotNull(), //location
                new NotNull(), //status
                new ParseInt(), //goal
                new ParseInt(), //pledged
                new ParseDouble(), //funded Percentage
                new ParseInt(), //backers
                new ParseDate("E, dd MMM yyyy HH:mm:ss Z"), // funded Date
                new ParseInt(), //num levels
                new NotNull(), //reward levels, un-parsed
                new ParseInt(), //updates
                new ParseInt(), //comments
                new ParseDouble() //duration in days
		};
        
        return processors;

	}

}
