package ninja.seppli.ast.expression;

import ninja.seppli.lexer.token.KeywordType;

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

	/**
	 * maps the given keyword type to a {@link MathOp}.
	 * If the type could't be matched then null is returned
	 * @param t the keywordtype
	 * @return the MathOp
	 */
	public static MathOp fromKeyword(KeywordType t)  {
		switch (t) {
		case PLUS:
			return ADD;
		case MINUS:
			return SUB;
		}
		return null;
	}
}
