package rsupport.rsupport.Dto;

import lombok.Data;
import rsupport.rsupport.domain.Team;

@Data
public class MemberCreateForm {
    private Long id;
    private String name;
    private int number;
    private String phone;
    private Team team;
    private String spot; // 직위
    private String position; // 직책
}
