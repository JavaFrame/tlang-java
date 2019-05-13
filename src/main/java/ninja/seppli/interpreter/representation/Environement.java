package ninja.seppli.interpreter.representation;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Environement {
	private Logger logger = LogManager.getLogger();
	private Scope currentScope = new Scope();

	public Environement() {
	}

	public Scope getScope() {
		return currentScope;
	}

	public void loadDefaultFunctions() {
		currentScope.set("print", (TFunction) args -> {
			String msg = Arrays.stream(args).map(Value::convertToTString).map(TString::getValue).collect(Collectors.joining(", "));
			System.out.println(msg);
			return TNull.NULL;
		});
		currentScope.set("environementToString", (TFunction) args -> new TString(print()));
	}

	public String print() {
		StringBuilder out = new StringBuilder();
		out.append("environement:\n");
		String[] names = currentScope.getNames();
		for(int i = 0; i < names.length; i++) {
			out.append("\t" + names[i] + " --> " + currentScope.get(names[i]).convertToTString().getValue());
			if(i + 1 != names.length) {
				out.append("\n");
			}
		}
		return out.toString();
	}
}
