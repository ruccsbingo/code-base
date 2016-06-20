package exception;

/**
 * @author weijian
 * Date : 2013-07-15 14:24
 */

public final class InvalidFormatException extends BaseException{

    public InvalidFormatException(String message) {
        super(message);
    }


    /**
     * @author weijian
     * Date : 2014-09-26 18:35
     */

    public static class InvalidRequestParameterException extends BaseException{


        public InvalidRequestParameterException(String message) {
            super(message);
        }
    }
}
