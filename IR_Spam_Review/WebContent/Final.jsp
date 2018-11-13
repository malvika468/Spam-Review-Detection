<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
body {background-color:#66CDAA}
h1 {
    font-size: 40px;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ page import= "com.project.*"  %>
<%@ page import= "java.io.*"  %>
<%@ page import= "java.util.*"  %>
<title>Insert title here</title>
</head>
<body>

<center>
<div id = "div1"  >
<%
BufferedWriter outfile = new BufferedWriter(new FileWriter("outputfile.txt"));		
AFFINSentimentAnalysis senti = new AFFINSentimentAnalysis(outfile);
String inputFile = "C:\\Users\\user\\Desktop\\NeonWorkspace\\IR_GUI\\data.csv";

ReviewerAnalysis reviewer = new ReviewerAnalysis(outfile);
TextAnalysis text = new TextAnalysis(outfile);
String outputFile = "data1.csv";		

int GenuineReviews = 0;
int initialReviews = 0;

HashMap <String,Integer> op1 = reviewer.discardPerDayBasis(inputFile, outputFile, 3, 90.0);

//initialReviews = total reviews analysed by discardPerDayBasis() func.
inputFile = "data1.csv";
outputFile = "data2.csv";
HashMap <String,Integer> op2 = reviewer.discardExtremeRatingReviewBasis(inputFile, outputFile, 100.0, 5, 1);
inputFile = "data2.csv";
outputFile = "data3.csv";
HashMap <String,Integer> op3 =  reviewer.discardAvgReviewLengthBasis(inputFile, outputFile, 10, 75.0);
inputFile = "data3.csv";
outputFile = "data4.csv";

HashMap <String,Integer> op4 = text.discardReviewsWithLinks(inputFile, outputFile);
		inputFile = "data4.csv";
		outputFile = "data5.csv";
		HashMap <String,Integer> op5 =    text.discardReviewsWithQuestion(inputFile, outputFile);
		inputFile = "data5.csv";
		outputFile = "data6.csv";
		HashMap <String,Integer> op6 =  text.discardShortLengthReviews(inputFile, outputFile, 10);
		inputFile = "data6.csv";
		outputFile = "data7.csv";
		HashMap <String,Integer> op7 =   text.discardReviewsInCAPS(inputFile, outputFile, 90);
		inputFile = "data7.csv";
		outputFile = "data8.csv";
		HashMap <String,Integer> op8=  text.discardSimilarReviews(inputFile, outputFile, 0.95);
		inputFile = "data8.csv";
		outputFile = "data9.csv";
		HashMap <String,Integer> op9 =  text.discardReviewsWithDeviatedRating(inputFile, outputFile, 2, 90);
		inputFile = "data9.csv";
		outputFile = "data10.csv";
		HashMap <String,Integer> op10 =  text.discardReviewsWithDeviatedRating(inputFile, outputFile, 3.0);
		inputFile = "data10.csv";
		outputFile = "data11.csv";
		HashMap <String,Integer> op11 =   text.discardReviewsWithHighObjectivity(inputFile, outputFile, 0.6);
inputFile = "data11.csv";
outputFile = "data12.csv";

senti.storeAfinnScores(inputFile,outputFile);
inputFile = "data12.csv";
outputFile = "data13.csv";
   senti.computeAfinnAverage(inputFile,outputFile);
inputFile = "data13.csv";
outputFile = "final.csv";
HashMap <String,Integer> op12 =  senti.discardDeviatedScores(inputFile,outputFile, 3);

int analysedReviews = op1.get("Number of reviews being analysed");
int discardedReviews = op12.get("Reviews Discarded: ");
GenuineReviews += analysedReviews - discardedReviews;
	
	out.println("Number of reviews being analysed :  " + analysedReviews);
	out.println("<br>");
	out.println("Reviews Discarded : " + discardedReviews);
	out.println("<br>");
	out.println("Genuine Reviews : " +GenuineReviews);
	


//GenuineReviews += analysedReviews - discardedReviews;

//Display initial and Genuine Reviews count

outfile.close();
%>
</div>
</center>




</body>
</html>