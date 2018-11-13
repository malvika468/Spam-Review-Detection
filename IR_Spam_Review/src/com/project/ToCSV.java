package com.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.xml.parsers.*;
import java.io.*;

public class ToCSV {
	BufferedWriter outfile;
	
public	ToCSV(BufferedWriter outfile) throws IOException{
		this.outfile = outfile;
		System.out.println("******Converting all.xml to data.csv <uniqueId,Asin,Review>********");
		outfile.write("******Converting all.xml to data.csv <uniqueId,Asin,Review>********");
        outfile.newLine();
	}


	@SuppressWarnings("resource")
	public void makeHashMapOfOriginalXML()  throws ParserConfigurationException,IOException,SAXException, ServletException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    //Build Document
	    Document document = builder.parse(new File("all.xml"));
	    //Normalize the XML Structure; It's just too important !!
	    document.getDocumentElement().normalize();
	    Element root = document.getDocumentElement();
	    
	    System.out.println("Product Category--->"+root.getNodeName());
	    outfile.write("Product Category--->"+root.getNodeName());
	    outfile.newLine();
	   
	    NodeList nList = document.getElementsByTagName("review");
	    File file = new File("data.csv");
	    file.delete();
	    file = new File("data.csv");
	    CSVPrinter csvFilePrinter = null;
	    //Create the CSVFormat object with "\n" as a record delimiter
	    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
	    FileWriter fileWriter =  new FileWriter(file.getName(),true);
	    //initialize CSVPrinter object 
	    csvFilePrinter = new CSVPrinter(fileWriter,csvFileFormat);
	    List<String> listAddedToCSV = new ArrayList<String>();
	         
	    System.out.println("Number of Reviews --> "+String.valueOf(nList.getLength()));       
	    outfile.write("Number of Reviews --> "+String.valueOf(nList.getLength()));
	    outfile.newLine();
	    //int count = 0;
	    for (int temp = 0; temp < nList.getLength(); temp++){
	    	listAddedToCSV.clear();
	    	Node node = nList.item(temp);
		    if (node.getNodeType() == Node.ELEMENT_NODE){
		    	Element eElement = (Element) node;
		    	if(!(eElement.getElementsByTagName("reviewer").item(0)==null)){
		    		//System.out.println(++count);
			        String unique_id = eElement.getElementsByTagName("unique_id").item(0).getTextContent();
			        unique_id = unique_id.trim().replace("\n", "").replace("\r", "");
			        String review = eElement.getElementsByTagName("review_text").item(0).getTextContent();
			        review = review.trim().replace("\n", "").replace("\r", "");
			        String asin = eElement.getElementsByTagName("asin").item(0).getTextContent();
			        asin = asin.trim().replace("\n", "").replace("\r", "");
			        String reviewer = eElement.getElementsByTagName("reviewer").item(0).getTextContent();
			        reviewer = reviewer.trim().replace("\n", "").replace("\r", "");
			        String rating = eElement.getElementsByTagName("rating").item(0).getTextContent();
			        rating = rating.trim().replace("\n", "").replace("\r", "");
			        String date = eElement.getElementsByTagName("date").item(0).getTextContent();
			        date = date.trim().replace("\n", "").replace("\r", "");
			        outfile.write(unique_id + "\t" + review+"\t" +asin + "\t" +reviewer + "\t" + rating+"\t" +date);
			        outfile.newLine();
			        if(!unique_id.equals(" ")&&!asin.equals(" ")&&!review.equals(" ")&&!reviewer.equals(" ")&&!rating.equals(" ")&&!date.equals(" ")){
				        listAddedToCSV.add(unique_id);
				        listAddedToCSV.add(asin);
		        		listAddedToCSV.add(review);
		        		listAddedToCSV.add(reviewer);
				        listAddedToCSV.add(rating);
		        		listAddedToCSV.add(date);
		        		csvFilePrinter.printRecord(listAddedToCSV);
			        }
		    	}
		    }
	    }
	    csvFilePrinter.close();
	    fileWriter.close();
	}
}
