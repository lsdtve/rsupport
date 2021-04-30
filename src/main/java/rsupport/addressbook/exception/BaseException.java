package rsupport.addressbook.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ExceptionCode enumBaseException;

    public BaseException(ExceptionCode enumBaseException) {
        this.enumBaseException = enumBaseException;
    }
}
