package main;

import java.io.IOException;
import java.util.Date;

import algorithms.BM25;
import algorithms.LM;
import algorithms.VSM;

public class AlgoHandler implements Runnable {
	private Class<?> algo;
	long start;
	long end;
	long time;
	double[][]grades;
	Integer[][]top10;
	double MAP;
	String fileName;

	@Override
	public void run() {
		System.err.println("Starting " + algo.getSimpleName() + "...");
		start = new Date().getTime();
		
		switch (algo.getSimpleName()) {
		
		case "BM25":
			grades = BM25.gradeAll(MainClass.documents, MainClass.queries, MainClass.k, MainClass.withDocLength);
			fileName = "bm25-output.txt";			
			break;
		case "VSM":
			grades = VSM.gradeAll(MainClass.documents, MainClass.queries);
			fileName = "vsm-output.txt";
			break;
		case "LM":
			grades = LM.gradeAll(MainClass.documents, MainClass.queries, MainClass.lambda);
			fileName = "lm-output.txt";
			break;
		}
		
		end = new Date().getTime();
		time = (end - start) / 1000;
		top10 = Utils.getTop10(grades);
		MAP = Utils.calcPerc(MainClass.answers, top10);
		try {
			Utils.printTop10(top10, fileName, time, MAP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AlgoHandler(Class className) {
		this.algo = className;
	}

}
