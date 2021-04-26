package rsupport.addressbook.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseMessage {
    private String status;
    private String message;
    private Object data;
}
