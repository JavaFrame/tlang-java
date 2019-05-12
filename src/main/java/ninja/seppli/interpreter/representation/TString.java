package ninja.seppli.interpreter.representation;

public class TString implements Value {
	private String value;

	public TString(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String getTypeName() {
		return "TString";
	}

	@Override
	public TString convertToTString() {
		return new TString(getValue());
	}

}
