package br.com.springEstudo.SpringTmdbGui;

import java.awt.EventQueue;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import br.com.springEstudo.SpringTmdbGui.gui.TelaDadosCinema;

@SpringBootApplication
@ComponentScan(basePackages="br.com.springEstudo.SpringTmdbGui")
public class EstudoGuiComSpringBootETmdbApplication {

	public static void main(String[] args) {
		 // Inicia o contexto do Spring de uma forma que não presume um ambiente web (headless(false))
        ConfigurableApplicationContext context = new SpringApplicationBuilder(EstudoGuiComSpringBootETmdbApplication.class)
        		 .web(WebApplicationType.NONE)
                .headless(false)
                .run(args);

        // Pede para a fila de eventos do Swing para criar e mostrar a nossa GUI
        EventQueue.invokeLater(() -> {
            // Pega o nosso JFrame do contexto do Spring (que já tem o serviço injetado)
            TelaDadosCinema ex = context.getBean(TelaDadosCinema.class);
            ex.setVisible(true);
        });
	}

}
