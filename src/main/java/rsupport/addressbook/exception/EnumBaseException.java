
package rsupport.addressbook.exception;

import lombok.Getter;

@Getter
public enum EnumBaseException {
    FILE_NOT_FOUND("E0001", "file not found"),
    RUNTIME_ERROR("E0002", "runtime error");

    private final String code;
    private final String message;

    EnumBaseException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
