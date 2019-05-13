package ninja.seppli.lexer.token;

import ninja.seppli.utils.TextAddress;

public class StringToken implements Token {
	private String text;
	private TextAddress address;
	private boolean error;

	public StringToken(String text, TextAddress address) {
		this.text = text;
		this.address = address;
	}

	@Override
	public String getString() {
		return text;
	}

	@Override
	public TextAddress getAddress() {
		return address;
	}

	public StringToken markError() {
		error = true;
		return this;
	}

	@Override
	public boolean hasError() {
		return error;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + (error ? 1231 : 1237);
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
		StringToken other = (StringToken) obj;
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
		return "StringToken { " + getString() + " }";
	}

}
