package ninja.seppli.ast.expression;

/**
 * the math operation
 * @author sebi
 *
 */
public enum MathOp {
	/**
	 * the add operation
	 */
	ADD("+"),

	/**
	 * the subtraction operation
	 */
	SUB("-");

	/**
	 * the sign/representation of the operation
	 */
	private String sign;

	/**
	 * constructor
	 * @param sign the representation
	 */
	private MathOp(String sign) {
		this.sign = sign;
	}


	/**
	 * Returns the representation of the operation
	 * @return the  sign
	 */
	public String getSign() {
		return sign;
	}

}
