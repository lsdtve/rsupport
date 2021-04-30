package rsupport.addressbook.repository;

import org.springframework.data.repository.CrudRepository;
import rsupport.addressbook.domain.Member;

public interface MemberRepository extends CrudRepository<Member,Long>, CustomMemberRepository {
    int countByOriginalName(String originalName);
}
