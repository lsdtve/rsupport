
package rsupport.rsupport.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import rsupport.rsupport.domain.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, Long> {

    @EntityGraph(attributePaths = "members")
    List<Team> findAll();

    @EntityGraph(attributePaths = "members")
    List<Team> findAll(Sort sort);

    Optional<Team> findByName(String name);
}
