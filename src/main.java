import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.Scanner;
import java.io.FileReader;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Set;

import org.supercsv.cellprocessor.Optional;
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
	
	//static HashSet<myDataClass> campaigns;
	
	static HashSet<String> projectIDs;
	
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

	static TreeMap<Integer, Integer> histogram; //number of backers
	
	static TreeMap<Double, Integer> mode; //duration
	
	static TreeMap<String, Integer> categoryHistogram;
	static TreeMap<String, Integer> subcategoryHistogram;
	static TreeMap<String, Set<String>> catMapSub; 
	static TreeMap<String, TreeMap<String, Integer>> catHistogram;
	static TreeMap<String, TreeMap<String, Integer>> catHistogramSuccessful;
	static TreeMap<String, TreeMap<String, Integer>> catHistogramFailed;
	
	static double avgTiersSuccessful;
	
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
	
	//sets up 20 buckets of size 20i per bucket, along with an 21st bucket (key=401) for spillage
	static void initHash() {	
		
		histogram = new TreeMap<Integer, Integer>();
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
		
		//Histogram of num backers in all campaigns
		System.out.println("");
		System.out.println("Histogram: Number of Backers");
		System.out.println(histogram);
	
		//Mode & Median of duration
		System.out.println("");
		System.out.println("All Campaigns - Duration Mode:" + returnMode());
		System.out.println("All Campaigns - Duration Median: " + returnMedian());
		
		/*//successful categories, subcategories, frequency
		System.out.println("");
		System.out.println("-------------------------------Successful Campaigns-------------------------------");
		printCategories(catHistogramSuccessful);
		
		System.out.println("");
		System.out.println("-------------------------------Failed Campaigns-------------------------------");
		printCategories(catHistogramFailed);*/
		
		//successful/failed ratios
		System.out.println("");
		printRatios();
		
		//successful/failed ratios
		System.out.println("");
		System.out.println("Average successful reward tiers: " + avgTiersSuccessful/numElementsSuccessful);
		
		System.out.println("\nEnd Analytics\n");
	}
	
	static void printRatios() {
		
		TreeMap<String, Integer> tempS, tempA;
		int countS, countA;
		for(String c : catHistogram.keySet()) {
			
			countS = 0;
			countA = 0;
			System.out.println("Category: " + c);
			tempS = catHistogramSuccessful.get(c);
			tempA = catHistogram.get(c);
			
			for(String s : tempS.keySet()) {
				System.out.println("  Subcategory: " + s + " - " + ((double)tempS.get(s) / (double)tempA.get(s)) + " " + tempS.get(s) + "/" + tempA.get(s));
				countS+=tempS.get(s);
				countA+=tempA.get(s);
			}
			System.out.println("  Success vs Total: " + ((double) countS/ (double)countA) + " " + countS + "/" + countA);
			System.out.println("  Category Total vs All Total: " + ((double) countA/ (double)numElementsEnded) + " " + countA + "/" + numElementsEnded);
			System.out.println("  Category Success vs All Success: " + ((double) countS/ (double)numElementsSuccessful) + " " + countS + "/" + numElementsSuccessful);
			
		}

	}
	
	static void printCategories(TreeMap<String, TreeMap<String, Integer>> catH) {
		
		//catHistogram
		TreeMap<String, Integer> temp;
		int i;
		for(String c : catH.keySet()) {
			
			i = 0;
			System.out.println("Category: " + c);
			temp = catH.get(c);
			
			for(String s : temp.keySet()) {
				System.out.println("  Subcategory: " + s + " - " + temp.get(s));
				i+=temp.get(s);
			}
			System.out.println("  Total: " + i);
			
		}
	}
	
	static Double returnMode() {
		Double result = 0.0;
		int resultFreq = 0;
		
		Set<Double> keySet = mode.keySet();
		
		for(Double d : keySet) {
			if (mode.get(d) > resultFreq) {
				result = d;
				resultFreq = mode.get(d);
			}
		}
		
		return result;
	}
	
	static Double returnMedian() {
		double result = 0.0;
		int numLeft = numElements/2;
		
		for(Double d : mode.keySet()) {
			numLeft -= mode.get(d);
			if(numLeft<=0) {
				result = d;
				break;
			}
		}
		
		return result;
	}
	
	static void readData(File file) throws Exception{
		
		 
		//initiate the campaigns variable with all of the data from the CSV
		 
		 ICsvBeanReader beanReader = null;
		 //campaigns = new HashSet<myDataClass>();
		 mode = new TreeMap<Double, Integer>();
         projectIDs = new HashSet<String>();
         //categoryHistogram = new TreeMap<String, Integer>();
         //subcategoryHistogram = new TreeMap<String, Integer>();
         //catMapSub = new TreeMap<String, Set<String>>();
         catHistogram = new TreeMap<String, TreeMap<String, Integer>>();
         catHistogramSuccessful = new TreeMap<String, TreeMap<String, Integer>>();
         catHistogramFailed = new TreeMap<String, TreeMap<String, Integer>>();
         TreeMap<String, Integer> m;
         avgTiersSuccessful = 0.0;
		 
		 try {
                beanReader = new CsvBeanReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);
                
                // the header elements are used to map the values to the bean (names must match)
                final String[] header = beanReader.getHeader(true);
                final CellProcessor[] processors = getProcessors();
                
                
                myDataClass campaign;
                while( (campaign = beanReader.read(myDataClass.class, header, processors)) != null ) {
                        System.out.println(String.format("lineNo=%s, rowNo=%s, campaign=%s", beanReader.getLineNumber(),
                                beanReader.getRowNumber(), campaign));
                  
                        
                        if(!projectIDs.contains(campaign.getProject_id())) {
	                        projectIDs.add(campaign.getProject_id());
	                        
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
	                            
	                            catHistogramSuccessful.putIfAbsent(campaign.getCategory(), new TreeMap<String, Integer>());
		                        m = catHistogramSuccessful.get(campaign.getCategory());
		                        m.putIfAbsent(campaign.getSubcategory(), 0);
		                        m.put(campaign.getSubcategory(), m.get(campaign.getSubcategory())+1);
		                        catHistogramSuccessful.put(campaign.getCategory(), m);
		                        
		                        avgTiersSuccessful += campaign.getLevels();

	                        } else if(campaign.getStatus().equals("failed")) {
	                        	numElementsFailed++;
	                        	meanPledgedFailed += campaign.getPledged();
	                            meanGoalFailed += campaign.getGoal();
	                            meanDurationFailed += campaign.getDuration();
	                            
	                            catHistogramFailed.putIfAbsent(campaign.getCategory(), new TreeMap<String, Integer>());
		                        m = catHistogramFailed.get(campaign.getCategory());
		                        m.putIfAbsent(campaign.getSubcategory(), 0);
		                        m.put(campaign.getSubcategory(), m.get(campaign.getSubcategory())+1);
		                        catHistogramFailed.put(campaign.getCategory(), m);

	                        }
	                        
	                        if(!campaign.getStatus().equals("live")) {
	                        	numElementsEnded++;
	                        	meanPledgedEnded += campaign.getPledged();
	                        	meanGoalEnded += campaign.getGoal();
	                        	meanDurationEnded += campaign.getDuration();
	                        	
	                        	catHistogram.putIfAbsent(campaign.getCategory(), new TreeMap<String, Integer>());
		                        m = catHistogram.get(campaign.getCategory());
		                        m.putIfAbsent(campaign.getSubcategory(), 0);
		                        m.put(campaign.getSubcategory(), m.get(campaign.getSubcategory())+1);
		                        catHistogram.put(campaign.getCategory(), m);
	                        }
	                        
	                        
	                        //histogram
	                        addToHistogram(campaign);
	                        
	                        //mode/median
	                        if(mode.containsKey(campaign.getDuration())) {
	                        	mode.put(campaign.getDuration(), mode.get(campaign.getDuration())+1);
	                        } else {
	                        	mode.put(campaign.getDuration(), 1);
	                        }
	                        
	                        
	                        //Maps Category to Subcategory to Frequency of the category/subcategory pair
	                        
	                        
	                        
	                        /*if(categoryHistogram.containsKey(campaign.getCategory())) {
	                        	categoryHistogram.put(campaign.getCategory(), categoryHistogram.get(campaign.getCategory())+1);
	                        } else {
	                        	categoryHistogram.put(campaign.getCategory(), 1);
	                        }
	                        
	                        if(subcategoryHistogram.containsKey(campaign.getSubcategory())) {
	                        	subcategoryHistogram.put(campaign.getSubcategory(), subcategoryHistogram.get(campaign.getSubcategory())+1);
	                        } else {
	                        	subcategoryHistogram.put(campaign.getSubcategory(), 1);
	                        }
	                       
	                        catMapSub.putIfAbsent(campaign.getCategory(), new HashSet<String>());
	                        catMapSub.put(campaign.getCategory(), catMapSub.get(campaign.getCategory()).add(campaign.getSubcategory()));*/
                        
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
	
	//adds number of backers data to histogram
	private static void addToHistogram(myDataClass campaign) {
		int b = campaign.getBackers();
		
		for(int i = 20; i <= 400; i+=20) {
			if (b<=i) {
				histogram.put(i, histogram.get(i)+1);
				break;
			}
		}
		
		if(b>400) {
			histogram.put(401, histogram.get(401)+1);
		}
		
	}

	private static CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { 
                new NotNull(), //Project ID 
                new NotNull(), //name
                new NotNull(), //url
                new NotNull(), //category
                new NotNull(), //sub category
                new Optional(), //location
                new NotNull(), //status
                new ParseDouble(), //goal
                new Optional( new ParseInt()), //pledged
                new ParseDouble(), //funded Percentage
                new ParseInt(), //backers
                new ParseDate("E, dd MMM yyyy HH:mm:ss Z"), // funded Date
                new ParseInt(), //num levels
                new Optional(), //reward levels, un-parsed
                new ParseInt(), //updates
                new ParseInt(), //comments
                new ParseDouble() //duration in days
		};
        
        return processors;

	}

}
