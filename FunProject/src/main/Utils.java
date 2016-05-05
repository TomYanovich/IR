package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import dataStructures.Record;
import dataStructures.WordMatrix;

public class Utils {
	public static double calcPerc(HashMap<Integer, HashSet<Integer>> answers,
			Integer[][] top10) {

		double avgPerc = 0;
		double sumAvgPerc = 0;

		for (int q = 0; q < top10.length; q++) {
			int countRel = 0;
			double percision = 0;

			for (int d = 0; d < top10[q].length; d++) {

				if (answers.get(q + 1).contains(top10[q][d])) {
					countRel++;
					percision += (double) countRel / (d + 1);

				}
			}
			if (countRel == 0)
				avgPerc = 0;
			else
				avgPerc = percision / countRel;

			sumAvgPerc += avgPerc;
		}
		double MAP = sumAvgPerc / top10.length;
		return MAP;
	}
	
	public static HashMap<Integer, HashSet<Integer>> readReleventDocs(
			String relevantDocsFile2) throws FileNotFoundException, IOException {
		
		HashMap<Integer, HashSet<Integer>> relevant = new HashMap<Integer, HashSet<Integer>>();
		try (BufferedReader br = new BufferedReader(new FileReader(
				relevantDocsFile2))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {

				sb.append(line);
				sb.append(System.lineSeparator());

				String[] arguments = line.split(" ");

				int q;
				int d;
				try {
					q = Integer.valueOf(arguments[0]);
					d = Integer.valueOf(arguments[1]);
				} catch (NumberFormatException e) {
					line = br.readLine();
					continue;
				}
				if (relevant.containsKey(q)) {
					relevant.get(q).add(d);

				} else {
					HashSet<Integer> docsSet = new HashSet<Integer>();
					docsSet.add(d);
					relevant.put(q, docsSet);
				}
				line = br.readLine();
			}
		}
		return relevant;
	}
	
	
	
	public static int getTotalNumOfWords(String fileName)
			throws FileNotFoundException, IOException {

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = br.readLine();
			int totalWords = 0;
			while (line != null) {
				totalWords++;
				line = br.readLine();
			}
			return totalWords;
		}
	}
	
	public static Integer[][] getTop10(double[][] grades) {
		Integer[][] result = new Integer[grades.length][10];
		for (int q = 0; q < grades.length; q++) {
			final double[] gradedDocs = grades[q];
			List<Integer> top10 = new ArrayList<>();
			for (int doc = 0; doc < gradedDocs.length; doc++) {
					top10.add(doc + 1);
			}
			Collections.sort(top10, new Comparator<Integer>() {

				@Override
				public int compare(Integer doc1, Integer doc2) {
					double diff = gradedDocs[doc1 - 1] - gradedDocs[doc2 - 1];
					if (diff < 0){
						return 1;
					} else if (diff > 0){
						return -1;
					} else{
						return 0;
					}
				}
			});
			List<Integer> onlytop10;
			if (top10.size() >= 10) {
				onlytop10 = top10.subList(0, 10);
			} else {
				onlytop10 = top10;
				for (int i = top10.size(); i < 10; i++) {
					onlytop10.add(-1);
				}
			}
			result[q] = ((Integer[]) (onlytop10.toArray(new Integer[onlytop10
					.size()])));

		}
		return result;
	}
	
	public static void printTop10(Integer[][] top10, String outputFileName, long time, double map)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputFileName), "utf-8"))) {
			String output = "";
			int maxIlength = (int) Math.floor(Math.log10(top10.length) + 1);
			int maxDocLength = 4;
			int ibuffer;
			int docBuffer;
			
			for (int i = 1 ; i<7 + maxIlength ; i++){
				output+=" ";
			}
			output+="\t";
			
			for (int i = 1 ; i<=top10[0].length ; i++){
				output+= i + "\t\t";
			}
			output+="\n";
			
			for (int i = 0 ; i<top10.length ; i++){
				ibuffer = maxIlength - (int) Math.floor(Math.log10(i) + 1);
				
				output+= "Query " + (i+1) + ":";
				
				for (int b1=0 ; b1<ibuffer ; b1++){
					output+=" ";
				}
				output+="\t";
				
				for (int j=0 ; j<top10[i].length ; j++){
					
					docBuffer = maxDocLength - (int) Math.floor(Math.log10(top10[i][j]) + 1);
					for (int b2=0 ; b2<docBuffer ; b2++){
						output+=" ";
					}
					output+= top10[i][j] + "\t";
				}
				output += "\n";
			}
			output+="\n";
			output+="Calculation time: " + time + " seconds\n";
			output+="MAP: " + map;
			writer.write(output);
		}
	}
}
