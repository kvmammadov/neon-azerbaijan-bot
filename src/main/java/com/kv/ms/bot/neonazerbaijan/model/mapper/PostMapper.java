package com.kv.ms.bot.neonazerbaijan.model.mapper;


import com.kv.ms.bot.neonazerbaijan.dao.entity.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mappings({
            @Mapping(target = "post_id", source = "id"),
            @Mapping(target = "profile_id", constant = "5022499404471247"),
    })
    PostEntity toEntity(String id);
}
