package sirin.pass.repository.packaze;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import sirin.pass.service.packaze.Package;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PackageModelMapper {

    PackageModelMapper INSTANCE = Mappers.getMapper(PackageModelMapper.class);

    List<Package> map(List<PackageEntity> packageEntities);

}
