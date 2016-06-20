package com.bingo.exception;

import java.util.Locale;


/**
 * @author Grey
 */
public class WeMediaException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 8998245782374612L;
    /**
     * @uml.property name="errorCode"
     * @uml.associationEnd
     */
    protected ErrorCode errorCode;

    protected Locale locale;
    protected final Boolean toLocalize;

    // ----------------------------------------------
    // Default constructor - initializes instance variable to unknown
    public WeMediaException() {
        super(ErrorCode.GeneralError.toString()); // call superclass constructor
        errorCode = ErrorCode.GeneralError;
        toLocalize = true;
    }

    // -----------------------------------------------
    // Constructor receives some kind of message that is saved in an instance
    // variable.
    public WeMediaException(ErrorCode error) {
        super(error.toString()); // call super class constructor
        errorCode = error; // save message
        toLocalize = true;
    }

    // -----------------------------------------------
    // Constructor receives some kind of message that is saved in an instance
    // variable.
    public WeMediaException(ErrorCode error, String msg) {
        super(msg); // call super class constructor
        errorCode = error; // save message
        // if message is passed in constructor, it should be already localized. only generic messages are localized in AlertException
        toLocalize = false;
    }


    // ------------------------------------------------
    // public method, callable by com.bingo.exception catcher. It returns the error
    // message.
    public ErrorCode getError() {
        return errorCode;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }


}