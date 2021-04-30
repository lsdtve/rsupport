package rsupport.addressbook.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final EnumBaseException enumBaseException;

    public BaseException(EnumBaseException enumBaseException) {
        this.enumBaseException = enumBaseException;
    }
}
