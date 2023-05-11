package sirin.pass.repository.pass;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import sirin.pass.service.pass.Pass;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PassModelMapper {
    PassModelMapper INSTANCE = Mappers.getMapper(PassModelMapper.class);

    @Mapping(target = "status", qualifiedByName = "status")
    @Mapping(target = "remainingCount", source = "bulkPassEntity.count")
    PassEntity toPassEntity(BulkPassEntity bulkPassEntity, String userId);

    @Mapping(target = "packageName", source = "passEntity.packageEntity.packageName")
    Pass map(PassEntity passEntity);

    List<Pass> map(List<PassEntity> passEntities);

    @Named("status")
    default PassStatus status(BulkPassStatus status) {
        return PassStatus.READY;
    }

}
