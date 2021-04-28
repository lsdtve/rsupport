
package rsupport.addressbook.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseMessage {
    private String code;
    private String message;
}
