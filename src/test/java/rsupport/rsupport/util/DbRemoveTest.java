
package rsupport.rsupport.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rsupport.rsupport.domain.Member;
import rsupport.rsupport.domain.Team;
import rsupport.rsupport.repository.MemberRepository;
import rsupport.rsupport.repository.TeamRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DbRemoveTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private TeamRepository teamRepository;
    @Autowired private DbRemove dbRemove;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void dbRemove() throws Exception {
        //given
        Team team = teamRepository.save(Team.builder().name("웹개발1팀").build());
        memberRepository.save(Member.builder().name("홍길동").team(team).build());

        em.flush();
        em.clear();

        //when
        dbRemove.dbRemove();

        //then
        long teamCount = teamRepository.count();
        long memberCount = memberRepository.count();

        Assert.assertEquals(0L, memberCount);
        Assert.assertEquals(0L, teamCount);
    }

}