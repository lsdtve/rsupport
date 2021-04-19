
package rsupport.rsupport.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import rsupport.rsupport.domain.Member;
import rsupport.rsupport.domain.QMember;
import rsupport.rsupport.domain.QTeam;

import java.util.List;

public class CustomMemberRepositoryImpl extends QuerydslRepositorySupport implements CustomMemberRepository {

    public CustomMemberRepositoryImpl() {
        super(Member.class);
    }

    @Override
    public List<Member> searchMembers(String searchWord) {
        QMember member = QMember.member;

        BooleanBuilder builder = new BooleanBuilder();
        if (searchWord!=null) {
            builder.or(member.name.contains(searchWord))
                    .or(member.team.name.contains(searchWord))
                    .or(member.number.contains(searchWord))
                    .or(member.phone.contains(searchWord));
        }

        JPQLQuery<Member> query = from(member)
                .leftJoin(member.team, QTeam.team)
                .fetchJoin()
                .where(builder)
                .orderBy(member.name.desc());

        return query.fetch();
    }
}
