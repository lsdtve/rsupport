
package rsupport.addressbook.repository;

import rsupport.addressbook.Dto.MemberDto;

import java.util.List;

public interface CustomMemberRepository{
    List<MemberDto> searchMembers(String searchWord);
}
