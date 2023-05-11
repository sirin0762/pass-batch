package sirin.pass.repository.pass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sirin.pass.repository.BaseEntity;
import sirin.pass.repository.packaze.PackageEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "pass")
public class PassEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passSeq;

    private Long packageSeq;

    private String userId;

    @Enumerated(EnumType.STRING)
    private PassStatus status;

    private Integer remainingCount;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private LocalDateTime expiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packageSeq", insertable = false, updatable = false)
    private PackageEntity packageEntity;

}
