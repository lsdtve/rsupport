package rsupport.addressbook.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rsupport.addressbook.domain.Member;
import rsupport.addressbook.dto.OrganizationChartTeamDto;
import rsupport.addressbook.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public String duplicateName(String name) {
        int sameNameCount = memberRepository.countByNameStartingWith(name);

        if (sameNameCount==0) {
            return name;
        }

        return String.format("%s(%c)", name, sameNameCount+'A');
    }

    public List<OrganizationChartTeamDto> getOrganizationChart(String searchWord) {

        return memberRepository.findAllBySearchWord(searchWord).stream()
            .collect(Collectors.groupingBy(member -> member.getTeam().getName(),
                LinkedHashMap::new,
                Collectors.toList()))
            .entrySet().stream()
            .map(entry -> new OrganizationChartTeamDto(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }

    public void deleteAll() {
        memberRepository.deleteAll();
    }

    public long count() {
        return memberRepository.count();
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
