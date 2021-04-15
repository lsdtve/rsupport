
package rsupport.rsupport.Dto;

import lombok.Getter;
import lombok.Setter;
import rsupport.rsupport.domain.Member;
import rsupport.rsupport.domain.Position;

@Getter
@Setter
public class ChartMemberDto {
    private String name;
    private int number;
    private String phone;
    private String grade;
    private String position;

    public ChartMemberDto(Member member) {
        this.name = member.getName();
        this.number = member.getNumber();
        this.phone = member.getPhone();
        this.grade = member.getGrade();
        this.position = member.getPosition();
    }
}
