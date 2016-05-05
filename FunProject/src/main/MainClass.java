package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import algorithms.BM25;
import algorithms.LM;
import algorithms.VSM;
import dataStructures.WordMatrix;

public class MainClass {

	public static String docFileName = "tmg_docs_tf.txt";
	public static String dictionaryFileName = "dictionary.txt";
	public static String queriesFileName = "tmg_queries_tf.txt";
	public static String relevantDocsFile = "qrels_reindexed.txt";
	public static String outputFileName = "output.txt";
	public static double k = 16;
	public static boolean withDocLength = false;
	public static double lambda = 0.1;

	static int totalNumOfWords;
	static WordMatrix documents;
	static WordMatrix queries;
	static HashMap<Integer, HashSet<Integer>> answers;

	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		
		totalNumOfWords = Utils.getTotalNumOfWords(dictionaryFileName);
		documents = new WordMatrix(totalNumOfWords, docFileName);
		queries = new WordMatrix(totalNumOfWords, queriesFileName);
		answers = Utils.readReleventDocs(relevantDocsFile);
		
		Thread bm25Handler = new Thread(new AlgoHandler(BM25.class));
		Thread vsmHandler =  new Thread(new AlgoHandler(VSM.class));
		Thread lmHandler =  new Thread(new AlgoHandler(LM.class));
		
		bm25Handler.start();
		vsmHandler.start();
		lmHandler.start();
	}
}
