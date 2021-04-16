
package rsupport.rsupport.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchDto {
    private String teamName;
    private String name;
    private int number;
    private String phone;

    @Builder
    public SearchDto(String teamName, String name, int number, String phone) {
        this.teamName = teamName;
        this.name = name;
        this.number = number;
        this.phone = phone;
    }
}
