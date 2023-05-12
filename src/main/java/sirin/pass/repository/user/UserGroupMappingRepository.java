package sirin.pass.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserGroupMappingRepository extends JpaRepository<UserGroupMappingEntity, Long> {

    List<UserGroupMappingEntity> findByUserGroupId(String userGroupId);

    @Query(value = "SELECT  u.userGroupId "
        + "         FROM    UserGroupMappingEntity u "
        + "         ORDER BY u.userGroupId")
    List<String> findDistinctUserGroupId();

}
