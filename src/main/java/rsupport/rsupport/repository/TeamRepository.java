package rsupport.rsupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rsupport.rsupport.domain.Team;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAll();
    Team findByName(String name);
    Boolean existsByName(String name);
}
