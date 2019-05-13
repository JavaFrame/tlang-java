package ninja.seppli.lexer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import ninja.seppli.lexer.token.IdentifierToken;
import ninja.seppli.lexer.token.IntegerToken;
import ninja.seppli.lexer.token.KeywordToken;
import ninja.seppli.lexer.token.KeywordType;
import ninja.seppli.lexer.token.StringToken;
import ninja.seppli.lexer.token.Token;
import ninja.seppli.utils.TextAddress;

public class LexerTest {
	private Logger logger = LogManager.getLogger();

	private Lexer lexer;
	private int nextChPos = 1;
	private String text;

	@Test
	public void testMathTokens() {
		text = "1+2-30-2";
		lexer = new Lexer(text, "<junittest>");
		assertArrayEquals(new Token[] { new IntegerToken(1, getAddr()), new KeywordToken(KeywordType.PLUS, getAddr()),
				new IntegerToken(2, getAddr()), new KeywordToken(KeywordType.MINUS, getAddr()),
				new IntegerToken(30, getAddr()), new KeywordToken(KeywordType.MINUS, getAddr()),
				new IntegerToken(2, getAddr()), }, readAllTokens(lexer));
	}

	@Test
	public void testIdTokens() {
		text = "var1+2";
		lexer = new Lexer(text, "<junittest>");
		assertArrayEquals(new Token[] { new IdentifierToken("var1", getAddr()),
				new KeywordToken(KeywordType.PLUS, getAddr()), new IntegerToken(2, getAddr()), }, readAllTokens(lexer));
	}

	@Test
	public void testStringToken() {
		text = "\"test\";";
		lexer = new Lexer(text, "<junittest>");
		assertArrayEquals(
				new Token[] { new StringToken("test", getAddr()), new KeywordToken(KeywordType.SEMICOLON, getAddr()) },
				readAllTokens(lexer));
	}

	private Token[] readAllTokens(Lexer lexer) {
		List<Token> tokens = new ArrayList<>();
		Token t;
		KeywordToken eofToken = new KeywordToken(KeywordType.EOF, null);
		while ((t = lexer.getNextToken()) != null && !eofToken.equals(t)) {
			tokens.add(t);
		}
		logger.info("tokens: {}",
				tokens.stream().map(token -> "{ " + token.getAddress().getLine() + ":"
						+ token.getAddress().getCharacter() + ": " + token.getString() + " }")
				.collect(Collectors.joining("; ")));
		return tokens.toArray(new Token[tokens.size()]);
	}

	private TextAddress getAddr() {
		return new TextAddress(0, nextChPos++, "<junittest>", text);
	}
}
