package sirin.pass.job.pass;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import sirin.pass.repository.pass.BulkPassEntity;
import sirin.pass.repository.pass.BulkPassRepository;
import sirin.pass.repository.pass.BulkPassStatus;
import sirin.pass.repository.pass.PassEntity;
import sirin.pass.repository.pass.PassModelMapper;
import sirin.pass.repository.pass.PassRepository;
import sirin.pass.repository.user.UserGroupMappingEntity;
import sirin.pass.repository.user.UserGroupMappingRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AddPassesTasklet implements Tasklet {

    private final PassRepository passRepository;
    private final BulkPassRepository bulkPassRepository;
    private final UserGroupMappingRepository userGroupMappingRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        final LocalDateTime startedAt = LocalDateTime.now().minusDays(1);
        final List<BulkPassEntity> bulkPassEntities = bulkPassRepository.findByStatusAndStartedAtGreaterThan(BulkPassStatus.READY, startedAt);

        int count = 0;
        for(BulkPassEntity bulkPassEntity: bulkPassEntities) {
            final List<String> userIds = userGroupMappingRepository.findByUserGroupId(bulkPassEntity.getUserGroupId())
                .stream()
                .map(UserGroupMappingEntity::getUserId)
                .toList();

            count += addPasses(bulkPassEntity, userIds);

            bulkPassEntity.setStatus(BulkPassStatus.COMPLETED);
        }


        return null;
    }

    private int addPasses(BulkPassEntity bulkPassEntity, List<String> userIds) {
        List<PassEntity> passEntities = new ArrayList<>();
        for (String userId: userIds) {
            PassEntity passEntity = PassModelMapper.INSTANCE.toPassEntity(bulkPassEntity, userId);
            passEntities.add(passEntity);
        }
        return passRepository.saveAll(passEntities).size();
    }

}
