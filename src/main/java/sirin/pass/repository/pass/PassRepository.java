package sirin.pass.repository.pass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PassRepository extends JpaRepository<PassEntity, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE  PassEntity p "
        + "         SET     p.remainingCount = :remainingCount,"
        + "                 p.modifiedAt = CURRENT_TIMESTAMP"
        + "         WHERE   p.passSeq = :passSeq")
    int updateRemainingCount(Long passSeq, Integer remainingCount);

    @Query(value = "SELECT  p "
        + "         FROM    PassEntity p "
        + "         JOIN FETCH p.packageEntity "
        + "         WHERE   p.userId = :userId "
        + "         ORDER BY p.endedAt desc nulls first ")
    List<PassEntity> findByUserId(@Param("userId") String userId);
}
