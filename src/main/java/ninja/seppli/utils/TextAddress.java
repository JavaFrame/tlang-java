package ninja.seppli.utils;

/**
 * a text address
 * @author sebi
 *
 */
public class TextAddress {
	/**
	 * the line number
	 */
	private int line;
	/**
	 * the character position in the line
	 */
	private int character;
	/**
	 * the file name/path
	 */
	private String filePath;
	/**
	 * the text in which the text address is valid
	 */
	private String text;

	/**
	 * Constructor
	 * @param line the line number
	 * @param character the character position
	 * @param filePath the file path
	 * @param text the text
	 */
	public TextAddress(int line, int character, String filePath, String text) {
		this.line = line;
		this.character = character;
		this.filePath = filePath;
		this.text = text;
	}

	/**
	 * the line number
	 * @return the line number
	 */
	public int getLine() {
		return line;
	}

	/**
	 * the characrer in the line
	 * @return the character
	 */
	public int getCharacter() {
		return character;
	}

	/**
	 * the file path
	 * @return the file path
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * returns the text
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + character;
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + line;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TextAddress other = (TextAddress) obj;
		if (character != other.character) {
			return false;
		}
		if (filePath == null) {
			if (other.filePath != null) {
				return false;
			}
		} else if (!filePath.equals(other.filePath)) {
			return false;
		}
		if (line != other.line) {
			return false;
		}
		if (text == null) {
			if (other.text != null) {
				return false;
			}
		} else if (!text.equals(other.text)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getLine() + ":" + getCharacter() + " ("  + getFilePath() + ")";
	}


}
