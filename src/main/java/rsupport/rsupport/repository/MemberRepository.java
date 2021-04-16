
package rsupport.rsupport.repository;

import org.springframework.data.repository.CrudRepository;
import rsupport.rsupport.domain.Member;

import java.util.List;

public interface MemberRepository extends CrudRepository<Member,Long>, CustomMemberRepository {
    List<Member> findAll();
    int countByOriginalName(String originalName);
}
