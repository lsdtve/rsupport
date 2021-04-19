
package rsupport.rsupport.Dto;

import lombok.Getter;
import lombok.Setter;
import rsupport.rsupport.domain.Member;

@Getter
@Setter
public class MemberDto {
    private String name;
    private String number;
    private String phone;
    private String teamName;
    private String grade;
    private String position;

    public MemberDto(Member member) {
        this.name = member.getName();
        this.number = member.getNumber();
        this.phone = member.getPhone();
        this.teamName = member.getTeam().getName();
        this.grade = member.getGrade();
        this.position = member.getPosition();
    }
}
