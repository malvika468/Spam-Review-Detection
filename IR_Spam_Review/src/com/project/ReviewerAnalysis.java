package com.project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.opencsv.CSVReader;

public class ReviewerAnalysis {
	BufferedWriter outfile;
	//String outfile1;
	
	public ReviewerAnalysis(BufferedWriter outfile){
		this.outfile = outfile;
	 }
	 
	
public HashMap<String,Integer>  discardPerDayBasis(String inputFile, String outputFile, int perDayThreshold, double limitExceedThreshold) throws IOException{
	HashMap<String,Integer> op_gui = new HashMap<>();	
	System.out.println("<<<Discarding all the reviews from reviewers posting more than "+perDayThreshold+" reviews per day >> "+outputFile+" >>>");
		outfile.write("<<<Discarding all the reviews from reviewers posting more than "+perDayThreshold+" reviews per day >> "+outputFile+" >>>");
	    outfile.newLine();
	    File file = new File(outputFile);
	    file.delete();
	    file = new File(outputFile);
	    //Create the CSVFormat object with "\n" as a record delimiter
	     CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
	     FileWriter fileWriter =  new FileWriter(file.getName(),true);
	     //initialize CSVPrinter object 
	     List<String> listAddedToCSV = new ArrayList<String>();
	     CSVReader read1 = new CSVReader(new FileReader(inputFile));
	     int main_size=read1.readAll().size();
	     CSVReader read = new CSVReader(new FileReader(inputFile));
	     System.out.println("Number of reviews being analysed -->"+main_size);
	     op_gui.put("Number of reviews being analysed", main_size);           // here
		 outfile.write("Number of reviews being analysed -->"+main_size);
	     outfile.newLine();
	     CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter,csvFileFormat);
	     HashMap<String,HashMap<String,Integer>> Reviewer_ReviewCount = new HashMap<>();
	     String nextLine[];
	     while((nextLine = read.readNext())!=null){
	    	 //System.out.println(nextLine.length);
	    	 if(Reviewer_ReviewCount.containsKey(nextLine[3])){
	    		 HashMap<String,Integer> date_count = Reviewer_ReviewCount.get(nextLine[3]);
	    		 if(date_count.containsKey(nextLine[5])){
		    		 int temp = date_count.get(nextLine[5]);
		    		 temp = temp + 1;
		    		 Reviewer_ReviewCount.get(nextLine[3]).put(nextLine[5], temp);
	    		 }
	    		 else{
	    			 Reviewer_ReviewCount.get(nextLine[3]).put(nextLine[5], 1);
	    		 }
	    	 }
	    	 else{
	    		 HashMap<String,Integer> date_count = new HashMap<>();
	    		 date_count.put(nextLine[5], 1);
	    		 Reviewer_ReviewCount.put(nextLine[3], date_count);
	    	 }
	     }
	     ArrayList<String> discardedReviewer = new ArrayList<>();
	     int sz = Reviewer_ReviewCount.size();
	     for (HashMap.Entry<String,HashMap<String,Integer>> entry : Reviewer_ReviewCount.entrySet()){
	    	 HashMap<String,Integer> date_count = entry.getValue();
	    	 int limitExceeded = 0;
	    	 for (HashMap.Entry<String,Integer> ent : date_count.entrySet()){
	    		 if(ent.getValue() >= perDayThreshold)
	    			 limitExceeded++;
	    	 }
	    	 double limitExceedPercentage = (double)(limitExceeded/date_count.size()) * 100;
	    	 if(limitExceedPercentage >= limitExceedThreshold)
	    		 discardedReviewer.add(entry.getKey());
	     }
	     System.out.println("Total Reviewers: "+sz+"\tDefaulters: "+discardedReviewer.size());
	     op_gui.put("Total Reviewers", sz);
	     outfile.write("Total Reviewers: "+sz+"\tDefaulters: "+discardedReviewer.size());
		 outfile.newLine();
		 read = new CSVReader(new FileReader(inputFile));
		 int discardedReviews = 0;
		 while((nextLine = read.readNext())!=null){
			 if(discardedReviewer.contains(nextLine[3]))
				 discardedReviews++;
	    	 else{
	    		 List<String> listAddedToCSV1 = new ArrayList<String>();
	    		 listAddedToCSV1.add(nextLine[0]);
			     listAddedToCSV1.add(nextLine[1]);
			     listAddedToCSV1.add(nextLine[2]);
			     listAddedToCSV1.add(nextLine[3]);
			     listAddedToCSV1.add(nextLine[4]);
			     listAddedToCSV1.add(nextLine[5]);
			     csvFilePrinter.printRecord(listAddedToCSV1);
	    	 }
	     }
		 System.out.println("Total Discarded Reviews: "+discardedReviews);
		 op_gui.put("Total Discarded Reviews" , discardedReviews);
		 System.out.println("op size"+ op_gui.size());
	     outfile.write("Total Discarded Reviews: "+discardedReviews);
		 outfile.newLine();
		 read.close();
		 csvFilePrinter.close();
		 fileWriter.close();
		 return op_gui;
	}
	
	
