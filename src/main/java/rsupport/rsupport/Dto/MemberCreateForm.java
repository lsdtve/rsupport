
package rsupport.rsupport.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import rsupport.rsupport.domain.Team;

@Getter
@Setter
public class MemberCreateForm {
    private Long id;
    private String name;
    private String number;
    private String phone;
    private Team team;
    private String grade;
    private String position;

    @Builder
    public MemberCreateForm(String name, String number, String phone, Team team, String grade, String position) {
        this.name = name;
        this.number = number;
        this.phone = phone;
        this.team = team;
        this.grade = grade;
        this.position = position;
    }
}
