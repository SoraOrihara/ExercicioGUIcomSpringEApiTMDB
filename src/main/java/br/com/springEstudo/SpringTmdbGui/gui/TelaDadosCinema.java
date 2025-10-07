package br.com.springEstudo.SpringTmdbGui.gui;



import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import org.springframework.stereotype.Component;

import br.com.springEstudo.SpringTmdbGui.business.FilmeService;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
@Component
public class TelaDadosCinema extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final FilmeService filmeService;
	private JTextField txtNomeFilme;


	/**
	 * Create the frame.
	 */
	public TelaDadosCinema(FilmeService filmeService) {
		setResizable(false);
		this.filmeService=filmeService;
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaDadosCinema.class.getResource("/br/com/springEstudo/SpringTmdbGui/gui/img/home20x20.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 531);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNomeFilme = new JLabel("Nome do filme:");
		lblNomeFilme.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNomeFilme.setBounds(10, 32, 96, 14);
		contentPane.add(lblNomeFilme);
		
		txtNomeFilme = new JTextField();
		txtNomeFilme.setText("");
		txtNomeFilme.setBounds(116, 30, 217, 20);
		contentPane.add(txtNomeFilme);
		txtNomeFilme.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(343, 29, 89, 23);
		contentPane.add(btnBuscar);
		
		JLabel lblTituloFilme = new JLabel("Titulo do Filme");
		lblTituloFilme.setBounds(41, 91, 143, 20);
		contentPane.add(lblTituloFilme);
		
		JLabel lblNota = new JLabel("Nota:");
		lblNota.setBounds(41, 130, 143, 14);
		contentPane.add(lblNota);
		
		JTextArea txtAreaSinopse = new JTextArea();
		txtAreaSinopse.setText("Sinopse");
		txtAreaSinopse.setEditable(false);
		txtAreaSinopse.setLineWrap(true);
		txtAreaSinopse.setWrapStyleWord(true);
		txtAreaSinopse.setBounds(41, 155, 211, 241);
		contentPane.add(txtAreaSinopse);
		
		JLabel lblPoster = new JLabel("");
		lblPoster.setBounds(299, 91, 200, 311);
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
		btnSobre.setIcon(new ImageIcon(TelaDadosCinema.class.getResource("/br/com/springEstudo/SpringTmdbGui/gui/img/about20x20.png")));
		btnSobre.setBounds(489, 30, 20, 20);
		contentPane.add(btnSobre);

	}
}
