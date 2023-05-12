package sirin.pass.repository.packaze;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface PackageRepository extends JpaRepository<PackageEntity, Long> {

    List<PackageEntity> findByCreatedAtAfter(LocalDateTime dateTime, PageRequest packageSeq);

    @Transactional
    @Modifying
    @Query(value = "UPDATE PackageEntity p"
        + "         SET    p.count = :count,"
        + "                p.period = :period"
        + "         WHERE  p.packageSeq = :packageSeq"
    )
    int updateCountAndPeriod(Long packageSeq, Integer count, Integer period);

    List<PackageEntity> findAllByOrderByPackageName();

}
