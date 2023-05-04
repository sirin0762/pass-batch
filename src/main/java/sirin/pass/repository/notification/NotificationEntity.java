package sirin.pass.repository.notification;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sirin.pass.repository.BaseEntity;

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
@Table(name = "notification")
public class NotificationEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationSeq;

    private String uuid;

    private NotificationEvent event;

    private String text;

    private boolean sent;

    private LocalDateTime sentAt;

}
