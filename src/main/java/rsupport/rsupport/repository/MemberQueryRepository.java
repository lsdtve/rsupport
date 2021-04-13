package rsupport.rsupport.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import rsupport.rsupport.Dto.SearchDto;
import rsupport.rsupport.domain.Member;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Member> search(SearchDto searchDto) {
//        return queryFactory.selectFrom(member)
//                .fetch();
        return null;
    }
}
