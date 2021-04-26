
package rsupport.addressbook.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private String name;
    private String number;
    private String phone;
    private String teamName;
    private String grade;
    private String position;

    public MemberDto(String name, String number, String phone, String teamName, String grade, String position) {
        this.name = name;
        this.number = number;
        this.phone = phone;
        this.teamName = teamName;
        this.grade = grade;
        this.position = position;
    }
}
