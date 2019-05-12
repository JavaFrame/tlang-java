package ninja.seppli.exception;

import ninja.seppli.utils.TextAddress;

/**
 * An error which happend while parsing
 * @author sebi
 *
 */
public class ParserException extends FileException {

	/**
	 * constructor
	 * @param address the address of the address
	 * @param error the error message itself
	 */
	public ParserException(TextAddress address, String error) {
		super(address, error);
	}

}
