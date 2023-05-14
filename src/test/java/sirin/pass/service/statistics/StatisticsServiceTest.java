package sirin.pass.service.statistics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sirin.pass.repository.statistic.AggregatedStatistics;
import sirin.pass.repository.statistic.StatisticsRepository;
import sirin.pass.utils.LocalDateTimeUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {

    @Mock
    public StatisticsRepository statisticsRepository;

    @InjectMocks
    public StatisticsService statisticsService;

    @Nested
    @DisplayName("통계 데이터를 기반으로 차트 만들기")
    class MakeChartData {

        final LocalDateTime to = LocalDateTime.of(2023, 5,  10, 0, 0);

        @Test
        @DisplayName("통계 데이터가 있을 때")
        void test_makeChartData_when_hasStatistics() {
            // given
            List<AggregatedStatistics> statistics = List.of(new AggregatedStatistics(to, 2L, 1L, 1L));

            // when
            when(statisticsRepository.findByStatisticsAtBetweenAndGroupBy(eq(to.minusDays(10)), eq(to))).thenReturn(statistics);
            final ChartData chartData = statisticsService.makeChartData(to);

            // then
            verify(statisticsRepository, times(1)).findByStatisticsAtBetweenAndGroupBy(eq(to.minusDays(10)), eq(to));
            assertThat(chartData.getLabels()).isEqualTo(List.of(LocalDateTimeUtils.format(to, LocalDateTimeUtils.MM_DD)));
            assertThat(chartData.getAttendedCounts()).isEqualTo(List.of(1L));
            assertThat(chartData.getCanceledCounts()).isEqualTo(List.of(1L));
        }

        @Test
        @DisplayName("통계 데이터가 없을 때")
        void test_makeChartData_when_NotHasStatistics() {
            // given
            List<AggregatedStatistics> statistics = List.of(new AggregatedStatistics(to, 2L, 1L, 1L));

            // when
            when(statisticsRepository.findByStatisticsAtBetweenAndGroupBy(eq(to.minusDays(10)), eq(to))).thenReturn(Collections.emptyList());
            final ChartData chartData = statisticsService.makeChartData(to);

            // then
            verify(statisticsRepository, times(1)).findByStatisticsAtBetweenAndGroupBy(any(LocalDateTime.class), any(LocalDateTime.class));
            assertThat(chartData.getLabels()).isEqualTo(Collections.emptyList());
            assertThat(chartData.getAttendedCounts()).isEqualTo(Collections.emptyList());
            assertThat(chartData.getCanceledCounts()).isEqualTo(Collections.emptyList());
        }
    }

}