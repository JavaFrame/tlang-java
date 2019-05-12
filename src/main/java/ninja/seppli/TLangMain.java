package ninja.seppli;

import ninja.seppli.repl.Repl;

/**
 * Hello world!
 *
 */
public class TLangMain {
	public static void main(String[] args) {
		for(String arg : args) {
			if("-i".equals(arg) || "--repl".equals(arg)) {
				new Repl().run();
			}
		}
	}
}
