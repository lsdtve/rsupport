
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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String originalName;
    private String name;
    private String number;
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    private String grade;

    private String position;

    @Builder
    public Member(String originalName, String name, String number, String phone, Team team, String grade, String position) {
        this.originalName = originalName;
        this.name = name;
        this.number = number;
        this.phone = phone;
        this.team = team;
        this.grade = grade;
        this.position = position;
    }
}
