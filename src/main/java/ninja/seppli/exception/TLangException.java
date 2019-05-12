package ninja.seppli.exception;

/**
 * An exception for general tlang exceptions.
 * Do use its subclasses instead of the TLangException itself
 * @author sebi
 *
 */
public abstract class TLangException extends Exception {

	public TLangException(String message, Throwable cause) {
		super(message, cause);
	}

	public TLangException(String message) {
		super(message);
	}

}
