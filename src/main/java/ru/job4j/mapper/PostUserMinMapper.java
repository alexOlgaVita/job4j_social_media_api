package ru.job4j.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.dto.PostUserMinDto;
import ru.job4j.model.Post;

@Mapper(componentModel = "spring")
public interface PostUserMinMapper {

    @Mapping(source = "post.user.id", target = "userId")
    @Mapping(source = "post.user.name", target = "userName")
    PostUserMinDto getModelFromEntity(Post post);
}