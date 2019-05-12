package ninja.seppli.lexer.token;

import ninja.seppli.utils.TextAddress;

public class IntegerToken implements Token {
	private int number;
	private TextAddress address;
	private boolean error = false;

	public IntegerToken(int number, TextAddress address) {
		this.number = number;
		this.address = address;
	}

	public int getNumber() {
		return number;
	}

	public IntegerToken markError() {
		this.error = true;
		return this;
	}

	@Override
	public boolean hasError() {
		return error;
	}

	@Override
	public String getString() {
		return getNumber()  + "";
	}

	@Override
	public TextAddress getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "IntegerToken { " + getAddress() + " "+ getNumber() + " }";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
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
		IntegerToken other = (IntegerToken) obj;
		if (number != other.number) {
			return false;
		}
		return true;
	}


}
