package ninja.seppli.interpreter.representation;

import java.util.HashMap;
import java.util.Map;

public class Scope {
	/**
	 * the variables of the scope
	 */
	private Map<String, Value> variables = new HashMap<>();

	public Scope() {
	}

	public Value get(String name) {
		return variables.get(name);
	}

	public boolean exists(String name) {
		return variables.containsKey(name);
	}

	public void set(String name, Value v) {
		variables.put(name, v);
	}

	public String[] getNames() {
		return variables.keySet().toArray(new String[variables.size()]);
	}
}
