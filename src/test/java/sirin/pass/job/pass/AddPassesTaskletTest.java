package sirin.pass.job.pass;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import sirin.pass.repository.pass.BulkPassEntity;
import sirin.pass.repository.pass.BulkPassRepository;
import sirin.pass.repository.pass.BulkPassStatus;
import sirin.pass.repository.pass.PassEntity;
import sirin.pass.repository.pass.PassRepository;
import sirin.pass.repository.pass.PassStatus;
import sirin.pass.repository.user.UserGroupMappingEntity;
import sirin.pass.repository.user.UserGroupMappingRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class AddPassesTaskletTest {

    @Mock
    private StepContribution stepContribution;

    @Mock
    private ChunkContext chunkContext;

    @Mock
    private PassRepository passRepository;

    @Mock
    private BulkPassRepository bulkPassRepository;

    @Mock
    private UserGroupMappingRepository userGroupMappingRepository;

    @InjectMocks
    private AddPassesTasklet addPassesTasklet;

    @Test
    public void test_execute() throws Exception {
        // given
        String userGroupId = "GROUP";
        long bulkPassSeq = 1L;
        long packageSeq = 1L;
        int count = 10;

        final BulkPassEntity bulkPassEntity = new BulkPassEntity();
        bulkPassEntity.setBulkPassSeq(bulkPassSeq);
        bulkPassEntity.setPackageSeq(packageSeq);
        bulkPassEntity.setStatus(BulkPassStatus.READY);
        bulkPassEntity.setUserGroupId(userGroupId);
        bulkPassEntity.setCount(count);
        bulkPassEntity.setStartedAt(LocalDateTime.now());
        bulkPassEntity.setEndedAt(LocalDateTime.now());

        String userId = "A000000";
        final UserGroupMappingEntity userGroupMappingEntity = new UserGroupMappingEntity();
        userGroupMappingEntity.setUserId(userId);
        userGroupMappingEntity.setUserGroupId(userGroupId);

        // when
        when(bulkPassRepository.findByStatusAndStartedAtGreaterThan(eq(BulkPassStatus.READY), any(LocalDateTime.class))).thenReturn(List.of(bulkPassEntity));
        when(userGroupMappingRepository.findByUserGroupId(eq(userGroupId))).thenReturn(List.of(userGroupMappingEntity));

        RepeatStatus repeatStatus = addPassesTasklet.execute(stepContribution, chunkContext);

        // then
        assertThat(repeatStatus).isEqualTo(RepeatStatus.FINISHED);

        ArgumentCaptor<List> passEntitiesCaptor = ArgumentCaptor.forClass(List.class);
        verify(passRepository, times(1)).saveAll(passEntitiesCaptor.capture());
        verify(bulkPassRepository).findByStatusAndStartedAtGreaterThan(eq(BulkPassStatus.READY), any(LocalDateTime.class));
        verify(userGroupMappingRepository).findByUserGroupId(eq(userGroupId));

        final List<PassEntity> passEntities = passEntitiesCaptor.getValue();
        assertThat(passEntities.size()).isEqualTo(1);

        final PassEntity passEntity = passEntities.get(0);
        assertThat(passEntity.getPackageSeq()).isEqualTo(packageSeq);
        assertThat(passEntity.getStatus()).isEqualTo(PassStatus.READY);
        assertThat(passEntity.getRemainingCount()).isEqualTo(count);
        assertThat(passEntity.getUserId()).isEqualTo(userId);

    }


}