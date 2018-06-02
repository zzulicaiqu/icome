package cn.zzuli.cloud.commons.exception;

/**
 * 运行时异常类
 *
 * @author fredlau
 * @date 2017/12/15/015
 */
public class EnnException extends RuntimeException {
    private static final long serialVersionUID = -3549913936889296342L;

    /**
     *
     */
    public EnnException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public EnnException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public EnnException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public EnnException(Throwable cause) {
        super(cause);
    }

}
