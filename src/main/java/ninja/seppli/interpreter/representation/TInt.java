package ninja.seppli.interpreter.representation;

/**
 * A representation of an integer
 * @author sebi
 *
 */
public class TInt implements Value {
	/**
	 * the int value
	 */
	private int value;

	/**
	 * Construtor
	 * @param value the value
	 */
	public TInt(int value) {
		this.value = value;
	}

	/**
	 * returns the value
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * sets the value
	 * @param value the value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String getTypeName() {
		return "TInt";
	}

	@Override
	public TString convertToTString() {
		return new TString(String.valueOf(getValue()));
	}
}
