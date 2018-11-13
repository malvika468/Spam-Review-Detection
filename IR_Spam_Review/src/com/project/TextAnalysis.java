package com.project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.opencsv.CSVReader;

import info.debatty.java.stringsimilarity.KShingling;
import info.debatty.java.stringsimilarity.StringProfile;

public class TextAnalysis {
	BufferedWriter outfile;
	ArrayList<String>stopWords;
	public static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
	public static final String Question_REGEX = "(?:Who|What|When|Where|Why|Which|Whom|Whose|How)(?:'s)?\\s+[^\\?\\.\\!]+\\?";
	
public	TextAnalysis(BufferedWriter outfile) throws FileNotFoundException{
		this.outfile = outfile;
		stopWords = new ArrayList<>();
		getStopWords();
	}
	
	private void getStopWords() throws FileNotFoundException{
		Scanner read = new Scanner (new File("C:\\Users\\user\\Desktop\\NeonWorkspace\\IR_GUI\\Resources\\stop-word-list.txt"));
		while(read.hasNextLine()){
			String str = read.nextLine();
			stopWords.add(str.toLowerCase());
		}
		System.out.println("Number of Stopwords: "+stopWords.size());
	}
	
	private ArrayList<String> discardStopWordsAndTokenize(String review) throws IOException{
		//System.out.println("<<<Discarding all the stop words and tokenize >> "+review+" >>>");
		outfile.write("<<<Discarding all the stop words and tokenize >> "+review+" >>>");
		outfile.newLine();
	    String[] words = review.split("\\s+"); 
	    ArrayList<String>tokens = new ArrayList<>();
	    for(String word:words){
	    	if(!stopWords.contains(word.toLowerCase()))
	    		tokens.add(word);
	    }
	    return tokens;
	}
	
public HashMap<String,Integer>  discardShortLengthReviews(String inputFile, String outputFile, int reviewLengthThreshold) throws IOException{
		HashMap<String,Integer> op = new HashMap<>();
		System.out.println("<<<Discarding all the reviews with length less than "+reviewLengthThreshold+ " >> "+outputFile+" >>>");
		outfile.write("<<<Discarding all the reviews with length less than "+reviewLengthThreshold+" >> "+outputFile+" >>>");
	    outfile.newLine();
	    File file = new File(outputFile);
	    file.delete();
	    file = new File(outputFile);
	    //Create the CSVFormat object with "\n" as a record delimiter
	     CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
	     FileWriter fileWriter =  new FileWriter(file.getName(),true);
	     //initialize CSVPrinter object 
	     CSVReader read1 = new CSVReader(new FileReader(inputFile));
	     int main_size=read1.readAll().size();
	     CSVReader read = new CSVReader(new FileReader(inputFile));
	     System.out.println("Number of reviews being analysed -->"+main_size);
	     op.put("Number of reviews being analysed", main_size);
		 outfile.write("Number of reviews being analysed -->"+main_size);
	     outfile.newLine();
	     CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter,csvFileFormat);
	     HashMap<String,HashMap<String,Integer>> Reviewer_ReviewCount = new HashMap<>();
	     String nextLine[];
	     int discardedReviews = 0;
	     while((nextLine = read.readNext())!=null){
	    	 String review=nextLine[2];
	    	 ArrayList<String> tokenizedReview = this.discardStopWordsAndTokenize(review);
	    	 //System.out.println(tokenizedReview);
	    	 if(tokenizedReview.size() > reviewLengthThreshold){
	    		 List<String> listAddedToCSV1 = new ArrayList<String>();
	    		 listAddedToCSV1.add(nextLine[0]);
			     listAddedToCSV1.add(nextLine[1]);
			     listAddedToCSV1.add(nextLine[2]);
			     listAddedToCSV1.add(nextLine[3]);
			     listAddedToCSV1.add(nextLine[4]);
			     listAddedToCSV1.add(nextLine[5]);
			     csvFilePrinter.printRecord(listAddedToCSV1);
	    	 }
	    	 else
	    		 discardedReviews++;
	     }
	     System.out.println("Total Discarded Reviews: "+discardedReviews);
	     op.put("Total Discarded Reviews", discardedReviews);
	     outfile.write("Total Discarded Reviews: "+discardedReviews);
		 outfile.newLine();	
		 csvFilePrinter.close();
		 fileWriter.close();
		 return op;
	}
	
	public HashMap<String,Integer> discardReviewsWithLinks(String inputFile, String outputFile) throws IOException{
		
		HashMap<String,Integer> op = new HashMap<>();
		
		System.out.println("<<<Discarding all the reviews with links>> "+outputFile+" >>>");
		outfile.write("<<<Discarding all the reviews with links>> "+outputFile+" >>>");
	    outfile.newLine();
	    File file = new File(outputFile);
	    file.delete();
	    file = new File(outputFile);
	    //Create the CSVFormat object with "\n" as a record delimiter
	     CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
	     FileWriter fileWriter =  new FileWriter(file.getName(),true);
	     //initialize CSVPrinter object 
	     CSVReader read1 = new CSVReader(new FileReader(inputFile));
	     int main_size=read1.readAll().size();
	     CSVReader read = new CSVReader(new FileReader(inputFile));
	     System.out.println("Number of reviews being analysed -->"+main_size);
	     op.put("Number of reviews being analysed", main_size);
		 outfile.write("Number of reviews being analysed -->"+main_size);
	     outfile.newLine();
	     CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter,csvFileFormat);
	     HashMap<String,HashMap<String,Integer>> Reviewer_ReviewCount = new HashMap<>();
	     String nextLine[];
	     int discardedReviews = 0;
	     while((nextLine = read.readNext())!=null){
	    	 String review=nextLine[2];
	    	 Pattern pattern = Pattern.compile(URL_REGEX);
	    	 Matcher matcher = pattern.matcher(review);
	    	 if(matcher.find()) {
	    		 System.out.println("String contains URL");
	    		 discardedReviews++;
	    	 }
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
	     op.put("Total Discarded Reviews", discardedReviews);
	     outfile.write("Total Discarded Reviews: "+discardedReviews);
		 outfile.newLine();	
		 csvFilePrinter.close();
		 fileWriter.close();
		 return op;
		
	}
	
	public HashMap<String , Integer> discardReviewsInCAPS(String inputFile, String outputFile, double CAPSThreshold) throws IOException{
		
		HashMap<String , Integer> op = new HashMap<>();
		System.out.println("<<<Discarding all the reviews containing more than "+CAPSThreshold+" % words in CAPITALS >> "+outputFile+" >>>");
		outfile.write("<<<Discarding all the reviews containing more than "+CAPSThreshold+" % words in CAPITALS >> "+outputFile+" >>>");
	    outfile.newLine();
	    File file = new File(outputFile);
	    file.delete();
	    file = new File(outputFile);
	    //Create the CSVFormat object with "\n" as a record delimiter
	     CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
	     FileWriter fileWriter =  new FileWriter(file.getName(),true);
	     //initialize CSVPrinter object 
	     CSVReader read1 = new CSVReader(new FileReader(inputFile));
	     int main_size=read1.readAll().size();
	     CSVReader read = new CSVReader(new FileReader(inputFile));
	     System.out.println("Number of reviews being analysed -->"+main_size);
	     op.put("Number of reviews being analysed", main_size);
		 outfile.write("Number of reviews being analysed -->"+main_size);
	     outfile.newLine();
	     CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter,csvFileFormat);
	     HashMap<String,HashMap<String,Integer>> Reviewer_ReviewCount = new HashMap<>();
	     String nextLine[];
	     int discardedReviews = 0;
	     while((nextLine = read.readNext())!=null){
	    	 String review=nextLine[2];
	    	 String[] words = review.split("\\s+"); 
	    	 int total = words.length;
	    	 int upper = 0;
	    	 for(String word:words){
	    		 String com = word.toUpperCase();
	    		 if(word.equals(com))
	    			 upper++;
	    	 }
	    	 double CAPSpercentage = (double)(upper/total)*100;
	    	 if(CAPSpercentage < CAPSThreshold) {
	    		 List<String> listAddedToCSV1 = new ArrayList<String>();
	    		 listAddedToCSV1.add(nextLine[0]);
			     listAddedToCSV1.add(nextLine[1]);
			     listAddedToCSV1.add(nextLine[2]);
			     listAddedToCSV1.add(nextLine[3]);
			     listAddedToCSV1.add(nextLine[4]);
			     listAddedToCSV1.add(nextLine[5]);
			     csvFilePrinter.printRecord(listAddedToCSV1);
	    	 }
	    	 else
	    		 discardedReviews++; 
	     }
	     System.out.println("Total Discarded Reviews: "+discardedReviews);
	     op.put("Total Discarded Reviews",discardedReviews);
	     outfile.write("Total Discarded Reviews: "+discardedReviews);
		 outfile.newLine();	
		 csvFilePrinter.close();
		 fileWriter.close();
		 return op;
	}
	
