
package rsupport.rsupport.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import rsupport.rsupport.Dto.MemberDto;
import rsupport.rsupport.domain.Member;
import rsupport.rsupport.domain.QMember;
import rsupport.rsupport.domain.QTeam;

import java.util.List;

public class CustomMemberRepositoryImpl extends QuerydslRepositorySupport implements CustomMemberRepository {

    public CustomMemberRepositoryImpl() {
        super(Member.class);
    }

    @Override
    public List<MemberDto> searchMembers(String searchWord) {
        QMember member = QMember.member;

        BooleanBuilder builder = new BooleanBuilder();
        if (searchWord!=null) {
            builder.or(member.name.contains(searchWord))
                    .or(member.team.name.contains(searchWord))
                    .or(member.number.contains(searchWord))
                    .or(member.phone.contains(searchWord));
        }

        return from(member)
                .select(Projections.constructor(MemberDto.class, member.name, member.number, member.phone, member.team.name, member.grade, member.position))
                .join(member.team, QTeam.team)
                .where(builder)
                .fetch();
    }
}
