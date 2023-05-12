package sirin.pass.repository.pass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BulkPassRepository extends JpaRepository<BulkPassEntity, Long> {

    List<BulkPassEntity> findByStatusAndStartedAtGreaterThan(BulkPassStatus status, LocalDateTime startedAt);

    @Query(value = "SELECT  b "
        + "         FROM    BulkPassEntity b "
        + "         ORDER BY b.startedAt DESC")
    List<BulkPassEntity> findAllOrderByStartedAtDesc();

}
