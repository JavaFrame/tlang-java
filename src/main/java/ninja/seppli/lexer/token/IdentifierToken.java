package ninja.seppli.lexer.token;

import ninja.seppli.utils.TextAddress;

public class IdentifierToken implements Token {

	private String id;
	private TextAddress address;

	public IdentifierToken(String id, TextAddress address) {
		this.id = id;
		this.address = address;
	}

	@Override
	public String getString() {
		return id;
	}

	@Override
	public TextAddress getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "IdentifierToken { " + getString() + " }";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		IdentifierToken other = (IdentifierToken) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}





}