public	HashMap<String,Integer> discardSimilarReviews(String inputFile, String outputFile, double similarityThreshold) throws Exception{
		
		HashMap<String , Integer> op = new HashMap<>();
		System.out.println("<<<Discarding all duplicate reviews >> "+outputFile+" >>>");
		outfile.write("<<<Discarding all duplicate reviews >> "+outputFile+" >>>");
		outfile.newLine();
	    File file = new File(outputFile);
	    file.delete();
	    file = new File(outputFile);
	    //Create the CSVFormat object with "\n" as a record delimiter
	     CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
	     FileWriter fileWriter =  new FileWriter(file.getName(),true);
	     //initialize CSVPrinter object 
	     CSVReader read1 = new CSVReader(new FileReader(inputFile));
	     int main_size=read1.readAll().size();
	     CSVReader read = new CSVReader(new FileReader(inputFile));
	     System.out.println("Number of reviews being analysed -->"+main_size);
	     op.put("Number of reviews being analysed",main_size);
		 outfile.write("Number of reviews being analysed -->"+main_size);
	     outfile.newLine();
	     CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter,csvFileFormat);
	     HashMap<String,HashMap<String,Integer>> Reviewer_ReviewCount = new HashMap<>();
	     String nextLine[];
	     int discardedReviews = 0;
	     int count = 0;
	     ArrayList<Integer> dupe = new ArrayList<>();
	     while((nextLine = read.readNext())!=null){
	    	 count++;
	    	 if(!dupe.contains(count)){
		    	 boolean flag = false;
		    	 CSVReader read2 = new CSVReader(new FileReader(inputFile));
		    	 String nextLine2[];
		    	 int c = 0;
		    	 while((nextLine2 = read2.readNext())!=null){
		    		 c++;
		    		 if(c>count){
		    			 String review1=nextLine[2];
				    	 ArrayList<String> tokenizedReview1 = this.discardStopWordsAndTokenize(review1);
				    	 String review2=nextLine2[2];
				    	 ArrayList<String> tokenizedReview2 = this.discardStopWordsAndTokenize(review2);
				    	 
				    	 /*CharSequence processedReview1 = toString().valueOf(tokenizedReview1);
				    	 CharSequence processedReview2 = toString().valueOf(tokenizedReview2);
				    	 JaccardSimilarity jacsimilarity = new JaccardSimilarity();
				    	 double similarity = jacsimilarity.apply(processedReview1, processedReview2);
				    	 */
				    	 
				    	 String processedReview1 = toString().valueOf(tokenizedReview1);
				    	 String processedReview2 = toString().valueOf(tokenizedReview2);
				    	 KShingling ks = new KShingling(2);
				    	 StringProfile profile1 = ks.getProfile(processedReview1);
				    	 System.out.println(profile1);
				         StringProfile profile2 = ks.getProfile(processedReview2);
				    	 double similarity = profile1.cosineSimilarity(profile2);
				    	 
				    	 //System.out.println("Similarity:"+similarity);
				    	 if(similarity >= similarityThreshold){
				    		 dupe.add(c);
				    	 }
		    		 }
		    	 }
		    	 List<String> listAddedToCSV1 = new ArrayList<String>();
	    		 listAddedToCSV1.add(nextLine[0]);
				 listAddedToCSV1.add(nextLine[1]);
				 listAddedToCSV1.add(nextLine[2]);
				 listAddedToCSV1.add(nextLine[3]);
				 listAddedToCSV1.add(nextLine[4]);
				 listAddedToCSV1.add(nextLine[5]);
				 csvFilePrinter.printRecord(listAddedToCSV1);
	    	 }
	    	 else
	    		 discardedReviews++;
	     }
	     System.out.println("Total Discarded Reviews: "+discardedReviews);
	     op.put("Total Discarded Reviews:", discardedReviews);
	     outfile.write("Total Discarded Reviews: "+discardedReviews);
		 outfile.newLine();	
		 csvFilePrinter.close();
		 fileWriter.close();
		 return op;
	}
	
	private void computeAverageRating(String inputFile, String outputfile)throws IOException{
		 
		System.out.println("<<<Getting Average Rating for all the products in "+outputfile+" >>>");
		 outfile.write("<<<Getting Average Rating for all the products in "+outputfile+" >>>");
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
	    		 temp = temp + Double.parseDouble(nextLine[4]);
	    		 sum_total.put(nextLine[1], temp);
	    		 Integer count_var = count.get(nextLine[1]);
	    		 count_var++;
	    		 count.put(nextLine[1], count_var);
	    	 }
	    	 else{
	    		 sum_total.put(nextLine[1], Double.parseDouble(nextLine[4]));
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
	    	 listAddedToCSV.add(nextLineAgain[4]);
	    	 listAddedToCSV.add(nextLineAgain[5]);
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
	
	public HashMap<String ,Integer>  discardReviewsWithDeviatedRating(String inputFile, String outputfile, double deviationThreshold, double limitExceedThreshold) throws IOException{
		
		HashMap<String ,Integer> op = new HashMap<>();
		 System.out.println("<<<Discarding Reviews with Ratings highly deviated from average in "+outputfile+" >>>");
		 outfile.write("<<<Discarding Reviews with Ratings highly deviated from average in "+outputfile+" >>>");
	     outfile.newLine();
	     String inputFile1 = "avgRatingFile.csv";
	     this.computeAverageRating(inputFile, inputFile1);
		 File file = new File(outputfile);
		 file.delete();
		 file = new File(outputfile);
		 //Create the CSVFormat object with "\n" as a record delimiter
		 CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
		 FileWriter fileWriter =  new FileWriter(file.getName(),true);
		 //initialize CSVPrinter object 
		 List<String> listAddedToCSV = new ArrayList<String>();
		 CSVReader read2 = new CSVReader(new FileReader(inputFile1));
	     CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter,csvFileFormat);
	     String nextLineAgain[];
	     int t = 0;
	     int c = 0;
	     while((nextLineAgain = read2.readNext())!=null){
	    	 listAddedToCSV.clear();
	    	 Double review_afinn = Double.parseDouble(nextLineAgain[4]);
	    	 Double product_average = Double.parseDouble(nextLineAgain[6]);
	    	 Double difference = Math.abs(review_afinn - product_average);
	    	 t++;
	    	 //System.out.println("difference:"+difference);
	    		 if(difference >= deviationThreshold)
	    			 c++;
	    		 else{
	    			 listAddedToCSV.add(nextLineAgain[0]);
			    	 listAddedToCSV.add(nextLineAgain[1]);
			    	 listAddedToCSV.add(nextLineAgain[2]);
			    	 listAddedToCSV.add(nextLineAgain[3]);
			    	 listAddedToCSV.add(nextLineAgain[4]);
			    	 listAddedToCSV.add(nextLineAgain[5]);
			    	 //listAddedToCSV.add(String.valueOf(limit));
			    	 csvFilePrinter.printRecord(listAddedToCSV);
			    	 csvFilePrinter.flush();
	    		 }
	     }

	     System.out.println("Reviews Analysed --> "+t+"\tReviews Discarded --> "+c);
	     op.put("Reviews Analysed", t);
		 op.put("Reviews Discarded", c);
		 outfile.write("Reviews Analysed --> "+t+"\tReviews Discarded --> "+c);
	     outfile.newLine();
	     csvFilePrinter.close();
		 fileWriter.close();
		 return op;
	 }

	public HashMap<String ,Integer> discardReviewsWithQuestion(String inputFile, String outputFile) throws IOException{
		HashMap<String ,Integer> op = new HashMap<>();
	    System.out.println("<<<Discarding all the reviews with Question>> "+outputFile+" >>>");
		outfile.write("<<<Discarding all the reviews with Question>> "+outputFile+" >>>");
	    outfile.newLine();
	    File file = new File(outputFile);
	    file.delete();
	    file = new File(outputFile);
	    //Create the CSVFormat object with "\n" as a record delimiter
	     CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
	     FileWriter fileWriter =  new FileWriter(file.getName(),true);
	     //initialize CSVPrinter object 
	     CSVReader read1 = new CSVReader(new FileReader(inputFile));
	     int main_size=read1.readAll().size();
	     CSVReader read = new CSVReader(new FileReader(inputFile));
	     System.out.println("Number of reviews being analysed -->"+main_size);
	     op.put("Number of reviews being analysed", main_size);
		 outfile.write("Number of reviews being analysed -->"+main_size);
	     outfile.newLine();
	     CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter,csvFileFormat);
	     HashMap<String,HashMap<String,Integer>> Reviewer_ReviewCount = new HashMap<>();
	     String nextLine[];
	     int discardedReviews = 0;
	     while((nextLine = read.readNext())!=null){
	    	 String review=nextLine[2];
	    	 Pattern p = Pattern.compile(Question_REGEX);
	    	 Matcher m = p.matcher(review);
	    	 if(m.find()) {
	    		 System.out.println("String contains question");
	    		 discardedReviews++;
	    	 }
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
	     op.put("Total Discarded Reviews:",discardedReviews);
	     outfile.write("Total Discarded Reviews: "+discardedReviews);
		 outfile.newLine();	
		 csvFilePrinter.close();
		 fileWriter.close();
		 return op;
		
	}
	
	public HashMap<String , Integer> discardReviewsWithHighObjectivity(String inputFile, String outputFile, Double objectivityThreshold) throws IOException{
		
		HashMap<String , Integer> op = new HashMap<>();
		System.out.println("<<<Discarding all the reviews with High Objectivity>> "+outputFile+" >>>");
		outfile.write("<<<Discarding all the reviews with High Objectivity>> "+outputFile+" >>>");
	    outfile.newLine();
	    File file = new File(outputFile);
	    file.delete();
	    file = new File(outputFile);
	    //Create the CSVFormat object with "\n" as a record delimiter
	     CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
	     FileWriter fileWriter =  new FileWriter(file.getName(),true);
	     //initialize CSVPrinter object 
	     CSVReader read1 = new CSVReader(new FileReader(inputFile));
	     int main_size=read1.readAll().size();
	     CSVReader read = new CSVReader(new FileReader(inputFile));
	     System.out.println("Number of reviews being analysed -->"+main_size);
	     op.put("Number of reviews being analysed", main_size);
		 outfile.write("Number of reviews being analysed -->"+main_size);
	     outfile.newLine();
	     CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter,csvFileFormat);
	     HashMap<String,HashMap<String,Integer>> Reviewer_ReviewCount = new HashMap<>();
	     String nextLine[];
	     int discardedReviews = 0;
	     ObjectivityAnalysis analyseObj = new ObjectivityAnalysis();
	     while((nextLine = read.readNext())!=null){
	    	 String review=nextLine[2];
	    	 analyseObj.getObjectivityScore(review);
	    	 if(analyseObj.objectivity >= objectivityThreshold) {
	    		 System.out.println("Review has high Objectivity!!");
	    		 discardedReviews++;
	    	 }
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
	     op.put("Total Discarded Reviews:",discardedReviews);
	     outfile.write("Total Discarded Reviews: "+discardedReviews);
		 outfile.newLine();	
		 csvFilePrinter.close();
		 fileWriter.close();
		 return op;
	}

	public HashMap<String , Integer> discardReviewsWithDeviatedRating(String inputFile, String outputFile, Double deviationThreshold) throws IOException{
		
		HashMap<String , Integer> op = new HashMap<>();
		System.out.println("<<<Discarding all the reviews with High deviation in review text and rating>> "+outputFile+" >>>");
		outfile.write("<<<Discarding all the reviews with High deviation in review text and rating>> "+outputFile+" >>>");
	    outfile.newLine();
	    File file = new File(outputFile);
	    file.delete();
	    file = new File(outputFile);
	    //Create the CSVFormat object with "\n" as a record delimiter
	     CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
	     FileWriter fileWriter =  new FileWriter(file.getName(),true);
	     //initialize CSVPrinter object 
	     CSVReader read1 = new CSVReader(new FileReader(inputFile));
	     int main_size=read1.readAll().size();
	     CSVReader read = new CSVReader(new FileReader(inputFile));
	     System.out.println("Number of reviews being analysed -->"+main_size);
	     op.put("Number of reviews being analysed",main_size);
		 outfile.write("Number of reviews being analysed -->"+main_size);
	     outfile.newLine();
	     CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter,csvFileFormat);
	     HashMap<String,HashMap<String,Integer>> Reviewer_ReviewCount = new HashMap<>();
	     String nextLine[];
	     int discardedReviews = 0;
	     ObjectivityAnalysis analyseObj = new ObjectivityAnalysis();
	     while((nextLine = read.readNext())!=null){
	    	 String review = nextLine[2];
	    	 analyseObj.getObjectivityScore(review);
	    	 Double rating = Double.parseDouble(nextLine[4]);
	    	 Double diff = Math.abs(analyseObj.expectedRating - rating);
	    	 if(diff >= deviationThreshold) {
	    		 System.out.println("Rating is highly deviated from the review text!!");
	    		 discardedReviews++;
	    	 }
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
	     op.put("Total Discarded Reviews:", discardedReviews);
	     outfile.write("Total Discarded Reviews: "+discardedReviews);
		 outfile.newLine();	
		 csvFilePrinter.close();
		 fileWriter.close();
		 return op;
	}

}
