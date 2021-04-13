package rsupport.rsupport.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private int number;
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    private String spot;
    private String position;

    @Builder
    public Member(Long id, String name, int number, String phone, Team team, String spot, String position) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.phone = phone;
        this.team = team;
        this.spot = spot;
        this.position = position;
    }
}
