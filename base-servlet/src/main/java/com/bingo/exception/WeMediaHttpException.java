/**
 *   AlertHttpException.java
 *   Created on: Nov 29, 2012
 *   Author: Ti Yunlong,  yti@microstrategy.com
 */
package com.bingo.exception;

/**
 *
 */
public class WeMediaHttpException extends WeMediaException {

    /**
     *
     */
    private static final long serialVersionUID = -8253925220741020974L;

    /**
     *
     */
    public WeMediaHttpException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param error
     * @param msg
     */
    public WeMediaHttpException(ErrorCode error, String msg) {
        super(error, msg);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param error
     */
    public WeMediaHttpException(ErrorCode error) {
        super(error);
        // TODO Auto-generated constructor stub
    }

}
