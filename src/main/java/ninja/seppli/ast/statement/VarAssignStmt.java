package ninja.seppli.ast.statement;

import ninja.seppli.ast.expression.Expression;
import ninja.seppli.lexer.token.IdentifierToken;

public class VarAssignStmt implements Statement {
	/**
	 * the id
	 */
	private IdentifierToken id;
	/**
	 * the expression
	 */
	private Expression expr;

	/**
	 * Constructor
	 * @param id the id
	 * @param expr the expression
	 */
	public VarAssignStmt(IdentifierToken id, Expression expr) {
		this.id = id;
		this.expr = expr;
	}

	/**
	 * the id of the assignment
	 * @return the id token
	 */
	public IdentifierToken getId() {
		return id;
	}

	/**
	 * the expresion which is set to the variable
	 * @return the expression
	 */
	public Expression getExpr() {
		return expr;
	}

	@Override
	public String getString() {
		return id.getString() + " := " + expr.getString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expr == null) ? 0 : expr.hashCode());
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
		VarAssignStmt other = (VarAssignStmt) obj;
		if (expr == null) {
			if (other.expr != null) {
				return false;
			}
		} else if (!expr.equals(other.expr)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "VarAssignStmt { " + getId() + " := " + getExpr() + " }";
	}

}
