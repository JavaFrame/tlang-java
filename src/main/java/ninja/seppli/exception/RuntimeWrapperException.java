package ninja.seppli.exception;

/**
 * An exception which wrapps an normal exception with a runtime exception
 * for the parser, so not every method has to specify an throws ParserException
 * @author sebi
 *
 */
public class RuntimeWrapperException extends RuntimeException {
	private TLangException exception;

	public RuntimeWrapperException(TLangException e) {
		super(e.getMessage(), e.getCause());
		this.exception = e;
	}

	public TLangException getException() {
		return exception;
	}
}
