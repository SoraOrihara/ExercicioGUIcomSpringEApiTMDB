package br.com.springEstudo.SpringTmdbGui.gui;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.springframework.stereotype.Component;

import br.com.springEstudo.SpringTmdbGui.business.FilmeService;
import br.com.springEstudo.SpringTmdbGui.dto.FilmeDto;

@Component
public class TelaDadosCinema extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final FilmeService filmeService;
	private JTextField txtNomeFilme;
	private JButton btnBuscar;
	private JLabel lblCheck;
	private JLabel lblPoster;
	private JTextArea txtAreaSinopse;
	private JLabel lblNota;
	private JLabel lblTituloFilme;
	private JLabel lblNomeFilme;

	/**
	 * Create the frame.
	 */
	public TelaDadosCinema(FilmeService filmeService) {
		setResizable(false);
		this.filmeService = filmeService;
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				TelaDadosCinema.class.getResource("/br/com/springEstudo/SpringTmdbGui/gui/img/home20x20.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 531);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.lblNomeFilme = new JLabel("Nome do filme:");
		lblNomeFilme.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNomeFilme.setBounds(10, 32, 96, 14);
		contentPane.add(lblNomeFilme);

		this.txtNomeFilme = new JTextField();
		txtNomeFilme.setText("");
		txtNomeFilme.setBounds(116, 30, 217, 20);
		contentPane.add(txtNomeFilme);
		txtNomeFilme.setColumns(10);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(244, 57, 89, 23);
		contentPane.add(btnBuscar);

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarFilme();

			}
		});

		this.lblTituloFilme = new JLabel("Titulo do Filme");
		lblTituloFilme.setBounds(41, 91, 441, 35);
		contentPane.add(lblTituloFilme);

		this.lblNota = new JLabel("Nota:");
		lblNota.setBounds(41, 130, 143, 14);
		contentPane.add(lblNota);

		this.txtAreaSinopse = new JTextArea();
		txtAreaSinopse.setText("Sinopse");
		txtAreaSinopse.setEditable(false);
		txtAreaSinopse.setLineWrap(true);
		txtAreaSinopse.setWrapStyleWord(true);
		txtAreaSinopse.setBounds(41, 155, 211, 241);
		contentPane.add(txtAreaSinopse);

		this.lblPoster = new JLabel("");
		lblPoster.setBounds(298, 155, 200, 311);
		contentPane.add(lblPoster);

		JButton btnSobre = new JButton("");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.setBackground(SystemColor.control);
		btnSobre.setIcon(new ImageIcon(
				TelaDadosCinema.class.getResource("/br/com/springEstudo/SpringTmdbGui/gui/img/about20x20.png")));
		btnSobre.setBounds(489, 30, 20, 20);
		contentPane.add(btnSobre);

		lblCheck = new JLabel("");
		lblCheck.setBounds(349, 16, 48, 48);
		contentPane.add(lblCheck);

	}

	public void buscarFilme() {
		String nomeFilme = txtNomeFilme.getText();
		if (nomeFilme == null || nomeFilme.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor, digite o nome de um filme", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Optional<FilmeDto> filmeOpt = filmeService.buscarFilme(nomeFilme);

		if (filmeOpt.isPresent()) {
			FilmeDto filme = filmeOpt.get();
			lblTituloFilme.setText("Titulo: " + filme.title() + "(" + filme.releaseDate().substring(0, 4) + ")");
			lblNota.setText("Nota: " + filme.voteAverage());
			txtAreaSinopse.setText(filme.overview());
			carregarImagem(filme.posterPath());
			lblCheck.setIcon(
					new ImageIcon(getClass().getResource("/br/com/springEstudo/SpringTmdbGui/gui/img/check48x48.png")));

		} else {
			JOptionPane.showMessageDialog(this, "Filme não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
			limparCampos();
		}

	}

	public void carregarImagem(String imgPath) {
		if (imgPath.isEmpty() || imgPath == null) {
			lblPoster.setIcon(null);
			lblPoster.setText("Sem imagem");
			return;
		}

		try {
			// Constrói a URL completa da imagem.
			// A URL base "https://image.tmdb.org/t/p/w200" é fornecida pela documentação do
			// TMDB.
			// "w200" especifica que queremos uma imagem com 200 pixels de largura.
			// Concatenamos com o posterPath (ex: "/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg").
			String urlImagem = "https://image.tmdb.org/t/p/w200" + imgPath;
			URL urlImage = new URL(urlImagem);

			//// A MÁGICA ACONTECE AQUI: ImageIO.read(url) se conecta à internet,
			// baixa os dados da imagem e os decodifica em um objeto BufferedImage.
			BufferedImage bfImage = ImageIO.read(urlImage);

			// Redimensiona a imagem baixada para que ela caiba perfeitamente
			// no tamanho do nosso JLabel (lblPoster).
			// - lblPoster.getWidth() e .getHeight() pegam as dimensões atuais do
			// componente.
			// - Image.SCALE_SMOOTH é um algoritmo que prioriza a qualidade da imagem
			// redimensionada.
			Image imagemRedimencionada = bfImage.getScaledInstance(lblPoster.getWidth(), lblPoster.getHeight(),
					Image.SCALE_SMOOTH);

			// Cria um ImageIcon a partir da nossa imagem já redimensionada. JLabels
			// só aceitam 'Icons' para exibir imagens.
			// Define o ícone do JLabel, efetivamente exibindo a imagem na tela.
			lblPoster.setIcon(new ImageIcon(imagemRedimencionada));
			lblPoster.setText("");
		} catch (IOException e) {
			System.out.println("Erro ao carregar a imagem");
		}

	}

	public void limparCampos() {
		lblTituloFilme.setText("TItulo do filme");
		txtAreaSinopse.setText("Sinopse...");
		lblNota.setText("Nota: ");
		lblPoster.setText("");
		lblPoster.setIcon(null);
	}
}