public HashMap<String,Integer> discardExtremeRatingReviewBasis(String inputFile, String outputFile, double extremeReviewThreshold, int maxRating, int minRating) throws IOException{
		
	    HashMap<String,Integer> op_gui = new HashMap<>();
	    System.out.println("<<<Discarding all the reviews from reviewers posting more than "+extremeReviewThreshold+"% extreme rating reviews >> "+outputFile+" >>>");
		outfile.write("<<<Discarding all the reviews from reviewers posting more than "+extremeReviewThreshold+"%  extreme rating reviews >> "+outputFile+" >>>");
	    outfile.newLine();
	    File file = new File(outputFile);
	    file.delete();
	    file = new File(outputFile);
	  //Create the CSVFormat object with "\n" as a record delimiter
	     CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
	     FileWriter fileWriter =  new FileWriter(file.getName(),true);
	     //initialize CSVPrinter object 
	     List<String> listAddedToCSV = new ArrayList<String>();
	     CSVReader read1 = new CSVReader(new FileReader(inputFile));
	     int main_size=read1.readAll().size();
	     CSVReader read = new CSVReader(new FileReader(inputFile));
	     System.out.println("Number of reviews being analysed -->"+main_size);
	     op_gui.put("Number of reviews being analysed",main_size);
		 outfile.write("Number of reviews being analysed -->"+main_size);
	     outfile.newLine();
	     CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter,csvFileFormat);
	     HashMap<String,Integer> Reviewer_RatingCount = new HashMap<>();
	     HashMap<String,Integer> Reviewer_ExtremeCount = new HashMap<>();
	     String nextLine[];
	     while((nextLine = read.readNext())!=null){
	    	 //System.out.println(nextLine.length);
	    	 int c1 = 0;
	    	 int c2 = 0;
	    	 if(Reviewer_RatingCount.containsKey(nextLine[3])){
	    		 c1 = Reviewer_RatingCount.get(nextLine[3]);
	    		 c2 = Reviewer_ExtremeCount.get(nextLine[3]);
	    	 }
	    	 System.out.println(nextLine[4]);
	    	 if(Double.parseDouble(nextLine[4]) == maxRating || Double.parseDouble(nextLine[4]) == minRating)
    			 c2++;
    		 c1++;
	    	 Reviewer_RatingCount.put(nextLine[3], c1);
	    	 Reviewer_ExtremeCount.put(nextLine[3], c2);
	     }
	     int sz = Reviewer_RatingCount.size();
	     ArrayList<String> discardedReviewer = new ArrayList<>();
	     for (HashMap.Entry<String,Integer> entry : Reviewer_RatingCount.entrySet()){
	    	 double limitExceedPercentage = (double)(Reviewer_ExtremeCount.get(entry.getKey())/entry.getValue()) * 100;
	    	 if(limitExceedPercentage >= extremeReviewThreshold)
	    		 discardedReviewer.add(entry.getKey());
	     }
	     System.out.println("Total Reviewers: "+sz+"\tDefaulters: "+discardedReviewer.size());
	     op_gui.put("Total Reviewers", sz);
	     outfile.write("Total Reviewers: "+sz+"\tDefaulters: "+discardedReviewer.size());
		 outfile.newLine();
		 read = new CSVReader(new FileReader(inputFile));
		 int discardedReviews = 0;
		 while((nextLine = read.readNext())!=null){
			 if(discardedReviewer.contains(nextLine[3]))
				 discardedReviews++;
	    	 else{
	    		 List<String> listAddedToCSV1 = new ArrayList<String>();
	    		 listAddedToCSV1.add(nextLine[0]);
			     listAddedToCSV1.add(nextLine[1]);
			     listAddedToCSV1.add(nextLine[2]);
			     listAddedToCSV1.add(nextLine[3]);
			     listAddedToCSV1.add(nextLine[4]);
			     listAddedToCSV1.add(nextLine[5]);
			     csvFilePrinter.printRecord(listAddedToCSV1);
	    	 }
	     }
		 System.out.println("Total Discarded Reviews: "+discardedReviews);
		 op_gui.put("Total Discarded Reviews", discardedReviews);
	     outfile.write("Total Discarded Reviews: "+discardedReviews);
		 outfile.newLine();	
		 read.close();
		 csvFilePrinter.close();
		 fileWriter.close();
		 return op_gui;
	}
	
