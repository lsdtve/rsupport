package rsupport.rsupport.Dto;

import lombok.Data;
import rsupport.rsupport.domain.Member;

@Data
public class MemberDto {
    private String name;
    private int number;
    private String phone;
    private String teamName;
    private String spot;
    private String position;

    public MemberDto(){

    }
    public MemberDto(Member member) {
        this.name = member.getName();
        this.number = member.getNumber();
        this.phone = member.getPhone();
        this.teamName = member.getTeam().getName();
        this.spot = member.getSpot();
        this.position = member.getPosition();
    }
}
