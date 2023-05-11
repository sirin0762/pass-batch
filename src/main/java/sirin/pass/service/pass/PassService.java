package sirin.pass.service.pass;

import org.springframework.stereotype.Service;
import sirin.pass.repository.pass.PassEntity;
import sirin.pass.repository.pass.PassModelMapper;
import sirin.pass.repository.pass.PassRepository;

import java.util.List;

@Service
public class PassService {

    private final PassRepository passRepository;

    public PassService(PassRepository passRepository) {
        this.passRepository = passRepository;
    }

    public List<Pass> getPasses(final String userId) {
        final List<PassEntity> passEntities = passRepository.findByUserId(userId);
        return PassModelMapper.INSTANCE.map(passEntities);
    }

}
