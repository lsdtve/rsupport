package rsupport.addressbook.dto;

import lombok.Getter;
import lombok.Setter;
import rsupport.addressbook.domain.Member;

@Getter
@Setter
public class OrganizationChartMemberDto {
    private String name;
    private String number;
    private String phone;
    private String grade;
    private String position;

    public OrganizationChartMemberDto(Member member) {
        this.name = member.getName();
        this.number = member.getNumber();
        this.phone = member.getPhone();
        this.grade = member.getGrade();
        this.position = member.getPosition();
    }
}
