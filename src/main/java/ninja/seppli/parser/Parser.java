package ninja.seppli.parser;

import java.util.Arrays;
import java.util.stream.Collectors;

import ninja.seppli.ast.expression.Expression;
import ninja.seppli.ast.expression.InfixExpr;
import ninja.seppli.ast.expression.IntegerExpr;
import ninja.seppli.ast.expression.MathOp;
import ninja.seppli.ast.expression.PrefixExpr;
import ninja.seppli.ast.expression.VarExpr;
import ninja.seppli.ast.statement.ExpressionStmt;
import ninja.seppli.ast.statement.Program;
import ninja.seppli.ast.statement.Statement;
import ninja.seppli.ast.statement.VarAssignStmt;
import ninja.seppli.ast.statement.VarSetStmt;
import ninja.seppli.exception.ExceptionHandler;
import ninja.seppli.exception.ParserException;
import ninja.seppli.exception.RuntimeWrapperException;
import ninja.seppli.lexer.Lexer;
import ninja.seppli.lexer.token.IdentifierToken;
import ninja.seppli.lexer.token.IntegerToken;
import ninja.seppli.lexer.token.KeywordToken;
import ninja.seppli.lexer.token.KeywordType;
import ninja.seppli.lexer.token.Precedence;
import ninja.seppli.lexer.token.Token;

public class Parser {
	/**
	 * the lexer
	 */
	private Lexer lexer;
	/**
	 * the current token
	 */
	private Token current;
	/**
	 * the token afterthe current token
	 */
	private Token next;

	/**
	 * the exception handler
	 */
	private ExceptionHandler handler;

	/**
	 * constructor
	 *
	 * @param lexer the lexer
	 */
	public Parser(Lexer lexer, ExceptionHandler handler) {
		this.lexer = lexer;
		this.handler = handler;
	}

	/**
	 * Returns the next token
	 */
	private void nextToken() {
		current = next;
		next = lexer.getNextToken();
	}

	/**
	 * returns the current token
	 *
	 * @return the current token
	 */
	private Token getCurrent() {
		return current;
	}

	/**
	 * the token after the current token
	 *
	 * @return the next token
	 */
	private Token getNext() {
		return next;
	}

	/**
	 * the lexer
	 *
	 * @return the lexer
	 */
	public Lexer getLexer() {
		return lexer;
	}

	/**
	 * Checks if the input token is equal to one of the expected token. If not then
	 * an error message is thrown and false returned, else true is returned
	 *
	 * @param input    the input token (should be either the current or the next
	 *                 token)
	 * @param expected the expected tokens
	 * @return how the check evaluated
	 */
	private boolean expectType(Token input, Class<?>... expected) {
		boolean result = Arrays.stream(expected).anyMatch(e -> e.isAssignableFrom(input.getClass()));
		if (!result) {
			String expectedStr = Arrays.stream(expected).map(Class::getSimpleName)
					.collect(Collectors.joining("\", \""));
			resync();
			throw new RuntimeWrapperException(new ParserException(input.getAddress(),
					"Unexpected Token \"" + input.getString() + "\", expected \"" + expectedStr + " \""));
		}
		return true;
	}

	/**
	 * Checks if the current token is equal to one of the given token. If non of the
	 * given token was equal then an error message logged
	 *
	 * @param expected the expected tokens
	 * @return if one was equal or not
	 */
	private boolean expectTypeCurrent(Class<?>... expected) {
		return expectType(getCurrent(), expected);
	}

	/**
	 * Checks if the next token is equal to one of the given token. If non of the
	 * given token was equal then an error message logged
	 *
	 * @param expected the expected tokens
	 * @return if one was equal or not
	 */
	private boolean expectTypeNext(Class<?>... expected) {
		return expectType(getNext(), expected);
	}

