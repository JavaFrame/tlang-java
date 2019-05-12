package ninja.seppli.ast.expression;

/**
 * an infix expression (like 1 + 1)
 *
 * @author sebi
 *
 */
public class InfixExpr implements Expression {
	/**
	 * the operation
	 */
	private MathOp op;
	/**
	 * the first expression on the left side
	 */
	private Expression expr1;
	/**
	 * the second expression on the right side
	 */
	private Expression expr2;

	/**
	 * Constructor
	 *
	 * @param op    the operation
	 * @param expr1 the left expression
	 * @param expr2 the right expression
	 */
	public InfixExpr(MathOp op, Expression expr1, Expression expr2) {
		this.op = op;
		this.expr1 = expr1;
		this.expr2 = expr2;
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
	public Expression getExpr1() {
		return expr1;
	}

	/**
	 * the reight side expression
	 *
	 * @return the right expression
	 */
	public Expression getExpr2() {
		return expr2;
	}

	@Override
	public String getString() {
		return "(" + expr1.getString() + " " + op.getSign() + " " + expr2.getString() + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expr1 == null) ? 0 : expr1.hashCode());
		result = prime * result + ((expr2 == null) ? 0 : expr2.hashCode());
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
		InfixExpr other = (InfixExpr) obj;
		if (expr1 == null) {
			if (other.expr1 != null) {
				return false;
			}
		} else if (!expr1.equals(other.expr1)) {
			return false;
		}
		if (expr2 == null) {
			if (other.expr2 != null) {
				return false;
			}
		} else if (!expr2.equals(other.expr2)) {
			return false;
		}
		if (op != other.op) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "InfixExpr { " + expr1 + " " + op.getSign() + " " + expr2 + " }";
	}
}
