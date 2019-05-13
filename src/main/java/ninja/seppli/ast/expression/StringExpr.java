package ninja.seppli.ast.expression;

/**
 * An expression for an integer
 *
 * @author sebi
 *
 */
public class StringExpr implements Expression {
	/**
	 * the number
	 */
	private String value;

	/**
	 * constructor
	 *
	 * @param number the number which this expression represents
	 */
	public StringExpr(String value) {
		this.value = value;
	}

	/**
	 * returns the number of this expression
	 *
	 * @return the number
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String getString() {
		return getValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		StringExpr other = (StringExpr) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "StringExpr {  " + getValue() + " }";
	}

}
