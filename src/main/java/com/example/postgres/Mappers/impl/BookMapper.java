package com.example.postgres.Mappers.impl;

import com.example.postgres.Mappers.Mapper;
import com.example.postgres.domain.BookEntity;
import com.example.postgres.domain.dto.BookDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements Mapper<BookEntity, BookDto> {
    private ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public BookDto mapto(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapfrom(BookDto bookDto) {
        return modelMapper.map(bookDto,BookEntity.class);
    }
}
