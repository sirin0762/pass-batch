package sirin.pass.service.pass;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sirin.pass.controller.admin.BulkPassRequest;
import sirin.pass.repository.packaze.PackageEntity;
import sirin.pass.repository.packaze.PackageRepository;
import sirin.pass.repository.pass.BulkPassEntity;
import sirin.pass.repository.pass.BulkPassModelMapper;
import sirin.pass.repository.pass.BulkPassRepository;
import sirin.pass.repository.pass.BulkPassStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BulkPassService {

    private final BulkPassRepository bulkPassRepository;
    private final PackageRepository packageRepository;

    public List<BulkPass> getAllBulkPasses() {
        List<BulkPassEntity> bulkPassEntities = bulkPassRepository.findAllOrderByStartedAtDesc();
        return BulkPassModelMapper.INSTANCE.map(bulkPassEntities);
    }

    public void addBulkPass(BulkPassRequest request) {
        PackageEntity packageEntity = packageRepository.findById(request.getPackageSeq()).orElseThrow();

        BulkPassEntity bulkPassEntity = BulkPassModelMapper.INSTANCE.map(request);
        bulkPassEntity.setStatus(BulkPassStatus.READY);
        bulkPassEntity.setCount(packageEntity.getCount());
        bulkPassEntity.setEndedAt(packageEntity.getPeriod());

        bulkPassRepository.save(bulkPassEntity);
    }

}
