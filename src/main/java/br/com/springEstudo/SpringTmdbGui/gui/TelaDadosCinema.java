package br.com.springEstudo.SpringTmdbGui.gui;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
	private JPopupMenu popupSugestoes; // <-- NOSSO NOVO CAMPO
	private Timer debounceTimer; // <-- TIMER PARA O "DEBOUNCE"

	/**
	 * Create the frame.
	 */
	public TelaDadosCinema(FilmeService filmeService) {
		setResizable(false);
		this.filmeService = filmeService;
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				TelaDadosCinema.class.getResource("/br/com/springEstudo/SpringTmdbGui/gui/img/home20x20.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 728, 531);
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
		// Inicializa o popup menu
		popupSugestoes = new JPopupMenu();

		// Configura o Timer para esperar 300ms antes de buscar
		// O listener do timer vai chamar a busca de sugestões
		debounceTimer = new Timer(500, e -> buscarSugestoesBackground());
		debounceTimer.setRepeats(false); // Para executar apenas uma vez por pausa na digitação

		txtNomeFilme.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				// Sempre que o usuario digita algo, reinicia o timer
				debounceTimer.restart();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// Sempre que o usuario digita algo, reinicia o timer
				debounceTimer.restart();

			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}

		});

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(423, 29, 89, 23);
		contentPane.add(btnBuscar);

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
				buscarFilme();

			}
		});

		this.lblTituloFilme = new JLabel("Titulo do Filme");
		lblTituloFilme.setBounds(41, 91, 502, 35);
		contentPane.add(lblTituloFilme);

		this.lblNota = new JLabel("Nota:");
		lblNota.setBounds(41, 130, 143, 14);
		contentPane.add(lblNota);

		this.txtAreaSinopse = new JTextArea();
		txtAreaSinopse.setText("Sinopse");
		txtAreaSinopse.setEditable(false);
		txtAreaSinopse.setLineWrap(true);
		txtAreaSinopse.setWrapStyleWord(true);
		txtAreaSinopse.setBounds(41, 155, 333, 241);
		contentPane.add(txtAreaSinopse);

		this.lblPoster = new JLabel("");
		lblPoster.setBounds(440, 137, 200, 311);
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
		btnSobre.setBounds(629, 30, 20, 20);
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
		lblCheck.setIcon(null);
	}
	
	/**
	 * Este método é chamado pelo Timer. Ele inicia a busca em uma nova Thread
	 * para não congelar a interface do usuário.
	 */
	public void buscarSugestoesBackground() {
		String termo = txtNomeFilme.getText();

		if (termo.trim().length() <= 3) {// Esconde o popup se o texto for muito curto
			popupSugestoes.setVisible(false);
			return;
		}
		// Usa uma nova Thread para fazer a chamada de API fora da Event Dispatch Thread (EDT)
		new Thread(() -> {
			List<FilmeDto> sugestoes = filmeService.buscarSugestoesFilmes(termo);
			// Depois que a busca terminar, volta para a EDT para atualizar a interface
			EventQueue.invokeLater(() -> {
				atualizarPopupSugestoes(sugestoes);
			});

		}).start();

	}

	public void atualizarPopupSugestoes(List<FilmeDto>sugestoes) {
		popupSugestoes.removeAll();
		
		if(sugestoes.isEmpty()) {
			popupSugestoes.setVisible(false);
			return;
		}
		//Para cada filme na lista de sugestão
		for(FilmeDto filme: sugestoes) {
			//Cria um item de menu com o titulo e o ano do filme
			String txtItem=filme.title();
			String txtItemMenu=filme.title()+"("+filme.releaseDate().substring(0,4)+")";
			JMenuItem menuItem = new JMenuItem(txtItemMenu);
			//Adicionar uma ação par quando o usuario clicar no item
			menuItem.addActionListener(e->{
				//Colocar na caixa de texto
				txtNomeFilme.setText(txtItem);
				//esconder o popup de sugestões
				popupSugestoes.setVisible(false);
				//buscar o filme escolhido
				buscarFilme();
			});
			popupSugestoes.add(menuItem);
		}
		 // Mostra o popup logo abaixo do campo de texto
	    popupSugestoes.show(txtNomeFilme, 0, txtNomeFilme.getHeight());
	    
	    
	    // Força o foco de volta para o campo de texto para o usuário continuar digitando se quiser
	    txtNomeFilme.requestFocusInWindow();
	}
}
