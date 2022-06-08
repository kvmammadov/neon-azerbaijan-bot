package com.kv.ms.bot.neonazerbaijan.model.mapper;


import com.kv.ms.bot.neonazerbaijan.client.response.Data;
import com.kv.ms.bot.neonazerbaijan.dao.entity.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mappings({
            @Mapping(target = "postId", source = "id"),
            @Mapping(target = "isPublished", constant = "true"),
            @Mapping(target = "profileId", constant = "5362713430462517")
    })
    PostEntity toEntity(Data data);
}
