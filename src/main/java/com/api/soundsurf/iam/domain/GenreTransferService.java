package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.dto.GenreDto;
import com.api.soundsurf.iam.entity.Genre;
import com.api.soundsurf.iam.entity.UserGenre;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreTransferService {
    private final GenreBusinessService businessService;

    @Transactional
    public List<GenreDto.GetAll.Response> getAllGenres() {
        List<Genre> genres = businessService.getAllGenres();
        return genres.stream().map(genre -> new GenreDto.GetAll.Response(genre.getId(), genre.getImage(), genre.getName(), genre.getDescription())).collect(Collectors.toList());
    }

    @Transactional
    public List<GenreDto.Select.Response> selectGenre(final List<GenreDto.Select.Request> requestDtos) {
        List<UserGenre> userGenres = businessService.selectGenre(requestDtos);
        return userGenres.stream().map(userGenre -> new GenreDto.Select.Response(userGenre.getGenre().getName())).collect(Collectors.toList());
    }
}
