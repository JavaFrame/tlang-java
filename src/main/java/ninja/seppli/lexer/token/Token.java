package ninja.seppli.lexer.token;

import ninja.seppli.utils.TextAddress;

public interface Token {
	String getString();
	TextAddress getAddress();
}
