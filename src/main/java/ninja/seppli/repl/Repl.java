package ninja.seppli.repl;

import java.util.Scanner;

import ninja.seppli.ast.statement.Program;
import ninja.seppli.exception.ExceptionHandler;
import ninja.seppli.interpreter.Interpreter;
import ninja.seppli.interpreter.representation.Environement;
import ninja.seppli.interpreter.representation.Value;
import ninja.seppli.lexer.Lexer;
import ninja.seppli.parser.Parser;

public class Repl {

	public Repl() {
	}

	public void run() {
		try {
			Environement environement = new Environement();
			environement.loadDefaultFunctions();
			System.out.println("Welcome to the TLang REPL");
			System.out.println(environement.print());
			System.out.println();

			System.out.print(">");
			Scanner in = new Scanner(System.in);
			while (in.hasNext()) {
				String line = in.nextLine();
				if(!line.trim().endsWith(";")) {
					line += ";";
				}

				ExceptionHandler exceptionHandler = new ExceptionHandler();

				Lexer lexer = new Lexer(line, "<repl>");
				Parser parser = new Parser(lexer, exceptionHandler);
				Program program = parser.parse();
				if (exceptionHandler.hasErrors()) {
					System.err.println(exceptionHandler.printToString());
					System.err.flush();
					Thread.sleep(50);
				} else {
					Interpreter interpreter = new Interpreter(program, environement, exceptionHandler);
					Value returnValue = interpreter.execute();
					if(exceptionHandler.hasErrors()) {
						System.err.println(exceptionHandler.printToString() );
						System.err.flush();
						Thread.sleep(50);
					}
					System.out.println("-> " + returnValue.convertToTString().getValue());
				}
				System.out.print("> ");
			}
		} catch (InterruptedException e) {
			return;
		}
	}
}
