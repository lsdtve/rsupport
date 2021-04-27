package rsupport.addressbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rsupport.addressbook.dto.ErrorResponseMessage;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponseMessage> baseException(BaseException e) {
        ErrorResponseMessage response = new ErrorResponseMessage();
        response.setStatus("500");
        response.setCode(e.getEnumBaseException().getCode());
        response.setMessage(e.getEnumBaseException().getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
