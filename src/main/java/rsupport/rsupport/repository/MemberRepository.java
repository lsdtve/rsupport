package rsupport.rsupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import rsupport.rsupport.Dto.SearchDto;
import rsupport.rsupport.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long>, QuerydslPredicateExecutor<Member> {
    List<Member> findAll();
    char countByName(String name);

    boolean existsByName(String name);

    Member findByName(String name);
}
