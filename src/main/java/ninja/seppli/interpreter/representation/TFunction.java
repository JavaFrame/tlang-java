package ninja.seppli.interpreter.representation;

@FunctionalInterface
public interface TFunction extends Value {
	Value execute(Value[] args);

	@Override
	default String getTypeName() {
		return "TFunction";
	}

	@Override
	default TString convertToTString() {
		return new TString("<function()>");
	}
}
