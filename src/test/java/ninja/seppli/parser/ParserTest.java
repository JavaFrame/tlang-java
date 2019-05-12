package ninja.seppli.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ninja.seppli.ast.AstNode;
import ninja.seppli.ast.expression.InfixExpr;
import ninja.seppli.ast.expression.IntegerExpr;
import ninja.seppli.ast.expression.MathOp;
import ninja.seppli.ast.expression.PrefixExpr;
import ninja.seppli.ast.expression.VarExpr;
import ninja.seppli.ast.statement.Program;
import ninja.seppli.ast.statement.VarAssignStmt;
import ninja.seppli.ast.statement.VarSetStmt;
import ninja.seppli.exception.ExceptionHandler;
import ninja.seppli.lexer.Lexer;
import ninja.seppli.lexer.token.IdentifierToken;

public class ParserTest {
	private Logger logger = LogManager.getLogger();

	@Test
	public void basicTest() {
		Lexer l = new Lexer("i := 0; j := 0; i = 2;", "<junit test>");
		ExceptionHandler handler = new ExceptionHandler();
		Parser p = new Parser(l, handler);
		Program program = p.parse();
		if (handler.hasErrors()) {
			String msg = handler.printToString();
			logger.error(msg);
			Assertions.fail(msg);
		}
		Assertions
		.assertArrayEquals(
				new AstNode[] { new VarAssignStmt(new IdentifierToken("i", null), new IntegerExpr(0)),
						new VarAssignStmt(new IdentifierToken("j", null), new IntegerExpr(0)),
						new VarSetStmt(new IdentifierToken("i", null), new IntegerExpr(2)) },
				program.getNodes());
	}

	@Test
	public void basicMathTest() {
		Lexer l = new Lexer("i := 1 + j - 2;", "<junit test>");
		ExceptionHandler handler = new ExceptionHandler();
		Parser p = new Parser(l, handler);
		Program program = p.parse();
		if (handler.hasErrors()) {
			String msg = handler.printToString();
			logger.error(msg);
			Assertions.fail(msg);
		}
		Assertions.assertArrayEquals(
				new AstNode[] { new VarAssignStmt(new IdentifierToken("i", null),
						new InfixExpr(MathOp.SUB,
								new InfixExpr(MathOp.ADD, new IntegerExpr(1),
										new VarExpr(new IdentifierToken("j", null))),
								new IntegerExpr(2))) },
				program.getNodes());
	}

	@Test
	public void parenthesesMathTest() {
		Lexer l = new Lexer("i := 1 + (j - 2);", "<junit test>");
		ExceptionHandler handler = new ExceptionHandler();
		Parser p = new Parser(l, handler);
		Program program = p.parse();
		if (handler.hasErrors()) {
			String msg = handler.printToString();
			logger.error(msg);
			Assertions.fail(msg);
		}
		Assertions.assertArrayEquals(
				new AstNode[] { new VarAssignStmt(new IdentifierToken("i", null),
						new InfixExpr(MathOp.ADD, new IntegerExpr(1), new InfixExpr(MathOp.SUB,
								new VarExpr(new IdentifierToken("j", null)), new IntegerExpr(2)))) },
				program.getNodes());
	}

	@Test
	public void prefixMathTest() {
		Lexer l = new Lexer("i := -2; j := -j;", "<junit test>");
		ExceptionHandler handler = new ExceptionHandler();
		Parser p = new Parser(l, handler);
		Program program = p.parse();
		if (handler.hasErrors()) {
			String msg = handler.printToString();
			logger.error(msg);
			Assertions.fail(msg);
		}
		Assertions.assertArrayEquals(
				new AstNode[] {
						new VarAssignStmt(new IdentifierToken("i", null),
								new PrefixExpr(MathOp.SUB, new IntegerExpr(2))),
						new VarAssignStmt(new IdentifierToken("j", null),
								new PrefixExpr(MathOp.SUB, new VarExpr(new IdentifierToken("j", null)))) },
				program.getNodes());
	}
}
