package rsupport.addressbook.repository;

import org.springframework.data.repository.CrudRepository;
import rsupport.addressbook.domain.Member;

public interface MemberRepository extends CrudRepository<Member,Long>, MemberCustomRepository {
    int countByOriginalName(String originalName);
}
