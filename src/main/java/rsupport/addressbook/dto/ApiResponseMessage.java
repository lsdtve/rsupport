package rsupport.addressbook.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseMessage {
    private String status;
    private String message;
    private Object data;
}
