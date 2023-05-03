package sirin.pass.repository.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user_group_mappping")
@IdClass(UserGroupMappingId.class)
public class UserGroupMappingEntity {

    @Id
    private String userGroupId;

    @Id
    private String userId;

    private String userGroupName;
    private String description;

}
