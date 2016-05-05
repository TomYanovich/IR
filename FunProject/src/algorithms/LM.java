package algorithms;

import java.util.ArrayList;
import java.util.Collection;

import dataStructures.WordMatrix;

public class LM {
	/**
	 * Grades a document given a query
	 * 
	 * @param wordMatrix
	 * @param doc
	 * @param query
	 * @param lambda
	 * @return
	 */
	private static double grade(WordMatrix wordMatrix, int doc,
			Collection<Integer> query, double lambda) {
		double mul = 1;
		if (lambda == 0) {
			return 0;
		}
		
		for (int q : query) {
			mul *= (1 - lambda) * wordMatrix.getProbability(q, doc)
					+ lambda * wordMatrix.getProbability(q);
		}
		return mul;
	}

	/**
	 * Grades all documents given a all queries
	 * 
	 * @param docs
	 * @param queries
	 * @param k
	 * @return
	 */
	public static double[][] gradeAll(WordMatrix docs, WordMatrix queries,
			double lambda) {
		double[][] documentScores = new double[queries.getN()][docs
				.getN()];
		for (int q = 1; q <= queries.getN(); q++) {
			for (int doc = 1; doc <= docs.getN(); doc++) {
				documentScores[q-1][doc-1] = grade(docs, doc, queries.getBooleanDocument(q), lambda);	
			}
		}
		return documentScores;
	}
}
