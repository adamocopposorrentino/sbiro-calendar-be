package sorrentino.sbirocalendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sorrentino.sbirocalendar.entity.Availability;

import java.util.List;
import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
    List<Availability> findByUser_Id(Long userId);
    List<Availability> findByUser_IdNot(Long userId);
    Optional<Availability> findByUser_idAndDay(Long userId, Integer day);

}
