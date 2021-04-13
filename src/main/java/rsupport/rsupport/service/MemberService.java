package rsupport.rsupport.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsupport.rsupport.Dto.MemberCreateForm;
import rsupport.rsupport.Dto.MemberDto;
import rsupport.rsupport.Dto.SearchDto;
import rsupport.rsupport.domain.Member;
import rsupport.rsupport.repository.MemberQueryRepository;
import rsupport.rsupport.repository.MemberRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;

    @Transactional
    public Member save(MemberCreateForm form){
        Member member = Member.builder()
                .name(DuplicateName(form.getName()))
                .number(form.getNumber())
                .phone(form.getPhone())
                .team(form.getTeam())
                .spot(form.getSpot())
                .position(form.getPosition())
                .build();
        return memberRepository.save(member);
    }

    public String DuplicateName(String name){
        int cnt = 65;
        String result = name;
        while(memberRepository.existsByName(result)){
            result = name + (cnt==65 ? "" : "("+(char) cnt+")");
            cnt += 1;
        }
        return result;
    }

    public List<MemberDto> findAll() {
        List<MemberDto> result = new ArrayList<>();
        memberRepository.findAll().forEach(member -> {
            MemberDto memberDto = new MemberDto(member);
            result.add(memberDto);
        });
        return result;
    }

    public List<Member> Search(SearchDto searchDto) {
        memberQueryRepository.search(searchDto);
        return null;
    }

    public List<MemberDto> sortMembers(List<Member> members) {
        return members.stream()
                .sorted(Comparator.comparing(Member::getName))
                .map(MemberDto::new)
                .collect(Collectors.toList());
    }
}
