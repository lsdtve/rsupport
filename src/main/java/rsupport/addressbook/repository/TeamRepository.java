
package rsupport.addressbook.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import rsupport.addressbook.domain.Team;

public interface TeamRepository extends CrudRepository<Team, Long> {

    @EntityGraph(attributePaths = "members")
    List<Team> findAll(Sort sort);

    Optional<Team> findByName(String name);
}
