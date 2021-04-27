
package rsupport.addressbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rsupport.addressbook.dto.MemberCreateForm;
import rsupport.addressbook.dto.MemberDto;
import rsupport.addressbook.domain.Member;
import rsupport.addressbook.domain.Team;
import rsupport.addressbook.repository.MemberRepository;
import rsupport.addressbook.repository.TeamRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    public Member save(MemberCreateForm form){
        Team team = teamRepository.findByName(form.getTeamName()).orElseGet(() ->
                Team.builder().name(form.getTeamName()).build()
        );

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

    public String duplicateName(String name){
        int sameNameCount = memberRepository.countByOriginalName(name);

        if (sameNameCount==0){
            return name;
        }

        return String.format("%s(%c)", name, sameNameCount+'A');
    }

    public List<MemberDto> search(String searchWord) {
        return memberRepository.searchMembers(searchWord);
    }
}
