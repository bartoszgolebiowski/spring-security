package pl.ttwug.security.ttwug.exception;

import org.springframework.security.core.AuthenticationException;

public class UserLoginException extends AuthenticationException {
    public UserLoginException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserLoginException(String msg) {
        super(msg);
    }
}
