
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
    private int number;
    private String phone;
    private Team team;
    private String grade; // 직위
    private String position; // 직책

    @Builder
    public MemberCreateForm(String name, int number, String phone, Team team, String grade, String position) {
        this.name = name;
        this.number = number;
        this.phone = phone;
        this.team = team;
        this.grade = grade;
        this.position = position;
    }
}