public HashMap<String,Integer> discardAvgReviewLengthBasis(String inputFile, String outputFile, int ReviewLengthThreshold, double AvgReviewLengthLimitThreshold) throws IOException{
		
	   HashMap<String,Integer> op_gui = new HashMap<>();
	   System.out.println("<<<Discarding all the reviews from reviewers posting less than "+ReviewLengthThreshold+" avg. length reviews >> "+outputFile+" >>>");
		outfile.write("<<<Discarding all the reviews from reviewers posting less than "+ReviewLengthThreshold+"  avg. length reviews >> "+outputFile+" >>>");
	    outfile.newLine();
	    File file = new File(outputFile);
	    file.delete();
	    file = new File(outputFile);
	    //Create the CSVFormat object with "\n" as a record delimiter
	    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
	    FileWriter fileWriter =  new FileWriter(file.getName(),true);
	    //initialize CSVPrinter object 
	    List<String> listAddedToCSV = new ArrayList<String>();
	    CSVReader read1 = new CSVReader(new FileReader(inputFile));
	    int main_size=read1.readAll().size();
	    CSVReader read = new CSVReader(new FileReader(inputFile));
	    System.out.println("Number of reviews being analysed -->"+main_size);
	    op_gui.put("Number of reviews being analysed", main_size);
		outfile.write("Number of reviews being analysed -->"+main_size);
	    outfile.newLine();
	    CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter,csvFileFormat);
	    HashMap<String,Integer> Reviewer_ReviewCount = new HashMap<>();
	    HashMap<String,Integer> Reviewer_ShortReviewLengthCount = new HashMap<>();
	     String nextLine[];
	     while((nextLine = read.readNext())!=null){
	    	 int c1 = 0;
	    	 int c2 = 0;
	    	 String review=nextLine[2];
    		 String[] words = review.split("\\s+"); 
    		 int reviewlength = words.length;
    		 //System.out.println("length-->"+reviewlength);
	    	 if(Reviewer_ReviewCount.containsKey(nextLine[3])){
	    		 c1 = Reviewer_ReviewCount.get(nextLine[3]);
	    		 c2 = Reviewer_ShortReviewLengthCount.get(nextLine[3]);
	    	 }
	    	 if(reviewlength <= ReviewLengthThreshold)
	    		 c2++;
    		 c1++;
	    	 Reviewer_ReviewCount.put(nextLine[3], c1);
	    	 Reviewer_ShortReviewLengthCount.put(nextLine[3], c2);
	     }
	     int sz = Reviewer_ReviewCount.size();
	     ArrayList<String> discardedReviewer = new ArrayList<>();
	     for (HashMap.Entry<String,Integer> entry : Reviewer_ReviewCount.entrySet()){
	    	 double limitExceedPercentage = (double)(Reviewer_ShortReviewLengthCount.get(entry.getKey())/entry.getValue()) * 100;
	    	 if(limitExceedPercentage >= AvgReviewLengthLimitThreshold)
	    		 discardedReviewer.add(entry.getKey());
	     }
	     System.out.println("Total Reviewers: "+sz+"\tDefaulters: "+discardedReviewer.size());
	     op_gui.put("Total Reviewers", sz);
	     outfile.write("Total Reviewers: "+sz+"\tDefaulters: "+discardedReviewer.size());
		 outfile.newLine();
		 read = new CSVReader(new FileReader(inputFile));
		 int discardedReviews = 0;
		 while((nextLine = read.readNext())!=null){
			 if(discardedReviewer.contains(nextLine[3]))
				 discardedReviews++;
	    	 else{
	    		 List<String> listAddedToCSV1 = new ArrayList<String>();
	    		 listAddedToCSV1.add(nextLine[0]);
			     listAddedToCSV1.add(nextLine[1]);
			     listAddedToCSV1.add(nextLine[2]);
			     listAddedToCSV1.add(nextLine[3]);
			     listAddedToCSV1.add(nextLine[4]);
			     listAddedToCSV1.add(nextLine[5]);
			     csvFilePrinter.printRecord(listAddedToCSV1);
	    	 }
	     }
		 System.out.println("Total Discarded Reviews: "+discardedReviews);
		 op_gui.put("Total Discarded Reviews", discardedReviews);
	     outfile.write("Total Discarded Reviews: "+discardedReviews);
		 outfile.newLine();	
		 read.close();
		 csvFilePrinter.close();
		 fileWriter.close();
		 return op_gui;	 
	}
}
