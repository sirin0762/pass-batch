package sirin.pass.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sirin.pass.repository.user.UserEntity;
import sirin.pass.repository.user.UserModelMapper;
import sirin.pass.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(final String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        return UserModelMapper.INSTANCE.toUser(userEntity);
    }

}
