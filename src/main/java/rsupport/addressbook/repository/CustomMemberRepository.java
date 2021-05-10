package rsupport.addressbook.repository;

import java.util.List;
import rsupport.addressbook.domain.Member;

public interface CustomMemberRepository {
	List<Member> findAllBySearchWord(String searchWord);
}
