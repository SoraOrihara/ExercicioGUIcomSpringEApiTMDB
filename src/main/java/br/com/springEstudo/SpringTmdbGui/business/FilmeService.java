package br.com.springEstudo.SpringTmdbGui.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.springEstudo.SpringTmdbGui.dto.FilmeDto;
import br.com.springEstudo.SpringTmdbGui.dto.TmdbSearchResponseDto;

@Service
public class FilmeService {

	@Value("${tmdb.api.key}")
	private String API_KEY;
	
	private final RestTemplate restTemplate = new RestTemplate();
	private final String BASE_URL = "https://api.themoviedb.org/3/search/movie";
	
	public Optional<FilmeDto> buscarFilme(String nomeFilme){
		String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
				.queryParam("api_key", API_KEY)
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
	
}
