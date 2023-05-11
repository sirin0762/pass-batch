package sirin.pass.service.pass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sirin.pass.repository.pass.PassStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Pass {

    private Long passSeq;
    private Long packageSeq;
    private String packageName;
    private String userId;

    private PassStatus status;
    private Integer remainingCount;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDateTime expiredAt;

}