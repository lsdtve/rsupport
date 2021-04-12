package rsupport.rsupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rsupport.rsupport.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    List<Member> findAll();
    Boolean existsByName(String name);

    @Query("select m from Member m where m.team.id = :teamId and m.position = '팀장'")
    Optional<Member> findByLeader(@Param("teamId") Long teamId);

    @Query("select m from Member m where m.team.id=:teamId and m.position is null order by m.name")
    List<Member> findByMembers(@Param("teamId")Long teamId);
}
