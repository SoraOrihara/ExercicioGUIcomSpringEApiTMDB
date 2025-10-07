package br.com.springEstudo.SpringTmdbGui.gui;



import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import org.springframework.stereotype.Component;

import br.com.springEstudo.SpringTmdbGui.business.FilmeService;
@Component
public class TelaDadosCinema extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final FilmeService filmeService;


	/**
	 * Create the frame.
	 */
	public TelaDadosCinema(FilmeService filmeService) {
		this.filmeService=filmeService;
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaDadosCinema.class.getResource("/br/com/springEstudo/SpringTmdbGui/gui/img/home20x20.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 531);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

	}

}
