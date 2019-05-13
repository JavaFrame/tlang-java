package ninja.seppli.interpreter;

import java.util.Arrays;
import java.util.stream.Collectors;

import ninja.seppli.ast.AstNode;
import ninja.seppli.ast.expression.Expression;
import ninja.seppli.ast.expression.FunctionCallExpr;
import ninja.seppli.ast.expression.InfixExpr;
import ninja.seppli.ast.expression.IntegerExpr;
import ninja.seppli.ast.expression.PrefixExpr;
import ninja.seppli.ast.expression.StringExpr;
import ninja.seppli.ast.expression.VarExpr;
import ninja.seppli.ast.statement.ExpressionStmt;
import ninja.seppli.ast.statement.Program;
import ninja.seppli.ast.statement.Statement;
import ninja.seppli.ast.statement.VarAssignStmt;
import ninja.seppli.ast.statement.VarSetStmt;
import ninja.seppli.exception.ExceptionHandler;
import ninja.seppli.exception.InterpreterException;
import ninja.seppli.exception.RuntimeWrapperException;
import ninja.seppli.interpreter.representation.Environement;
import ninja.seppli.interpreter.representation.TFunction;
import ninja.seppli.interpreter.representation.TInt;
import ninja.seppli.interpreter.representation.TNull;
import ninja.seppli.interpreter.representation.TString;
import ninja.seppli.interpreter.representation.Value;

public class Interpreter {
	private Program program;
	private Environement environement;
	private ExceptionHandler handler;

	public Interpreter(Program program, Environement environement, ExceptionHandler handler) {
		this.program = program;
		this.environement = environement;
		this.handler = handler;
	}

	private void expectClass(String errorMsg, Value obj, Class<?>... expectedClass) {
		boolean matched = false;
		for (Class<?> cls : expectedClass) {
			if (!cls.isAssignableFrom(obj.getClass())) {
				matched = true;
			}
		}
		if (!matched) {
			errorMsg = errorMsg.replace("%got%", obj.getTypeName());
			errorMsg = errorMsg.replace("%expect%",
					Arrays.stream(expectedClass).map(Class::getSimpleName).collect(Collectors.joining(", ")));
			throw new RuntimeWrapperException(new InterpreterException(null, errorMsg));
		}
	}

	public Value execute() {
		Value lastValue = TNull.NULL;
		try {
			for (AstNode node : program.getNodes()) {
				if (node instanceof Statement) {
					lastValue = executeStatement((Statement) node);
				} else if (node instanceof Expression) {
					lastValue = executeExpression((Expression) node);
				} else {
					throw new IllegalStateException("Unkown subclass \"" + node.getClass().getName() + "\" of AstNode");
				}
			}
		} catch (RuntimeWrapperException e) {
			handler.error(e.getException());
		}
		return lastValue;
	}

	public Value executeStatement(Statement stmt) {
		if (stmt instanceof VarAssignStmt) {
			executeVarAssignStmt((VarAssignStmt) stmt);
		} else if (stmt instanceof VarSetStmt) {
			executeSetAssignStmt((VarSetStmt) stmt);
		} else if (stmt instanceof ExpressionStmt) {
			return executeExpressionStmt((ExpressionStmt) stmt);
		} else {
			throw new IllegalStateException("Unkown subclass \"" + stmt.getClass().getName() + "\" of Statement");
		}
		return TNull.NULL;
	}

	private void executeVarAssignStmt(VarAssignStmt stmt) {
		String name = stmt.getId().getString();
		if (environement.getScope().exists(name)) {
			throw new RuntimeWrapperException(
					new InterpreterException(stmt.getId().getAddress(), "Variable \"" + name + "\" already exists"));
		}
		Value value = executeExpression(stmt.getExpr());
		environement.getScope().set(stmt.getId().getString(), value);
	}

	private void executeSetAssignStmt(VarSetStmt stmt) {
		String name = stmt.getId().getString();
		if (!environement.getScope().exists(name)) {
			throw new RuntimeWrapperException(
					new InterpreterException(stmt.getId().getAddress(), "Unkown variable name \"" + name + "\""));
		}
		Value value = executeExpression(stmt.getExpr());
		environement.getScope().set(name, value);
	}

	private Value executeExpressionStmt(ExpressionStmt stmt) {
		return executeExpression(stmt.getExpr());
	}