	/**
	 * Checks if the input token is an instance of a Keyword token and the type in
	 * the token is equal to one of the given keyword type. If not then an error
	 * message is thrown and false returned, else true is returned
	 *
	 * @param input    the input token (should be either the current or the next
	 *                 token)
	 * @param expected the expected token types
	 * @return how the check evaluated
	 */
	private boolean expect(Token input, KeywordType... expected) {
		String expectedStr = Arrays.stream(expected).map(KeywordType::getString).collect(Collectors.joining("\", \""));
		// check if given token is a KeywordToken
		if (!(input instanceof KeywordToken)) {
			resync();
			throw new RuntimeWrapperException(new ParserException(input.getAddress(),
					"Unexpected Token \"" + input.getString() + "\", expected \"" + expectedStr + " \""));
		}

		KeywordType type = ((KeywordToken) input).getType();
		boolean result = Arrays.stream(expected).anyMatch(e -> type.equals(e));
		if (!result) {
			resync();
			throw new RuntimeWrapperException(new ParserException(input.getAddress(),
					"Unexpected Token \"" + type.getString() + "\", expected \"" + expectedStr + " \""));
		}
		return result;
	}

	/**
	 * Checks if the current token is an instance of a Keyword token and the type in
	 * the token is equal to one of the given keyword type. If not then an error
	 * message is thrown and false returned, else true is returned
	 *
	 * @param expected the expected tokens
	 * @return if one was equal or not
	 */
	private boolean expectCurrent(KeywordType... expected) {
		return expect(getCurrent(), expected);
	}

	/**
	 * Checks if the next token is an instance of a Keyword token and the type in
	 * the token is equal to one of the given keyword type. If not then an error
	 * message is thrown and false returned, else true is returned
	 *
	 * @param expected the expected tokens
	 * @return if one was equal or not
	 */
	private boolean expectNext(KeywordType... expected) {
		return expect(getNext(), expected);
	}

	/**
	 * tries to resynchronizes with the token stream by skipping tokens until a
	 * semicolon (;) is found
	 */
	private void resync() {
		do {
			nextToken();
		} while (KeywordType.getKeywordType(getCurrent()) != KeywordType.SEMICOLON
				&& KeywordType.getKeywordType(getCurrent()) != KeywordType.EOF);
	}

	/**
	 * starts parsing the tokens recieved from the lexer
	 *
	 * @return the parsed program
	 */
	public Program parse() {
		nextToken();
		nextToken();
		Program program = new Program();
		Statement stmt = null;
		do {
			try {
				stmt = parseStmt();
				program.add(stmt);
			} catch (RuntimeWrapperException e) {
				getHandler().error(e.getException());
				// if we are at the end of the input stream then break the loop
				if (KeywordType.getKeywordType(getCurrent()) == KeywordType.EOF) {
					break;
				}
				continue;
			}
		} while (stmt != null && KeywordType.getKeywordType(getCurrent()) != KeywordType.EOF);

		return program;
	}

	/**
	 * parses one statement
	 *
	 * @return the statement
	 */
	private Statement parseStmt() {
		Token t = getCurrent();
		expectTypeCurrent(IdentifierToken.class);
		expectTypeNext(KeywordToken.class);
		KeywordToken next = (KeywordToken) getNext();
		Statement stmt = null;
		if (KeywordType.EQUAL.equals(next.getType())) {
			stmt = parseVarSetStmt();
		} else if (KeywordType.COLON.equals(next.getType())) {
			stmt = parseVarAssignStmt();
		} else {
			stmt = parseExpressionStmt();
		}
		expectCurrent(KeywordType.SEMICOLON);
		nextToken();
		return stmt;
	}

	/**
	 * parses a var assignment (the creation of a new variable). It expects the
	 * current token to be on the identifier.<br>
	 *
	 * the syntax is as following: &lt;var name&gt; := &lt;expr&gt;
	 *
	 * @return the var assignment
	 */
	private VarAssignStmt parseVarAssignStmt() {
		IdentifierToken token = null;
		if (expectTypeCurrent(IdentifierToken.class)) {
			token = (IdentifierToken) getCurrent();
		} else {
			token = new IdentifierToken("<null>", getCurrent().getAddress()).markError();
		}

		nextToken();
		nextToken();
		nextToken();
		Expression expr = parseExpression(Precedence.NONE);
		return new VarAssignStmt(token, expr);
	}

