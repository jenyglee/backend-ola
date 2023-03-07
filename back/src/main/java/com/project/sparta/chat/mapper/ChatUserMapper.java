package com.project.sparta.chat.mapper;

import com.project.sparta.chat.dto.ChatUserDto;
import com.project.sparta.chat.entity.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ChatUserMapper {
    ChatUserMapper INSTANCE = Mappers.getMapper(ChatUserMapper.class);

    ChatUserDto toDto(Chat e);
    Chat toEntity(ChatUserDto d);
}
