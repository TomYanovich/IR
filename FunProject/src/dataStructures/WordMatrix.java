package dataStructures;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class WordMatrix {
	/*
	 * rows = word, columns = document, values = frequencies
	 */
	private int[][] wordMatrix;
	private PatriciaTrie<Integer, Integer> wm;
	/*
	 * index = document, value = document length
	 */
	private int[] docLengths;
	/*
	 * index = word, value = nj = num of docs the word appears in
	 */
	private int[] njArray;
	/*
	 * 
	 */
	private int[] wordFrequencies;
	/**
	 * words total number of words document = total number of documents
	 * 
	 * @param words
	 * @param documents
	 */
	public WordMatrix(int words, int documents) {
		super();
		wordMatrix = new int[words][documents];
		docLengths = new int[documents];
		njArray = new int[words];
		wordFrequencies = new int[words];
	}

	public int[][] getWordMatrix() {
		return wordMatrix;
	}

	// public void setWordMatrix(int[][] wordMatrix) {
	// this.wordMatrix = wordMatrix;
	// }

	/**
	 * Inserts a word frequency in a document to the word matrix word = the id
	 * of the word document = the id of the document f = the frequency of the
	 * word in the document
	 * 
	 * @param word
	 *            - the id of the word
	 * @param document
	 *            - the id of the document
	 * @param f
	 *            - the frequency of the word in the document
	 */
	public void insert(int word, int document, int f) {
		wordMatrix[word - 1][document - 1] = f;
		docLengths[document - 1] += f;
		njArray[word - 1] ++;
		wordFrequencies[word-1]+=f;
	}

	/**
	 * Returns the word IDs in the document (no duplicates)
	 * 
	 * @param doc
	 *            the id of the document
	 * @return - the words IDs in the document (no duplicates)
	 */
	public HashSet<Integer> getBooleanDocument(int doc) {
		HashSet<Integer> booleanDocument = new HashSet<>();
		for (int word_index = 0; word_index < wordMatrix.length; word_index++) {
			if (wordMatrix[word_index][doc-1] > 0){
				booleanDocument.add(word_index+1);
			}
		}
		return booleanDocument;
	}

	/**
	 * Returns the number of document that contain the word
	 * 
	 * @param word
	 *            the id of the word
	 * @return - the number of document that contain the word
	 */
	public int getnj(int word) {
		return njArray[word - 1];
	}

	/**
	 * 
	 * @return Returns the number of documents
	 */
	public int getN() {
		return wordMatrix[0].length;
	}

	/**
	 * Returns the length (number of words) of the document
	 * 
	 * @param document
	 *            id
	 * @return
	 */
	public int getDocLength(int document) {
		return docLengths[document - 1];
	}

	/**
	 * Returns the frequency of the word in the document
	 * 
	 * @param word
	 *            id
	 * @param document
	 *            id
	 * @return
	 */
	public int getf(int word, int document) {
		return wordMatrix[word - 1][document - 1];
	}

	/**
	 * Returns the probability of a word in a document for LM
	 * 
	 * @param word
	 *            id
	 * @param doc
	 *            id
	 * @return
	 */
	public double getProbability(int word, int doc) {
		return (double)getf(word, doc) / getDocLength(doc);
	}

	/**
	 * Returns the probability of a word in all documents for LM
	 * 
	 * @param word
	 *            id
	 * @return Returns the probability of a word in all documents for LM
	 */
	public double getProbability(int word) {
		return (double)getWordFrequency(word) / getTotalNumOfWords();
	}

	/**
	 * Returns the word frequency in all documents for LM
	 * 
	 * @param word
	 *            id
	 * @return Returns the word frequency in all documents for LM
	 */
	public int getWordFrequency(int word) {
		return wordFrequencies[word-1];
	}

	/**
	 * Returns the total number of words in all documents for LM
	 * 
	 * @return Returns the total number of words in all documents for LM
	 */
	public int getTotalNumOfWords() {
		return wordMatrix.length;
	}

	
	/**
	 * calculate the wight of the
	 * 
	 * @param mtx
	 * @param word
	 * @param d
	 * @return
	 */
	public double tfIdf(int word, int d) {
		int f = getf(word, d);
		int nj = getnj(word);
		return (Math.log(f) + 1) * (Math.log(getN() / nj));
	}
	
	public WordMatrix (int words,
			String fileName) throws FileNotFoundException, IOException {
		HashSet<Record> records = new HashSet<Record>();
		HashSet<Integer> columns = new HashSet<Integer>();

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());

				String[] arguments = line.split(" ");
				int word = Integer.valueOf(arguments[0]);
				int document = Integer.valueOf(arguments[1]);
				int frequency = Integer.valueOf(arguments[2]);
				columns.add(document);
				records.add(new Record(word, document, frequency));

				line = br.readLine();
			}


			int docs = columns.size();
			wordMatrix = new int[words][docs];
			docLengths = new int[docs];
			njArray = new int[words];
			wordFrequencies = new int[words];
			
			
			for (Record r : records) {
				// inserts records to the data structures
				int word = r.getWord();
				int document =  r.getDocument();
				int f= r.getFrequency();
				wordMatrix[word - 1][document - 1] = f;
				docLengths[document - 1] += f;
				njArray[word - 1] ++;
				wordFrequencies[word-1]+=f;
			}
		}
	}
}
