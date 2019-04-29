package exceptions;

public class NetworkException extends RuntimeException {
	public NetworkException() {
	}

    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(Throwable cause) {
    	super(cause);
    }

	public NetworkException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
