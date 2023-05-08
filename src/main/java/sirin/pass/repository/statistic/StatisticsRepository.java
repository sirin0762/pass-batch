package sirin.pass.repository.statistic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<StatisticsEntity, Long> {

    @Query(value = "SELECT new sirin.pass.repository.statistic.AggregatedStatistics("
        + "             s.statisticsAt,"
        + "             SUM(s.allCount), "
        + "             SUM(s.attendedCount), "
        + "             SUM(s.cancelledCount) "
        + "         )"
        + "         FROM    StatisticsEntity s "
        + "         WHERE   s.statisticsAt BETWEEN :from AND :to"
        + "         GROUP BY s.statisticsAt")
    List<AggregatedStatistics> findByStatisticsAtBetweenAndGroupBy(@Param("from") LocalDateTime from, LocalDateTime to);

}
