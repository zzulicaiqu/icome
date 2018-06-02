package cn.zzuli.cloud.commons.exception;

import java.io.IOException;

/**
 * Http相关异常类
 *
 * @author fredlau
 * @date 2017/12/15/015
 */
public class EnnHttpException extends IOException {
    private static final long serialVersionUID = -3549913936889296341L;

    /**
     *
     */
    public EnnHttpException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public EnnHttpException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public EnnHttpException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public EnnHttpException(Throwable cause) {
        super(cause);
    }

}