	public Value executeExpression(Expression expr) {
		if (expr instanceof InfixExpr) {
			return executeInfixExpr((InfixExpr) expr);
		} else if (expr instanceof PrefixExpr) {
			return executePrefixExpr((PrefixExpr) expr);
		} else if (expr instanceof FunctionCallExpr) {
			return executeFunctionCallExpr((FunctionCallExpr) expr);
		} else if (expr instanceof VarExpr) {
			return executeVarExpr((VarExpr) expr);
		} else if (expr instanceof IntegerExpr) {
			return executeIntegerExpr((IntegerExpr) expr);
		} else if (expr instanceof StringExpr) {
			return executeStringExpr((StringExpr) expr);
		} else {
			throw new IllegalStateException("Unkown subclass \"" + expr.getClass().getName() + "\" of Expression");
		}
	}

	private Value executeInfixExpr(InfixExpr e) {
		Value v1 = executeExpression(e.getExpr1());
		Value v2 = executeExpression(e.getExpr2());
		TInt tint1;
		TInt tint2;
		switch (e.getOp()) {
		case ADD:
			if(v1 instanceof TInt && v2 instanceof TInt)  {
				tint1 = (TInt) v1;
				tint2 = (TInt) v2;
				return new TInt(tint1.getValue() + tint2.getValue());
			}
			return new TString(v1.convertToTString().getValue() + v2.convertToTString().getValue());
		case SUB:
			tint1 = (TInt) v1;
			tint2 = (TInt) v2;
			expectClass("Subtract only works with integers, got \"%got%\"", v1, TInt.class);
			expectClass("Subtract only works with integers, got \"%got%\"", v2, TInt.class);
			return new TInt(tint1.getValue() - tint2.getValue());
		default:
			throw new RuntimeWrapperException(
					new InterpreterException(null, "Unsupported prefix operation: " + e.getOp().getSign()));
		}
	}

	private Value executePrefixExpr(PrefixExpr e) {
		Value v = executeExpression(e.getExpr());
		if (!(v instanceof TInt)) {
			throw new RuntimeWrapperException(new InterpreterException(null, "The prefix operation "
					+ e.getOp().getSign() + " expects an integer, not an \"" + v.getTypeName() + "\""));
		}
		TInt tint = (TInt) v;
		switch (e.getOp()) {
		case ADD:
			return tint;
		case SUB:
			return new TInt(-tint.getValue());
		default:
			throw new RuntimeWrapperException(
					new InterpreterException(null, "Unsupported prefix operation: " + e.getOp().getSign()));
		}
	}

	private Value executeFunctionCallExpr(FunctionCallExpr expr) {
		Value function = getEnvironement().getScope().get(expr.getFunctionName().getString());
		if (function == null) {
			throw new RuntimeWrapperException(new InterpreterException(null,
					"The function \"" + expr.getFunctionName().getString() + "\" doesn't exist"));
		}
		if (!(function instanceof TFunction)) {
			throw new RuntimeWrapperException(
					new InterpreterException(null, "Cannot execute the type \"" + function.getTypeName() + "\""));
		}

		Expression[] argExprs = expr.getArgs();
		Value[] args = new Value[argExprs.length];
		for (int i = 0; i < argExprs.length; i++) {
			args[i] = executeExpression(argExprs[i]);
		}

		return ((TFunction) function).execute(args);
	}

	/**
	 * Returns the value from the current scope with the name of the var
	 *
	 * @param expr the var expression
	 * @return the value of the varexpression
	 */
	private Value executeVarExpr(VarExpr expr) {
		String name = expr.getId().getString();
		if (!environement.getScope().exists(name)) {
			throw new RuntimeWrapperException(
					new InterpreterException(expr.getId().getAddress(), "Unkown variable name \"" + name + "\""));
		}
		return environement.getScope().get(name);
	}

	/**
	 * converts an integer expression into a TInt
	 *
	 * @param expr the expression
	 * @return the value
	 */
	private Value executeIntegerExpr(IntegerExpr expr) {
		return new TInt(expr.getNumber());
	}

	private Value executeStringExpr(StringExpr expr) {
		return new TString(expr.getString());
	}

	public Program getProgram() {
		return program;
	}

	public Environement getEnvironement() {
		return environement;
	}

	public ExceptionHandler getHandler() {
		return handler;
	}
}
