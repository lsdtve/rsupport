
package rsupport.rsupport.repository;

import rsupport.rsupport.Dto.MemberDto;

import java.util.List;

public interface CustomMemberRepository{
    List<MemberDto> searchMembers(String searchWord);
}
