package sirin.pass.repository.statistic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sirin.pass.repository.booking.BookingEntity;
import sirin.pass.repository.booking.BookingStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "statistics")
public class StatisticsEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statisticsSeq;

    private LocalDateTime statisticsAt;

    private int allCount;

    private int attendedCount;

    private int cancelledCount;

    public static StatisticsEntity create(final BookingEntity bookingEntity) {
        StatisticsEntity statisticsEntity = new StatisticsEntity();
        statisticsEntity.setStatisticsAt(bookingEntity.getStatisticsAt());
        statisticsEntity.setAllCount(1);

        if (bookingEntity.isAttended()) {
            statisticsEntity.setAttendedCount(1);
        }

        if (BookingStatus.CANCELLED.equals(bookingEntity.getStatus())) {
            statisticsEntity.setCancelledCount(1);
        }

        return statisticsEntity;
    }

    public void add(final BookingEntity bookingEntity) {
        this.allCount++;
        if (bookingEntity.isAttended()) this.attendedCount++;
        if (BookingStatus.CANCELLED.equals(bookingEntity.getStatus())) this.cancelledCount++;
    }

}
