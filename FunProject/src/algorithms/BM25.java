package algorithms;
import java.util.HashSet;

import dataStructures.WordMatrix;

public class BM25 {
	/**
	 * Grades a document given a query
	 * 
	 * @param wordMatrix
	 * @param doc
	 * @param query
	 * @param k
	 * @return
	 */
	private static double grade(WordMatrix wordMatrix, int doc, HashSet<Integer> query, double k, boolean withDocLength) {
		double sum = 0;
		int docLength = (withDocLength)? wordMatrix.getDocLength(doc) : 0;

		for (int word : query) {

			double f = wordMatrix.getf(word, doc);
			double nj = wordMatrix.getnj(word);
			sum += (f / (k + docLength + f)) * Math.log10((wordMatrix.getN() - nj) / nj);
		}

		return sum;
	}


	/**
	 * Grades all documents given all queries (rows= queries, columns= docs)
	 * 
	 * @param docs
	 * @param queries
	 * @param k
	 * @return
	 */
	public static double[][] gradeAll(WordMatrix docs, WordMatrix queries, double k, boolean withDocLength) {

		double[][] documentScores = new double[queries.getN()][docs.getN()];
		for (int q = 1; q <= queries.getN(); q++) {
			for (int d = 1; d <= docs.getN(); d++) {
				int index_q = q-1;
				int index_d = d-1;
				documentScores[index_q][index_d] = grade(docs, d, queries.getBooleanDocument(q), k, withDocLength);
			}
		}
		return documentScores;
	}
}
