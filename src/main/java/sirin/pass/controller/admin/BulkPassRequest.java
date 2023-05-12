package sirin.pass.controller.admin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sirin.pass.utils.LocalDateTimeUtils;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BulkPassRequest {

    private Long packageSeq;
    private String userGroupId;
    private LocalDateTime startedAt;

    public void setStartedAt(String startedAtString) {
        this.startedAt = LocalDateTimeUtils.parse(startedAtString);
    }

}