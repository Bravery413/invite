package exceptions;

import common.RetCode;

public class BusinessException extends RuntimeException  {
    private int rc;
	public BusinessException(int rc) {
        this.rc = rc;
	}

    public BusinessException(RetCode rc) {
        this.rc = rc.getCode();
    }

    public BusinessException(int rc, String message) {
        super(message);
        this.rc = rc;
    }

    public BusinessException(RetCode rc, String message) {
        super(message);
        this.rc = rc.getCode();
    }

    public BusinessException(int rc, Throwable cause) {
    	super(cause);
        this.rc = rc;
    }

    public BusinessException(RetCode rc, Throwable cause) {
        super(cause);
        this.rc = rc.getCode();
    }

	public BusinessException(int rc, String msg, Throwable cause) {
		super(msg, cause);
        this.rc = rc;
	}

    public BusinessException(RetCode rc, String msg, Throwable cause) {
        super(msg, cause);
        this.rc = rc.getCode();
    }

    public int getRc() {
        return rc;
    }

    public RetCode getRetCode() {
        return RetCode.parse(rc);
    }
}
