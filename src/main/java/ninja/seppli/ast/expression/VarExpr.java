package ninja.seppli.ast.expression;

import ninja.seppli.lexer.token.IdentifierToken;

public class VarExpr implements Expression {
	private IdentifierToken id;

	public VarExpr(IdentifierToken id) {
		this.id = id;
	}

	public IdentifierToken getId() {
		return id;
	}

	@Override
	public String getString() {
		return id.getString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		VarExpr other = (VarExpr) obj;
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
		return "VarExpr { " + getId().getString() + " }";
	}
}
