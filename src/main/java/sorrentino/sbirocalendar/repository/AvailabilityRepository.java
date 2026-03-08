package sorrentino.sbirocalendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sorrentino.sbirocalendar.entity.Availability;

import java.util.List;
import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {

    List<Availability> findByUser_IdAndGroup_Id(Long userId, Long groupId);
    List<Availability> findByUser_IdNotAndGroup_Id(Long userId, Long groupId);
    Optional<Availability> findByUser_IdAndDayAndGroup_Id(Long userId, Integer day, Long groupId);

}
