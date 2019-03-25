package ninja.seppli.parser;

import ninja.seppli.ast.statement.Program;
import ninja.seppli.ast.statement.Statement;
import ninja.seppli.lexer.Lexer;
import ninja.seppli.lexer.token.Token;

public class Parser {
	private Lexer lexer;
	private Token current;
	private Token next;

	public Parser(Lexer lexer) {
		this.lexer = lexer;
	}

	private void nextToken() {
		current = next;
		next = lexer.getNextToken();
	}

	private Token getCurrent() {
		return current;
	}

	private Token getNext() {
		return next;
	}


	public Lexer getLexer() {
		return lexer;
	}

	public Program parse() {
		Program program = new Program();
		Statement stmt;
		while((stmt = parseStmt()) != null) {
			program.add(stmt);
		}
		return program;
	}

	private Statement parseStmt() {
		Token t = getCurrent();
		return null;
	}

}
