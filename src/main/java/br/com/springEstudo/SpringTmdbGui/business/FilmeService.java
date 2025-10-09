package br.com.springEstudo.SpringTmdbGui.business;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.springEstudo.SpringTmdbGui.config.TmdbProperties;
import br.com.springEstudo.SpringTmdbGui.dto.FilmeDto;
import br.com.springEstudo.SpringTmdbGui.dto.TmdbSearchResponseDto;

@Service
public class FilmeService {

	private final TmdbProperties tmdbProperties;
	
	private final RestTemplate restTemplate = new RestTemplate();
	private final String BASE_URL = "https://api.themoviedb.org/3/search/movie";
	// Construtor para injeção do TmdbProperties
    public FilmeService(TmdbProperties tmdbProperties) {
        this.tmdbProperties = tmdbProperties;
    }
	public Optional<FilmeDto> buscarFilme(String nomeFilme){
		String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
				.queryParam("api_key", tmdbProperties.getKey())
				.queryParam("query",nomeFilme)
				.queryParam("language", "pt-BR")
				.toUriString();
		
		try {
			TmdbSearchResponseDto result= restTemplate.getForObject(url, TmdbSearchResponseDto.class);
			if(result!=null&&result.results()!=null&&!result.results().isEmpty()) {
				return Optional.of(result.results().get(0));
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Optional.empty();
		
	}
	
	public List<FilmeDto> buscarSugestoesFilmes(String nomeFilme){
		String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
				.queryParam("api_key", tmdbProperties.getKey())
				.queryParam("query",nomeFilme)
				.queryParam("language", "pt-BR")
				.toUriString();
		
		try {
			TmdbSearchResponseDto result= restTemplate.getForObject(url, TmdbSearchResponseDto.class);
			if(result!=null&&result.results()!=null&&!result.results().isEmpty()) {
				return result.results().stream().limit(4).collect(Collectors.toList());
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.emptyList();
		
	}
}
