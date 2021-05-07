package rsupport.addressbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rsupport.addressbook.domain.Member;
import rsupport.addressbook.domain.Team;
import rsupport.addressbook.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(Member member) {
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
