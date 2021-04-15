
package rsupport.rsupport.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import rsupport.rsupport.Dto.SearchDto;
import rsupport.rsupport.domain.Member;
import rsupport.rsupport.domain.QTeam;

import java.util.List;

import static rsupport.rsupport.domain.QMember.member;

public class CustomMemberRepositoryImpl extends QuerydslRepositorySupport implements CustomMemberRepository {

    public CustomMemberRepositoryImpl() {
        super(Member.class);
    }

    @Override
    public List<Member> searchMembers(SearchDto searchDto) {
        JPQLQuery<Member> query = from(member)
                .leftJoin(member.team, QTeam.team)
                .fetchJoin()
                .where(eqOriginalName(searchDto.getName()),
                        eqNumber(searchDto.getNumber()),
                        eqTeam(searchDto.getTeamName()),
                        eqPhone(searchDto.getPhone()))
                .orderBy(member.name.desc());

        return query.fetch();
    }

    private BooleanExpression eqOriginalName(String name) {
        if (name==null){
            return null;
        }
        return member.originalName.eq(name);
    }

    private BooleanExpression eqNumber(int number) {
        if (number==0){
            return null;
        }
        return member.number.eq(number);
    }

    private BooleanExpression eqTeam(String teamName) {
        if (teamName==null){
            return null;
        }
        return member.team.name.eq(teamName);
    }

    private BooleanExpression eqPhone(String phone) {
        if (phone==null){
            return null;
        }
        return member.phone.eq(phone);
    }
}
