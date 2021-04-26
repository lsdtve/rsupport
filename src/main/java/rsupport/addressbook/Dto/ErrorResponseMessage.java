package rsupport.addressbook.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseMessage {
    private String status;
    private String code;
    private String message;
}
