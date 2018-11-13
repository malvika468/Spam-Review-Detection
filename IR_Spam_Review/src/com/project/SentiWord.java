package com.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import net.sf.snowball.ext.EnglishStemmer;

class SentiWord{
	String word;
	protected String positive;
	protected String negative;
	//constructor
		public SentiWord(){
			this.positive = "0";
			this.negative = "0";
		}
		
		//constructor
		public SentiWord(String word){
			this.word = word;
			this.positive = "0";
			this.negative = "0";
		}
		
		//constructor
		public SentiWord(String positive, String negative, String word){
			this.positive = positive;
			this.negative = negative;
			this.word = word;
		}
		
		//get word objectivity
		public double calculateObjectivity(){
			double positive = Double.parseDouble(this.positive.toString());
			double negative = Double.parseDouble(this.negative.toString());
			double objectivity = 1 - (positive + negative);
			return objectivity;
		}
		
		//print SentiWord values to console
		public void print(){
			System.out.println("word:\t\t"+this.word);
			System.out.println("positive:\t"+this.positive);
			System.out.println("negative:\t"+this.negative);
			System.out.println("objectivity:\t"+this.calculateObjectivity());
		}
}
 
class ObjectivityAnalysis {
 
	protected Hashtable<String, SentiWord> wordList;
	private ArrayList<String> adj_tag;
	MaxentTagger tagger;
	Double positivity;
	Double negativity;
	Double objectivity;
	Double expectedRating;
	
	ObjectivityAnalysis() throws FileNotFoundException, IOException{
		adj_tag = new ArrayList<>();
		adj_tag.add("JJ");
		adj_tag.add("JJR");
		adj_tag.add("JJS");
		final String downloadedFile = "C:\\Users\\user\\Desktop\\NeonWorkspace\\IR_GUI\\Resources\\SentiWordNet_3.0.0\\SentiWordNet_3.0.0_20130122.txt";
		final String minifiedFile = "C:\\Users\\user\\Desktop\\NeonWorkspace\\IR_GUI\\Resources\\SentiWordNet_3.0.0\\SentiWordNetMinified.txt"; 
		tagger =  new MaxentTagger("C:\\Users\\user\\Desktop\\NeonWorkspace\\IR_GUI\\Resources\\english-left3words-distsim.tagger");
		// called to minify the downloaded file
		minifySentiWordNetFile(downloadedFile, minifiedFile);
		
		// load SentiWordNet to memory, MUST BE CALLED BEFORE ANYTHING ELSE
		loadSentiWordList(minifiedFile);
		
	}
	//load SentiWordNet to a hashtable with word as key
	private Hashtable<String, SentiWord> loadSentiWordList(String sourceFile) throws FileNotFoundException, IOException{
		File file = new File(sourceFile);
		Hashtable<String, SentiWord> list = new Hashtable<String, SentiWord>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String[] sentiWord = line.split("\t");
		    	SentiWord currentSentiWord = new SentiWord(sentiWord[1], sentiWord[2], sentiWord[0]);
		    	list.put(sentiWord[0], currentSentiWord);
		    }
		}
		wordList = list;
		return list;
	}
	
	//minify downloaded file
	private void minifySentiWordNetFile(String sourceFile, String targetFile) throws FileNotFoundException, IOException{
		File file = new File(sourceFile);
		PrintWriter writer = new PrintWriter(targetFile, "UTF-8");
		int count = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
		    while ((line = br.readLine()) != null) {
		    	
		    	count++;
		    	String[] sentiWord = line.split("\t");
		    	if(sentiWord[0].equals("#")){
		    		continue;
		    	}
		    	
				for (String word : sentiWord[4].split(" ")){
					//System.out.println(word);
					String[] removeId = word.split("#");
					
					if(word.contains("#") && removeId.length > 0){
						word = removeId[0];
						//System.out.println(sentiWord[2]+"\t"+sentiWord[3]+"\t"+word);
				    	SentiWord currentWord = new SentiWord(sentiWord[2], sentiWord[3], word);
				    	
				    	if(currentWord.positive.equals("0") && currentWord.negative.equals("0")){
				    		continue;
				    	} else {
				    		//System.out.println("lines written:");
				    		writer.println(currentWord.word+"\t"+currentWord.positive+"\t"+currentWord.negative);
				    	}
					}
				
					if(count % 1000 == 0){
			    		//System.out.println("lines written:"+count);
			    	}
		    	}
		    }
			//System.out.println("lines written:"+count);
			writer.close();
		}
	}
	
	
	//get SentiWord object
	private SentiWord getSentiWord(String word) {
		String initialWord = word;
		
		//preprocess word, remove punctuation from sentence
		word = word.replaceAll("\\p{P}", "");
		word = word.toLowerCase();
		
		SentiWord sentiWord = new SentiWord(initialWord);

		if(wordList.containsKey(word)){
			sentiWord = wordList.get(word);
		} else if(wordList.containsKey(stemTerm(word))){
			System.out.println("Stem for "+word+": "+stemTerm(word));
			sentiWord = wordList.get(stemTerm(word));
		}
		
		sentiWord.word = initialWord;
		
		return sentiWord;
	}
	
	private String stemTerm(String word) {
		EnglishStemmer english = new EnglishStemmer();
        english.setCurrent(word.toLowerCase());
        english.stem();
	    return english.getCurrent();
	}
	
	//get the sentiment score for a given sentence
	//UNDER CONSTRUCTION
	private SentiWord calculateSentenceScore(String sentence) {
		SentiWord sentiText = new SentiWord(sentence);
		double positive = 0, negative = 0;
		for(String word : sentence.split(" ")){
			SentiWord currentWord = getSentiWord(word);
			positive += Double.parseDouble(currentWord.positive);
			negative += Double.parseDouble(currentWord.negative);
		}
		sentiText.positive = String.valueOf(positive) ;
		sentiText.negative = String.valueOf(negative);
		return sentiText;
	}
	
	@SuppressWarnings("unused")
	public void getObjectivityScore(String test) throws FileNotFoundException, IOException{
		//download file from http://sentiwordnet.isti.cnr.it/downloadFile.php
		//String a = "Very disappointing. This was advertised to be a note-for-note lesson on selected Clapton tunes and it was not even close. There was no tab to support the little (and sometime inaccurate) portions of the songs that were listed. I sent a complaint directly to Hal Leonard but never received the courtesy of a reply.";
		
		String tagged = tagger.tagString(test);
		System.out.println(tagged);
		String[] tags = tagged.split("\\s+");
		int total = 0;
		ArrayList<String> adj = new ArrayList<>();
		for(String tag:tags){
			String[] word_tag = tag.split("_");
			if(word_tag.length == 2){
				total++;
				if(adj_tag.contains(word_tag[1]))
					adj.add(word_tag[0]);
			}
		}
		
		System.out.println("Total tags : "+total+"\tAdj tags : "+adj.size());
		
		SentiWord sentiment = this.calculateSentenceScore(adj.toString());
		positivity = Double.parseDouble(sentiment.positive)/adj.size();
		negativity = Double.parseDouble(sentiment.negative)/adj.size();
		objectivity = 1-(positivity+negativity);
		System.out.println("pos:"+positivity+"\tneg:"+negativity+"\tobj:"+objectivity);
		
		expectedRating = 3 + (((positivity - negativity)/1) * 2);
		System.out.println("expectedRating:"+expectedRating);
	}
}