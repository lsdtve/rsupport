package rsupport.addressbook.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotNull
    private String name;

    @Column(unique = true)
    private String number;

    @Column(unique = true)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "team_id")
    private Team team;

    private String grade;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Position position;

    @Builder
    public Member(String name, String number, String phone, Team team, String grade, Position position) {
        this.name = name;
        this.number = number;
        this.phone = phone;
        this.team = team;
        this.grade = grade;
        this.position = position;
    }

    public Member changeTeam(Team team) {
        if (!team.getMembers().isEmpty()) {
            team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
        return this;
    }
}
