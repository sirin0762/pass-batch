package sirin.pass.repository.pass;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import sirin.pass.controller.admin.BulkPassRequest;
import sirin.pass.service.pass.BulkPass;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BulkPassModelMapper {

    BulkPassModelMapper INSTANCE = Mappers.getMapper(BulkPassModelMapper.class);

    List<BulkPass> map(List<BulkPassEntity> bulkPassEntities);

    BulkPassEntity map(BulkPassRequest request);

}
