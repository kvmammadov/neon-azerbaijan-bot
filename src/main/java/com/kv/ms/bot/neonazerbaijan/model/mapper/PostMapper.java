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
            @Mapping(target = "postId", source = "data.id"),
            @Mapping(target = "isPublished", source = "isPublished"),
            @Mapping(target = "profileId", source = "profileId")
    })
    PostEntity toEntity(Data data, Boolean isPublished, String profileId);
}