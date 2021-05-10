package rsupport.addressbook.repository;

import com.querydsl.core.BooleanBuilder;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import rsupport.addressbook.domain.Member;
import rsupport.addressbook.domain.QMember;
import rsupport.addressbook.domain.QTeam;

public class CustomMemberRepositoryImpl extends QuerydslRepositorySupport implements
	CustomMemberRepository {

	public CustomMemberRepositoryImpl() {
		super(Member.class);
	}

	@Override
	public List<Member> findAllBySearchWord(String searchWord) {
		
		QMember member = QMember.member;
		QTeam team = QTeam.team;
		
		BooleanBuilder builder = null;
		if (searchWord!=null) {
			builder = new BooleanBuilder();
			builder.or(member.name.contains(searchWord));
			builder.or(member.team.name.contains(searchWord));
			builder.or(member.number.contains(searchWord));
			builder.or(member.phone.contains(searchWord));
		}
		
		return from(member)
			.join(member.team, team)
			.fetchJoin()
			.where(builder)
			.orderBy(team.name.asc(), member.position.asc(), member.name.asc())
			.fetch();
	}
}
