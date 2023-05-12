package sirin.pass.service.packaze;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sirin.pass.repository.packaze.PackageEntity;
import sirin.pass.repository.packaze.PackageModelMapper;
import sirin.pass.repository.packaze.PackageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PackageService {

    private final PackageRepository packageRepository;

    public List<Package> getAllPackages() {
        List<PackageEntity> packageEntities = packageRepository.findAllByOrderByPackageName();
        return PackageModelMapper.INSTANCE.map(packageEntities);
    }

}
