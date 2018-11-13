package com.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

@SuppressWarnings("resource")
public class Simulator {
	
	public static void main(String args[])throws Exception
	{
		BufferedWriter outfile = new BufferedWriter(new FileWriter("outputfile.txt"));		
		/*
		ToCSV tocsv = new ToCSV(outfile);
		tocsv.makeHashMapOfOriginalXML();
		String inputFile = "data.csv";
						
		ReviewerAnalysis reviewer = new ReviewerAnalysis(outfile);
		String outputFile = "output.csv";
		reviewer.discardPerDayBasis(inputFile, outputFile, 2, 90.0);
		reviewer.discardExtremeRatingReviewBasis(inputFile, outputFile, 100.0, 5, 1);
		reviewer.discardAvgReviewLengthBasis(inputFile, outputFile, 10, 75.0);
			
		AFFINSentimentAnalysis senti = new AFFINSentimentAnalysis(outfile);
		senti.storeAfinnScores("data.csv","reviewsWithAfinnScore.csv");
		senti.computeAfinnAverage("reviewsWithAfinnScore.csv","reviewsWithAfinnScoreAndProductWiseAverage.csv");
		senti.discardDeviatedScores("reviewsWithAfinnScoreAndProductWiseAverage.csv","reviewsAfterAfinn.csv", 3);
		
		*/
		TextAnalysis text = new TextAnalysis(outfile);
		String inputFile = "data.csv";
		String outputFile = "output.csv";
		
		//text.discardReviewsWithLinks(inputFile, outputFile);
		//text.discardReviewsWithQuestion(inputFile, outputFile);
		//text.discardShortLengthReviews(inputFile, outputFile, 15);
		//text.discardReviewsInCAPS(inputFile, outputFile, 90);
		// 
		text.discardSimilarReviews(inputFile, "output.csv", 0.95);
		//text.discardReviewsWithDeviatedRating(inputFile, outputFile, 2, 90);
		//text.discardReviewsWithDeviatedRating(inputFile, outputFile, 3.0);
		//text.discardReviewsWithHighObjectivity(inputFile, outputFile, 0.6);
		
		outfile.close();
		
	}
	
	
	
	
	
}