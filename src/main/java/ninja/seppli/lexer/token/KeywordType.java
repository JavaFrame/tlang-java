package ninja.seppli.lexer.token;

import static ninja.seppli.lexer.token.Precedence.ADDSUB;
import static ninja.seppli.lexer.token.Precedence.FUNCTION_CALL;
import static ninja.seppli.lexer.token.Precedence.NONE;

import ninja.seppli.lexer.token.Precedence.HasPrecedence;

public enum KeywordType implements HasPrecedence {
	EQUAL("="),
	COLON(":"),
	PLUS("+", ADDSUB),
	MINUS("-", ADDSUB),
	OPENING_PARENTHESES("(", FUNCTION_CALL),
	CLOSING_PARENTHESES(")"),
	SEMICOLON(";"),
	COMMA(","),
	QUOTE("\""),
	EOF("<EOF>"),
	ERROR("<ERROR>")
	;

	private String str;
	private Precedence precedence;

	private KeywordType(String str) {
		this(str, NONE);
	}

	private KeywordType(String str, Precedence precedence) {
		this.str = str;
		this.precedence = precedence;
	}

	public String getString() {
		return str;
	}

	@Override
	public Precedence getPrecedence() {
		return precedence;
	}

	@Override
	public String toString() {
		return "KeywordType { " + getString()  + " }";
	}


	public static KeywordType getKeywordType(char c) {
		for(KeywordType t : KeywordType.values()) {
			String str = t.getString();
			if(str.length() > 0 && str.charAt(0) == c) {
				return t;
			}
		}
		return null;
	}

	public static KeywordType getKeywordType(Token t) {
		if(t instanceof KeywordToken) {
			return ((KeywordToken) t).getType();
		}
		return null;
	}
}
