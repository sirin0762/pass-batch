package sirin.pass.service.pass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sirin.pass.repository.pass.BulkPassStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BulkPass {

    private Long bulkPassSeq;
    private String userGroupId;
    private Integer count;
    private BulkPassStatus status;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

}
