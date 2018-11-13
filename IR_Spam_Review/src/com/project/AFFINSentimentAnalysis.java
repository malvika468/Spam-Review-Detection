package com.project;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.opencsv.CSVReader;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class AFFINSentimentAnalysis {
	
	HashMap<String, Integer> afinn_words;
	BufferedWriter outfile;
	
public AFFINSentimentAnalysis(BufferedWriter outfile) throws IOException{
		this.outfile = outfile;
		System.out.println("******Applying Sentiment Based Model********");
		outfile.write("******Applying Sentiment Based Model********");
        outfile.newLine();
	}
	
	void getAfinnMap()throws IOException{
		System.out.println("<<<Getting hashMap for Afinn) word>>>");
		outfile.write("<<<Getting hashMap for Afinn) word>>>");
        outfile.newLine();
		afinn_words = new HashMap<String, Integer>();
		File file = new File("C:\\Users\\user\\Desktop\\NeonWorkspace\\IR_GUI\\Resources\\working_AFINN-111.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));  
		String wholeContent="";
		String line="";
		while ((line = br.readLine()) != null) {
			wholeContent = wholeContent+line;
		}
		String tokenStr[] = wholeContent.split(";;");
		outfile.write("<<<Displaying Affin HashMap Content>>>");
        outfile.newLine();
		for(int i = 0;i< tokenStr.length;i++){
			String tokenStr1[] = tokenStr[i].split("\t");
			String word = tokenStr1[0];
			if(i==0){
				word = word.substring(1, word.length());		
			}
			outfile.write(word+"\t"+tokenStr1[1]);
	        outfile.newLine();
			afinn_words.put(word.toLowerCase(), Integer.parseInt(tokenStr1[1]));	
		}
		System.out.println("Number of Affin words --> "+afinn_words.size());
		outfile.write("Number of Affin words --> "+afinn_words.size());
        outfile.newLine();
	}
 
	 @SuppressWarnings("resource")
	public void storeAfinnScores(String inputFile, String outputfile)throws IOException
	{
		 getAfinnMap();	
		 System.out.println("<<<Getting Afinn Scores for all the reviews in "+outputfile+" >>>");
		 outfile.write("<<<Getting Afinn Scores for all the reviews in "+outputfile+" >>>");
	     outfile.newLine();
	     File file = new File(outputfile);
	     file.delete();
	     file = new File(outputfile);
	     //Create the CSVFormat object with "\n" as a record delimiter
	     CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
	     FileWriter fileWriter =  new FileWriter(file.getName(),true);
	     //initialize CSVPrinter object 
	     List<String> listAddedToCSV = new ArrayList<String>();
	     CSVReader read = new CSVReader(new FileReader(inputFile));
	     int main_size=read.readAll().size();
	     CSVReader read1 = new CSVReader(new FileReader(inputFile));
	     System.out.println("Number of reviews being analysed -->"+main_size);
		 outfile.write("Number of reviews being analysed -->"+main_size);
	     outfile.newLine();
	     CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter,csvFileFormat);
	     for(int k=0;k<main_size-1;k++){
	    	 String[] nextLine = read1.readNext();
	    	 listAddedToCSV.clear();
	    	 int score = 0;
	    	 outfile.write("Next Line-->"+nextLine[1]+"\t"+nextLine[2]);
		     outfile.newLine();
	        StringTokenizer st = new StringTokenizer(nextLine[2], " ,.-/:&[]!()?!!'\"");
	        outfile.write("Number of Tokens-->"+st.countTokens());
	        outfile.newLine();
	        for(int j = 0;j< st.countTokens();j++){
	        	String current_token = st.nextToken().toLowerCase();
	        	if(afinn_words.containsKey(current_token)){  // if affin contains this word
	        		//outfile.write("Affin Matched --> "+current_token+"\t"+afinn_words.get(current_token));
	    	        //outfile.newLine();
	        		score = score + afinn_words.get(current_token);
	        	}
	        }
	        listAddedToCSV.add(nextLine[0]);
	        listAddedToCSV.add(nextLine[1]);
	        listAddedToCSV.add(nextLine[2]);
	        listAddedToCSV.add(String.valueOf(score));
	        csvFilePrinter.printRecord(listAddedToCSV);
	     } 	
	     csvFilePrinter.close();
		 fileWriter.close();
	}

	 @SuppressWarnings("resource")
	 public void computeAfinnAverage(String inputFile, String outputfile)throws IOException{
		 System.out.println("<<<Getting Afinn Average Score for all the products in "+outputfile+" >>>");
		 outfile.write("<<<Getting Afinn Average Score for all the products in "+outputfile+" >>>");
	     outfile.newLine();
		 HashMap<String, Double> sum_total = new HashMap<>();
		 HashMap<String, Integer> count = new HashMap<>();
		 CSVReader read;
		 String nextLine[];
		 read = new CSVReader(new FileReader(inputFile));
	     while((nextLine = read.readNext())!=null){
	    	 //System.out.println(nextLine.length);
	    	 if(sum_total.containsKey(nextLine[1])){
	    		 Double temp = sum_total.get(nextLine[1]);
	    		 temp = temp + Integer.parseInt(nextLine[3]);
	    		 sum_total.put(nextLine[1], temp);
	    		 Integer count_var = count.get(nextLine[1]);
	    		 count_var++;
	    		 count.put(nextLine[1], count_var);
	    	 }
	    	 else{
	    		 sum_total.put(nextLine[1], 0.0);
	    		 count.put(nextLine[1], 1);
	    	 }
	     }
	     System.out.println("Number of unique products --> "+count.size());
	     outfile.write("Number of unique products --> "+count.size());
	     outfile.newLine();
	     File file = new File(outputfile);
		 file.delete();
		 file = new File(outputfile);
		 //Create the CSVFormat object with "\n" as a record delimiter
		 CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
		 FileWriter fileWriter =  new FileWriter(file.getName(),true);
		 //initialize CSVPrinter object 
		 List<String> listAddedToCSV = new ArrayList<String>();
		 CSVReader read2 = new CSVReader(new FileReader(inputFile));
	     String nextLineAgain[];
	     CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter,csvFileFormat);
	     while((nextLineAgain = read2.readNext())!=null){
	    	 Double avg = 0.0 ;
	    	 listAddedToCSV.clear();
	    	 listAddedToCSV.add(nextLineAgain[0]);
	    	 listAddedToCSV.add(nextLineAgain[1]);
	    	 listAddedToCSV.add(nextLineAgain[2]);
	    	 listAddedToCSV.add(nextLineAgain[3]);
	    	 if(sum_total.containsKey(nextLineAgain[1])){
	    		 Double sum = sum_total.get(nextLineAgain[1]);
	    		 Integer temp_count = count.get(nextLineAgain[1]);
	    		 avg = sum/temp_count;
		    }
	    	 listAddedToCSV.add(avg.toString());
	    	 csvFilePrinter.printRecord(listAddedToCSV);
	    	 csvFilePrinter.flush();
	     }  
	     csvFilePrinter.close();
		 fileWriter.close();
	 }
	 	
	 public HashMap<String,Integer> discardDeviatedScores(String inputFile, String outputfile, double deviationThreshold) throws IOException{
		 System.out.println("inside senti"); 
		 HashMap<String,Integer> op = new HashMap<>();
		 System.out.println("<<<Discarding Afinn Scores highly deviated from average in "+outputfile+" >>>");
		 outfile.write("<<<Discarding Afinn Scores highly deviated from average in "+outputfile+" >>>");
	     outfile.newLine();
		 File file = new File(outputfile);
		 file.delete();
		 file = new File(outputfile);
		 //Create the CSVFormat object with "\n" as a record delimiter
		 CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
		 FileWriter fileWriter =  new FileWriter(file.getName(),true);
		 //initialize CSVPrinter object 
		 List<String> listAddedToCSV = new ArrayList<String>();
		 CSVReader read2 = new CSVReader(new FileReader(inputFile));
	     CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter,csvFileFormat);
	     String nextLineAgain[];
	     int count = 0;
	     int total = 0;
	     while((nextLineAgain = read2.readNext())!=null){
	    	 listAddedToCSV.clear();
	    	 Double review_afinn = Double.parseDouble(nextLineAgain[3]);
	    	 Double product_average = Double.parseDouble(nextLineAgain[4]);
	    	 Double difference = Math.abs(review_afinn - product_average);
	    	 total++;
	    	 if(difference <= deviationThreshold){	    	
		    	 listAddedToCSV.add(nextLineAgain[0]);
		    	 listAddedToCSV.add(nextLineAgain[1]);
		    	 listAddedToCSV.add(nextLineAgain[2]);
		    	 listAddedToCSV.add(nextLineAgain[3]);  	
		    	 csvFilePrinter.printRecord(listAddedToCSV);
		    	 csvFilePrinter.flush();
		    	 count++;
	    	 }
	     }
	     System.out.println("Reviews Analysed --> "+total+"\tReviews Accepted --> "+count+"\tReviews Discarded --> "+(total-count));
	     op.put("Reviews Analysed: ", total);
	     op.put("Reviews Accepted: ", count);
	     op.put("Reviews Discarded: ", total-count);
		 outfile.write("Reviews Analysed --> "+total+"\tReviews Accepted --> "+count+"\tReviews Discarded --> "+(total-count));
	     outfile.newLine();
	     csvFilePrinter.close();
		 fileWriter.close();
		 return op;
	 }
}
