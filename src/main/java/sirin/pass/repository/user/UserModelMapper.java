package sirin.pass.repository.user;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import sirin.pass.service.user.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserModelMapper {

    UserModelMapper INSTANCE = Mappers.getMapper(UserModelMapper.class);

    User toUser(UserEntity userEntity);

}
