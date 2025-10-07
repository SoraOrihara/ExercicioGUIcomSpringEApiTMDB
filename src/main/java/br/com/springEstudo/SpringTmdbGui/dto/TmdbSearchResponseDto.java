package br.com.springEstudo.SpringTmdbGui.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TmdbSearchResponseDto(List<FilmeDto> results) {
}
