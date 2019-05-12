package ninja.seppli.interpreter.representation;

public class Environement {
	private Scope currentScope = new Scope();

	public Environement() {
	}

	public Scope getScope() {
		return currentScope;
	}
}
