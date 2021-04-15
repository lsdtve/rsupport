
package rsupport.rsupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rsupport.rsupport.domain.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long>, CustomMemberRepository {
    List<Member> findAll();
    char countByOriginalName(String originalName);
}
