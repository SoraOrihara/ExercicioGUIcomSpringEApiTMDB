package br.com.springEstudo.SpringTmdbGui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// Diz ao Spring para mapear todas as propriedades que começam com "tmdb.api"
@ConfigurationProperties(prefix = "tmdb.api")
@Component // Torna esta classe um Bean do Spring
public class TmdbProperties {

	// O Spring procurará por 'tmdb.api.key'
	private String key;

	// Getters e Setters
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
