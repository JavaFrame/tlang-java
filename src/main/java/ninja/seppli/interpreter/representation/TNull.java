package ninja.seppli.interpreter.representation;

public class TNull implements Value {
	public final static TNull NULL = new TNull();

	private TNull() {
	}

	@Override
	public String getTypeName() {
		return "null";
	}

	@Override
	public TString convertToTString() {
		return new TString("<null>");
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof TNull;
	}
}
