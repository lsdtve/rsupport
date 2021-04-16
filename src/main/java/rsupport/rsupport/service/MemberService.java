
package rsupport.rsupport.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsupport.rsupport.Dto.MemberCreateForm;
import rsupport.rsupport.Dto.MemberDto;
import rsupport.rsupport.Dto.SearchDto;
import rsupport.rsupport.domain.Member;
import rsupport.rsupport.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member save(MemberCreateForm form){
        Member member = Member.builder()
                .originalName(form.getName())
                .name(duplicateName(form.getName()))
                .number(form.getNumber())
                .phone(form.getPhone())
                .team(form.getTeam())
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

        return String.format("%s(%c)",name,sameNameCount+'A');
    }

    public List<MemberDto> search(SearchDto searchDto) {
        return memberRepository.searchMembers(searchDto).stream()
                .map(MemberDto::new)
                .collect(Collectors.toList());
    }
}
