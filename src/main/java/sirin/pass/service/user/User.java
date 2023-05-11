package sirin.pass.service.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sirin.pass.repository.user.UserStatus;

@Getter
@Setter
@ToString
public class User {
    private String userId;
    private String userName;
    private UserStatus status;
}