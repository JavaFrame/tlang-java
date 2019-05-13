package ninja.seppli.lexer;

import static ninja.seppli.lexer.token.KeywordType.EOF;

import java.io.EOFException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ninja.seppli.lexer.token.IdentifierToken;
import ninja.seppli.lexer.token.IntegerToken;
import ninja.seppli.lexer.token.KeywordToken;
import ninja.seppli.lexer.token.KeywordType;
import ninja.seppli.lexer.token.StringToken;
import ninja.seppli.lexer.token.Token;
import ninja.seppli.utils.TextAddress;

/**
 * The lexer of tlang
 * @author sebi
 *
 */
public class Lexer {
	/**
	 * logger
	 */
	private Logger logger = LogManager.getLogger();

	/**
	 * the input string
	 */
	private String inputStr;
	/**
	 * the input string as a char array
	 */
	private char[] input;
	/**
	 * the position of next character (returned by {@link #getNext()})
	 */
	private int nextPos;

	/**
	 * the current linen of the next character ({@link #getNext()})
	 */
	private int currentLine;
	/**
	 * the current position of the character in the {@link #currentLine}
	 */
	private int currentCharacter;

	/**
	 * the path to where input was read
	 */
	private String filePath;

	/**
	 * Constructor
	 * @param input the input string
	 * @param filepath the path form where the string was read
	 */
	public Lexer(String input, String filepath) {
		this.inputStr = input;
		this.input = input.toCharArray();
		this.filePath = filepath;

		this.nextPos = 1;
	}

	/**
	 * advances the current/next character by one and adjusts the
	 * {@link #currentLine} and {@link #currentCharacter}.<br>
	 *
	 * If the end of the input string is reached, then no exception will be thrown.
	 * This will happend if {@link #getCurrent()} or {@link #getNext()} is called in this state
	 */
	protected void read() {
		try {
			if (getCurrent() == '\n') {
				currentLine++;
				currentCharacter = 0;
			} else {
				currentCharacter++;
			}
		} catch (EOFException e) {
			// EOF found -> no line number increase
			// EOFException is thrown in the getNext() and getCurrent()
		}
		nextPos++;
	}

	/**
	 * Returns the next character to which {@link #nextPos} points
	 * @return the next character
	 * @throws EOFException
	 */
	private char getNext() throws EOFException {
		if (input.length <= getNextPos()) {
			throw new EOFException();
		}
		return input[getNextPos()];
	}

	/**
	 * returns the current character (it is {@link #nextPos} - 1)
	 * @return the current character
	 * @throws EOFException
	 */
	private char getCurrent() throws EOFException {
		if (input.length <= getNextPos() - 1) {
			throw new EOFException();
		}
		return input[getNextPos() - 1];
	}

	/**
	 * returns the position of the next character in the input string
	 * @return the position
	 */
	public int getNextPos() {
		return nextPos;
	}

	/**
	 * returns the current line of the next character
	 * @return the line
	 */
	public int getCurrentLine() {
		return currentLine;
	}

	/**
	 * returns the current character of this next character
	 * @return the position in the input string
	 */
	public int getCurrentCharacter() {
		return currentCharacter;
	}

	/**
	 * returns the file path of the input string
	 * @return the file path
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Creates and returns a {@link TextAddress} of the next character
	 * @return the text address
	 */
	private TextAddress getAddress() {
		return new TextAddress(getCurrentLine(), getCurrentCharacter(), getFilePath(), inputStr);
	}

	/**
	 * reads until the current character ({@link #getCurrent()}) is not a whitespace anymore
	 * @throws EOFException
	 */
	private void skipWhitespaces() throws EOFException {
		while (Character.isWhitespace(getCurrent())) {
			read();
		}
	}

	public Token getNextToken() {
		try {
			skipWhitespaces();

			// if the current character was matched to a keywordToken -> return the token
			KeywordToken keywordToken = KeywordToken.getKeywordToken(getCurrent(), getAddress());
			if(KeywordType.QUOTE.equals(KeywordType.getKeywordType(keywordToken))) {
				return readString();
			}
			if (keywordToken != null) {
				read();
				return keywordToken;
			}

			//read a number token if the current character is a digit
			if (Character.isDigit(getCurrent())) {
				return readNumber();
			}
			return readIdentifier();
		} catch (EOFException e) {
			return new KeywordToken(EOF, getAddress());
		}
	}

	/**
	 * Tries to read until the current character isn't a digit anymore and
	 * converts the read input to a {@link IntegerToken}
	 * @return the integer token
	 * @throws EOFException throws an eof exception only if no digit was read
	 */
	private IntegerToken readNumber() throws EOFException {
		String numberStr = "";
		try {
			while (Character.isDigit(getCurrent())) {
				numberStr += getCurrent();
				read();
			}
		} catch (EOFException ex) {
			//only throws the eof exception if the number string is empty, else a
			// IntegerToken should be returned
			if(numberStr.isEmpty()) {
				throw ex;
			}
		}
		int number = Integer.parseInt(numberStr);
		return new IntegerToken(number, getAddress());
	}

	/**
	 * Tries to read until the current character isn't a digit anymore and
	 * converts the read input to a {@link IntegerToken}
	 * @return the integer token
	 * @throws EOFException throws an eof exception only if no digit was read
	 */
	private StringToken readString() throws EOFException {
		//skip " character


		String str = "";
		try {
			while(getNext() != '"' || (getNext() == '\\' && getCurrent() == '\\')) {
				char next = getNext();
				if(getCurrent() == '\\') {
					switch (next) {
					case 'n':
						next = '\n';
						break;
					case 't':
						next = '\t';
						break;
					case '\\':
						next = '\\';
						break;
					default:
						str += '\\';
						break;
					}
				}
				if(getNext() != '\\') {
					str += next;
				}
				read();
			}
			read();
			read();
		} catch (EOFException ex) {
			//only throws the eof exception if the number string is empty, else a
			// IntegerToken should be returned
			if(str.isEmpty()) {
				throw ex;
			}
		}
		return new StringToken(str, getAddress());
	}


	/**
	 * Tries to read until the current character is a whitespace or an eof exception occured and
	 * converts the read input to a {@link IntegerToken}
	 * @return the identifier token
	 * @throws EOFException throws an eof exception only if no digit was read
	 */
	private IdentifierToken readIdentifier() throws EOFException {
		String idStr = "";
		try {
			idStr += getCurrent();
			read();
			while (Character.isLetterOrDigit(getCurrent())) {
				idStr += getCurrent();
				read();
			}
		} catch(EOFException ex) {
			if(idStr.isEmpty()) {
				throw ex;
			}
		}
		return new IdentifierToken(idStr, getAddress());
	}
}
