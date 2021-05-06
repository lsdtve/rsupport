package rsupport.addressbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rsupport.addressbook.domain.Member;
import rsupport.addressbook.domain.Team;
import rsupport.addressbook.dto.MemberCreateForm;
import rsupport.addressbook.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TeamService teamService;

    public Member save(MemberCreateForm form) {
        Team team = teamService.findTeamOrElseNewTeam(form.getTeamName());

        Member member = Member.builder()
                .originalName(form.getName())
                .name(duplicateName(form.getName()))
                .number(form.getNumber())
                .phone(form.getPhone())
                .team(team)
                .grade(form.getGrade())
                .position(form.getPosition())
                .build();
        return memberRepository.save(member);
    }

    public String duplicateName(String name) {
        int sameNameCount = memberRepository.countByOriginalName(name);

        if (sameNameCount==0) {
            return name;
        }

        return String.format("%s(%c)", name, sameNameCount+'A');
    }

}
