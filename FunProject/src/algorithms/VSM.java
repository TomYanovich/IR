package algorithms;
import java.lang.Math;
import java.util.HashSet;

import dataStructures.WordMatrix;

public class VSM {
	final static int N = 1398;

	/**
	 * 
	 * @param mtx
	 * @param w
	 * @param d
	 * @return
	 */
	public static double tfIdf(WordMatrix matrix, int doc, int word) {
		int f = matrix.getf(word, doc);
		int nj = matrix.getnj(word);
		return (((f!=0)?Math.log(f):0) + 1) * (Math.log(N / nj));
	}

	public static double grade(WordMatrix docs, int doc, WordMatrix queries,
			int query) {
		double tfidf;
		double sumD = 0;
		double sumDpow = 0;
		HashSet<Integer> docWords = queries.getBooleanDocument(query);
		
		for (Integer word : docWords) {
			tfidf = tfIdf(docs, doc, word);
			sumD += tfidf;
			sumDpow += Math.pow(tfidf, 2);

		}
		double sumQ = 0;
		double sumQpow = 0;
		HashSet<Integer> queryWords = queries.getBooleanDocument(query);
		
		for (Integer word : queryWords) {
			tfidf = tfIdf(queries, query, word);
			sumQ += tfidf;
			sumQpow += Math.pow(tfidf, 2);
		}

		double lngtD = Math.sqrt(sumDpow);
		double lngtQ = Math.sqrt(sumQpow);
		return sumD * sumQ / (lngtD * lngtQ);
	}

	public static double[][] gradeAll(WordMatrix docs, WordMatrix queries) {
		double[][] documentScores = new double[queries.getN()][docs.getN()];
		for (int q = 0; q < queries.getN(); q++) {
			for (int d = 0; d < docs.getN(); d++) {
				documentScores[q][d] = grade(docs, d+1, queries, q+1);
			}
		}
		return documentScores;
	}

}