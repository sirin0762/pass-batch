package sirin.pass.repository.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AggregatedStatistics {

    private LocalDateTime statisticsAt;
    private long allCount;
    private long attendCount;
    private long cancelledCount;

    public void merge(final AggregatedStatistics statistics) {
        this.allCount += statistics.getAllCount();
        this.attendCount += statistics.getAttendCount();
        this.cancelledCount += statistics.getCancelledCount();
    }

}
