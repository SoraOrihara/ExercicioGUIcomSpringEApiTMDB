package br.com.springEstudo.SpringTmdbGui.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FilmeDto(String title, String overview, @JsonProperty("release_date") String releaseDate,
		@JsonProperty("poster_path") String posterPath) {

}
