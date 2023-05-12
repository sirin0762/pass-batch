package sirin.pass.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sirin.pass.repository.user.UserGroupMappingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGroupMappingService {

    private final UserGroupMappingRepository userGroupMappingRepository;

    public List<String> getAllUserGroupIds() {
        return userGroupMappingRepository.findDistinctUserGroupId();
    }

}
