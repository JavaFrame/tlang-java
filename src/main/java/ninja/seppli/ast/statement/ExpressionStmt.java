package ninja.seppli.ast.statement;

import ninja.seppli.ast.expression.Expression;

public class ExpressionStmt implements Statement {
	private Expression expr;

	public ExpressionStmt(Expression expr) {
		this.expr = expr;
	}

	public Expression getExpr() {
		return expr;
	}

	@Override
	public String getString() {
		return getExpr().getString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expr == null) ? 0 : expr.hashCode());
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
		ExpressionStmt other = (ExpressionStmt) obj;
		if (expr == null) {
			if (other.expr != null) {
				return false;
			}
		} else if (!expr.equals(other.expr)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ExpressionStmt { " + expr.toString() + " }";
	}

}
