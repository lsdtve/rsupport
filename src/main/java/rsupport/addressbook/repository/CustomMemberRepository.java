package rsupport.addressbook.repository;

import java.util.List;
import rsupport.addressbook.dto.MemberDto;

public interface CustomMemberRepository{
    List<MemberDto> searchMembers(String searchWord);
}
