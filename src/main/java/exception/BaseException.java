package exception;

/**
 * Base com.yidian.exception, the com.yidian.exception of this class or its sub class should be thrown
 * explicitly which means we could by pass the trace log for them
 * */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1355130784944057139L;

	public BaseException(String message) {
		super(message);
	}

}
