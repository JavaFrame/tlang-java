package ninja.seppli.lexer.token;

public enum Precedence {
	NONE(0),
	FUNCTION_CALL(1),
	ADDSUB(2);

	private int precedence;

	private Precedence(int precedence) {
		this.precedence = precedence;
	}

	public int getPrecedence() {
		return precedence;
	}

	public static Precedence getPrecedenceOf(Object t) {
		if(t instanceof HasPrecedence) {
			return ((HasPrecedence) t).getPrecedence();
		}
		return NONE;
	}

	/**
	 * An interface which marks an class/interface as an object which has
	 * an precedence and thus the precendece can be retrieved using {@link Precedence#getPrecedence()}
	 * @author sebi
	 *
	 */
	public static interface HasPrecedence {
		/**
		 * Returns the precedence of this object
		 * @return the precedence
		 */
		Precedence getPrecedence();
	}
}
