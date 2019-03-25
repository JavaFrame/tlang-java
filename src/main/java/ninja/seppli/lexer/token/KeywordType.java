package ninja.seppli.lexer.token;

public enum KeywordType {
	EQAUL("="),
	COLON(":"),
	PLUS("+"),
	MINUS("-"),
	SEMICOLON(";"),
	EOF("<EOF>")
	;

	private String str;

	private KeywordType(String str) {
		this.str = str;
	}

	public String getString() {
		return str;
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
}
