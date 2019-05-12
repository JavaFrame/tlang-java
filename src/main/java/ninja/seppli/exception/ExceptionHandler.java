package ninja.seppli.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class ExceptionHandler {
	private List<TLangException> errors = new ArrayList<>();
	private List<TLangException> warnings = new ArrayList<>();
	private List<TLangException> infos = new ArrayList<>();

	public ExceptionHandler() {
	}

	public void error(TLangException e) {
		errors.add(e);
	}

	public void warning(TLangException e) {
		warnings.add(e);
	}

	public void info(TLangException e) {
		infos.add(e);
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public TLangException[] getErrors() {
		return errors.toArray(new TLangException[errors.size()]);
	}

	public TLangException[] getWarnings() {
		return warnings.toArray(new TLangException[warnings.size()]);
	}

	public TLangException[] getInfos() {
		return infos.toArray(new TLangException[infos.size()]);
	}

	public String printToString() {
		return printToString(false);
	}

	public String printToString(boolean debug) {
		StringBuffer buf = new StringBuffer();
		for(TLangException e : errors) {
			buf.append("[ERROR] " + e.getMessage() + (debug?getTrace(e):"") + "\n");
		}
		for(TLangException w : warnings) {
			buf.append("[WARN] " + w.getMessage() + "\n");
		}
		for(TLangException i : infos) {
			buf.append("[INFO] " + i.getMessage() + "\n");
		}
		String str = buf.toString();
		//splits the last \n away
		return str.substring(0, str.length() - 1);
	}

	private String getTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	public void clear() {
		errors.clear();
		warnings.clear();
		infos.clear();
	}

	private static ExceptionHandler instance;
	public static ExceptionHandler getInstance() {
		if(instance == null) {
			instance = new ExceptionHandler();
		}
		return instance;
	}
}
