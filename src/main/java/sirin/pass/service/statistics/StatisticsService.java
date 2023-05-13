package sirin.pass.service.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sirin.pass.repository.statistic.AggregatedStatistics;
import sirin.pass.repository.statistic.StatisticsRepository;
import sirin.pass.utils.LocalDateTimeUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public ChartData makeChartData(LocalDateTime to) {
        final LocalDateTime from = to.minusDays(10);

        List<AggregatedStatistics> statisticsEntities = statisticsRepository.findByStatisticsAtBetweenAndGroupBy(from, to);
        List<String> labels = new ArrayList<>();
        List<Long> attendedCount = new ArrayList<>();
        List<Long> canceledCount = new ArrayList<>();

        for (AggregatedStatistics statistics: statisticsEntities) {
            labels.add(LocalDateTimeUtils.format(statistics.getStatisticsAt(), LocalDateTimeUtils.MM_DD));
            attendedCount.add(statistics.getAttendCount());
            canceledCount.add(statistics.getCancelledCount());
        }

        return new ChartData(labels, attendedCount, canceledCount);
    }

}
