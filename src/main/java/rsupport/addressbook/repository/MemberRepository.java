package rsupport.addressbook.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import rsupport.addressbook.domain.Member;

public interface MemberRepository extends CrudRepository<Member,Long>, CustomMemberRepository {
    int countByNameStartingWith(String name);
    List<Member> findAll();
}
