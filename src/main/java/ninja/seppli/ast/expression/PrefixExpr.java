package ninja.seppli.ast.expression;

/**
 * an infix expression (like 1 + 1)
 *
 * @author sebi
 *
 */
public class PrefixExpr implements Expression {
	/**
	 * the operation
	 */
	private MathOp op;
	/**
	 * the expression
	 */
	private Expression expr;

	/**
	 * Constructor
	 *
	 * @param op    the operation
	 * @param expr1 the expression
	 */
	public PrefixExpr(MathOp op, Expression expr) {
		this.op = op;
		this.expr = expr;
	}

	/**
	 * the operation of the expression
	 *
	 * @return the operation
	 */
	public MathOp getOp() {
		return op;
	}

	/**
	 * the left side expression
	 *
	 * @return the left expression
	 */
	public Expression getExpr() {
		return expr;
	}


	@Override
	public String getString() {
		return "(" + op.getSign() + " " + expr.getString() + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expr == null) ? 0 : expr.hashCode());
		result = prime * result + ((op == null) ? 0 : op.hashCode());
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
		PrefixExpr other = (PrefixExpr) obj;
		if (expr == null) {
			if (other.expr != null) {
				return false;
			}
		} else if (!expr.equals(other.expr)) {
			return false;
		}
		if (op != other.op) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PrefixExpr { " + getOp().getSign() + " " + expr + " }";
	}
}
