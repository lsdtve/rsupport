package rsupport.addressbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamCreateForm {
    private String name;

    @Builder
    public TeamCreateForm(String name) {
        this.name = name;
    }
}
