package ninja.seppli.ast.expression;

/**
 * An expression for an integer
 * @author sebi
 *
 */
public class IntegerExpr implements Expression {
	/**
	 * the number
	 */
	private int number;

	/**
	 * constructor
	 * @param number the number which this expression represents
	 */
	public IntegerExpr(int number) {
		this.number = number;
	}

	/**
	 * returns the number of this expression
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	@Override
	public String getString() {
		return String.valueOf(getNumber());
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
		IntegerExpr other = (IntegerExpr) obj;
		if (number != other.number) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "IntegerExpr { number: " + getNumber() + " }";
	}

}
