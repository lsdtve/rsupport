package rsupport.addressbook.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import rsupport.addressbook.domain.Team;

public interface TeamRepository extends CrudRepository<Team, Long> {

    Optional<Team> findByName(String name);
}
