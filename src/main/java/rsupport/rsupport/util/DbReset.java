
package rsupport.rsupport.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rsupport.rsupport.repository.MemberRepository;
import rsupport.rsupport.repository.TeamRepository;

@RequiredArgsConstructor
@Component
public class DbReset {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    public void dbReset(){
        memberRepository.deleteAll();
        teamRepository.deleteAll();
    }
}
