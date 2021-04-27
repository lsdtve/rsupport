
package rsupport.addressbook.repository;

import rsupport.addressbook.dto.MemberDto;

import java.util.List;

public interface CustomMemberRepository{
    List<MemberDto> searchMembers(String searchWord);
}
