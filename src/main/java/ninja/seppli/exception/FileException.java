package ninja.seppli.exception;

import ninja.seppli.utils.TextAddress;

/**
 * An exception which happend in a file
 * @author sebi
 *
 */
public class FileException extends TLangException {
	/**
	 * the address where it happend
	 */
	private TextAddress address;
	/**
	 * the error message
	 */
	private String error;

	/**
	 * constructor
	 * @param address the address of the error
	 * @param error the error itself
	 */
	public FileException(TextAddress address, String error) {
		super(address + ": " + error);
		this.address = address;
		this.error = error;
	}

	/**
	 * Returns the address of the error
	 * @return the address
	 */
	public TextAddress getAddress() {
		return address;
	}

	/**
	 * Returns the error message itself.
	 * This function is not equal to {@link #getMessage()} which
	 * also returns the address.
	 * @return
	 */
	public String getError() {
		return error;
	}
}
