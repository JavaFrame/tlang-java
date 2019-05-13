package ninja.seppli.ast.expression;

import java.util.Arrays;

import ninja.seppli.lexer.token.IdentifierToken;

public class FunctionCallExpr implements Expression {
	private IdentifierToken functionName;
	private Expression[] args;

	public FunctionCallExpr(IdentifierToken functionName, Expression[] args) {
		this.functionName = functionName;
		this.args = args;
	}

	public IdentifierToken getFunctionName() {
		return functionName;
	}

	public Expression[] getArgs() {
		return args;
	}

	@Override
	public String getString() {
		StringBuilder str = new StringBuilder();
		str.append(functionName.getString() + "(");
		for(int i = 0; i < getArgs().length; i++) {
			str.append(getArgs()[i].getString());
			if(i < getArgs().length - 1) {
				str.append(",");
			}
		}
		return str.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(args);
		result = prime * result + ((functionName == null) ? 0 : functionName.hashCode());
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
		FunctionCallExpr other = (FunctionCallExpr) obj;
		if (!Arrays.equals(args, other.args)) {
			return false;
		}
		if (functionName == null) {
			if (other.functionName != null) {
				return false;
			}
		} else if (!functionName.equals(other.functionName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "FunctionCallExpr { " + getString() + " }";
	}
}
