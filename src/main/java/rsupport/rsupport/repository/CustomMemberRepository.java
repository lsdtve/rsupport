
package rsupport.rsupport.repository;

import rsupport.rsupport.Dto.SearchDto;
import rsupport.rsupport.domain.Member;

import java.util.List;

public interface CustomMemberRepository{
    List<Member> searchMembers(SearchDto searchDto);
}
