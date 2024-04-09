package com.api.soundsurf.music.domain;

import com.api.soundsurf.iam.dto.GenreDto;
import com.api.soundsurf.iam.dto.SessionUser;
import com.api.soundsurf.iam.entity.UserGenre;
import com.api.soundsurf.music.entity.Genre;
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
    public GenreDto.GetAll.Response getAllGenres() {
        final var genres = businessService.getAllGenres();

        return new GenreDto.GetAll.Response(
                genres.stream()
                        .map(genre -> new GenreDto.GetAll.Genre(genre.getId(), genre.getName(), genre.getDescription()))
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public GenreDto.Select.Response selectGenre(final SessionUser sessionUser, final GenreDto.Select.Request requestDto) {
        List<UserGenre> userGenres = businessService.selectGenre(sessionUser.getUserId(), requestDto);

        return new GenreDto.Select.Response(
                userGenres.stream()
                        .map(userGenre -> userGenre.getGenre().getName())
                        .toList()
        );
    }
}
