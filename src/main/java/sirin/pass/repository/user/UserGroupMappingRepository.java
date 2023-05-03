package sirin.pass.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGroupMappingRepository extends JpaRepository<UserGroupMappingEntity, Long> {

    List<UserGroupMappingEntity> findByUserGroupId(String userGroupId);

}