package ninja.seppli.interpreter.representation;

/**
 * an interface for a value representation
 * @author sebi
 *
 */
public interface Value {
	/**
	 * Returns the name of the type of this value
	 * @return the name of the type
	 */
	String getTypeName();

	/**
	 * Converts the value to a string
	 * @return the value
	 */
	TString convertToTString();
}
