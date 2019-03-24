package ninja.seppli.lexer;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Lexer {
	private Logger logger = LogManager.getLogger();
	private InputStream in;
	private char current;
	private char next;

	public Lexer(InputStream in) {
		this.in = in;
	}
	
	protected void read() {
		current = next;
		try {
			next = (char) in.read();
		} catch (IOException e) {
		}
	}
	
	protected char getNext() {
		return next;
	}
	
	protected char getCurrent() {
		return current;
	}
}
