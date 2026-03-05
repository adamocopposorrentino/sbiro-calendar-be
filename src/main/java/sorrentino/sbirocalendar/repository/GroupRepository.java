package sorrentino.sbirocalendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sorrentino.sbirocalendar.entity.Group;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByName(String name);
}
