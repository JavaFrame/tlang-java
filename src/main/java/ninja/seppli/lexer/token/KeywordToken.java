package ninja.seppli.lexer.token;

import ninja.seppli.utils.TextAddress;

public class KeywordToken implements Token {
	private KeywordType type;
	private TextAddress address;

	public KeywordToken(KeywordType type, TextAddress address) {
		this.type = type;
		this.address = address;
	}

	public KeywordType getType() {
		return type;
	}

	@Override
	public String getString() {
		return type.getString();
	}

	@Override
	public TextAddress getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "KeywordToken { " + getAddress() + " " + getString()  + " }";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		KeywordToken other = (KeywordToken) obj;
		if (type != other.type) {
			return false;
		}
		return true;
	}

	public static KeywordToken getKeywordToken(char c, TextAddress address) {
		KeywordType type = KeywordType.getKeywordType(c);
		if(type == null) {
			return null;
		}
		return new KeywordToken(type, address);
	}
}
