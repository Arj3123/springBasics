package com.example.postgres.Mappers.impl;

import com.example.postgres.Mappers.Mapper;
import com.example.postgres.domain.Author;
import com.example.postgres.domain.dto.AuthorDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements Mapper<Author, AuthorDto> {

    private ModelMapper modelMapper;

    public AuthorMapperImpl(ModelMapper modelMapper){
        this.modelMapper=modelMapper;
    }

    @Override
    public AuthorDto mapto(Author authorEntity) {
       return modelMapper.map(authorEntity, AuthorDto.class);
    }

    @Override
    public Author mapfrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, Author.class);
    }
}
