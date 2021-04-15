
package rsupport.rsupport.repository;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import rsupport.rsupport.domain.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAll();
    Optional<Team> findByName(String name);
}
