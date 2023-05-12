package sirin.pass.repository.pass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Entity
@Table(name = "bulk_pass")
public class BulkPassEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bulkPassSeq;
    private Long packageSeq;
    private String userGroupId;

    @Enumerated(EnumType.STRING)
    private BulkPassStatus status;
    private Integer count;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    public void setEndedAt(Integer period) {
        if (period == null) {
            return;
        }
        this.endedAt = this.startedAt.plusDays(period);
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

}
