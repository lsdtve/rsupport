package rsupport.addressbook.repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rsupport.addressbook.domain.Member;
import rsupport.addressbook.domain.QMember;
import rsupport.addressbook.domain.QTeam;
import rsupport.addressbook.dto.OrganizationChartTeamDto;

@Repository
public class OrganizationChartRepository {

	@Autowired
	private EntityManager entityManager;

	public List<OrganizationChartTeamDto> findOrganizationChart(String searchWord) {
		JPAQuery<?> query = new JPAQuery<>(entityManager);

		QMember member = QMember.member;
		QTeam team = QTeam.team;

		BooleanBuilder builder = new BooleanBuilder();
		if (searchWord!=null) {
			builder.or(member.name.contains(searchWord))
				.or(member.team.name.contains(searchWord))
				.or(member.number.contains(searchWord))
				.or(member.phone.contains(searchWord));
		}

		LinkedHashMap<String, List<Member>> transform = (LinkedHashMap<String, List<Member>>) query
			.from(member)
			.leftJoin(member.team, team)
			.where(builder)
			.orderBy(team.name.asc(), member.position.asc(), member.name.asc())
			.transform(groupBy(team.name).as(list(member)));

		return transform.entrySet().stream()
			.map(entity -> new OrganizationChartTeamDto(entity.getKey(),entity.getValue()))
			.collect(Collectors.toList());
	}
}