	/**
	 * parses the setting of a variable. It expects the current token to be on the
	 * identifier.<br>
	 *
	 * the syntax is as following: &lt;var name&gt; = &lt;expr&gt;
	 *
	 * @return the var set statement
	 */
	private VarSetStmt parseVarSetStmt() {
		IdentifierToken token = null;
		if (expectTypeCurrent(IdentifierToken.class)) {
			token = (IdentifierToken) getCurrent();
		} else {
			token = new IdentifierToken("<null>", getCurrent().getAddress()).markError();
		}
		nextToken();
		nextToken();
		Expression expr = parseExpression(Precedence.NONE);

		return new VarSetStmt(token, expr);
	}

	/**
	 * Parses an expression and wraps it in a {@link ExpressionStmt}
	 * @return the statement
	 */
	private ExpressionStmt parseExpressionStmt() {
		return new ExpressionStmt(parseExpression(Precedence.NONE));
	}

	/**
	 * parses an expression
	 *
	 * @param precedence the precedence of the last parseExpression. Just use
	 *                   {@link Precedence#NONE} if it is unkown
	 * @return the parsed expression
	 */
	private Expression parseExpression(Precedence precedence) {
		Expression leftExpr = parsePrefixExpression();
		while (Precedence.getPrecedenceOf(current).getPrecedence() > precedence.getPrecedence()) {
			Token opToken = getCurrent();
			nextToken();
			Expression rightExpr = parseExpression(Precedence.getPrecedenceOf(opToken));
			leftExpr = parseMathOp(leftExpr, opToken, rightExpr);
		}
		return leftExpr;
	}

	private Expression parseMathOp(Expression left, Token token, Expression right) {
		expectTypeCurrent(KeywordToken.class);
		KeywordToken keyword = (KeywordToken) token;
		return new InfixExpr(MathOp.fromKeyword(keyword.getType()), left, right);
	}

	/**
	 * Reads the current token and tries to pars it as an expression.
	 * An expression is a integer or a variable.
	 * @return returns the parsed expression or throws an {@link RuntimeWrapperException}
	 * @throws if the current token is an unexpected token
	 */
	private Expression parsePrefixExpression() {
		Token current = getCurrent();
		if (current instanceof IntegerToken) {
			return readNumber();
		} else if(current instanceof IdentifierToken) {
			return readVarExpression();
		} else if(KeywordType.getKeywordType(current) == KeywordType.OPENING_PARENTHESES) {
			return parseParentheses();
		} else if(current instanceof KeywordToken) {
			KeywordToken keywordToken = (KeywordToken) current;
			if(keywordToken.getPrecedence() != Precedence.NONE) {
				nextToken();
				Expression expr = parseExpression(Precedence.NONE);
				return new PrefixExpr(MathOp.fromKeyword(keywordToken.getType()), expr);
			}
		}
		nextToken();
		throw new RuntimeWrapperException(
				new ParserException(current.getAddress(), "Unexpected Token: \"" + current.getString() + "\""));
	}

	/**
	 * Reads a integer token into a {@link IntegerExpr}.
	 * Expects the current token to be an integer token
	 * @return the integer expression
	 */
	private IntegerExpr readNumber() {
		Token current = getCurrent();
		nextToken();

		if (expectType(current, IntegerToken.class)) {
			IntegerToken token = (IntegerToken) current;
			return new IntegerExpr(token.getNumber());
		} else {
			throw new RuntimeWrapperException(new ParserException(current.getAddress(),
					"Unexpected Token: \"" + current.getString() + "\", expected a Number"));
		}
	}

	/**
	 * Reads an identifier into an var expression.
	 * Expects the current token to be an identifier token
	 * @return
	 */
	private VarExpr readVarExpression() {
		Token current = getCurrent();
		nextToken();

		if (expectType(current, IdentifierToken.class)) {
			IdentifierToken token = (IdentifierToken) current;
			return new VarExpr(token);
		} else {
			throw new RuntimeWrapperException(new ParserException(current.getAddress(),
					"Unexpected Token: \"" + current.getString() + "\", expected a variable name"));
		}
	}

	private Expression parseParentheses() {
		expectCurrent(KeywordType.OPENING_PARENTHESES);
		nextToken();
		Expression expr = parseExpression(Precedence.NONE);
		expectCurrent(KeywordType.CLOSING_PARENTHESES);
		nextToken();
		return expr;
	}

	/**
	 * Returns the exception handler used by this parser
	 *
	 * @return the handler
	 */
	public ExceptionHandler getHandler() {
		return handler;
	}
}
