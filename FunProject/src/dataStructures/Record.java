package dataStructures;

public class Record {
	private int word;
	private int document;
	private int frequency;
	/**
	 * @param word
	 * @param document
	 * @param frequency
	 */
	public Record(int word, int document, int frequency) {
		super();
		this.word = word;
		this.document = document;
		this.frequency = frequency;
	}
	/**
	 * @return the word
	 */
	int getWord() {
		return word;
	}
	/**
	 * @return the document
	 */
	int getDocument() {
		return document;
	}
	/**
	 * @return the frequency
	 */
	int getFrequency() {
		return frequency;
	}
}
