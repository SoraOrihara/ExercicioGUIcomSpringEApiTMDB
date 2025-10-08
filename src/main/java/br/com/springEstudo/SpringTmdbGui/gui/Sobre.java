package br.com.springEstudo.SpringTmdbGui.gui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Desktop;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.net.URI;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

public class Sobre extends JDialog {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Sobre() {
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/br/com/springEstudo/SpringTmdbGui/gui/img/home20x20.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Buscador de filmes Tmdb 1.0");
		lblNewLabel.setBounds(10, 11, 232, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Author: Caio de Oliveira");
		lblNewLabel_1.setBounds(10, 71, 115, 14);
		getContentPane().add(lblNewLabel_1);
		
		JButton btnGithub = new JButton("");
		btnGithub.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGithub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				link("https://github.com/SoraOrihara/ExercicioGUIcomSpringEApiTMDB");
			}
		});
		btnGithub.setIcon(new ImageIcon(Sobre.class.getResource("/br/com/springEstudo/SpringTmdbGui/gui/img/github20x20.png")));
		btnGithub.setBounds(23, 160, 89, 23);
		getContentPane().add(btnGithub);
		
		JLabel lblNewLabel_2 = new JLabel("Api: ");
		lblNewLabel_2.setBounds(10, 114, 46, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("https://developer.themoviedb.org/docs/getting-started");
		lblNewLabel_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				link("https://developer.themoviedb.org/docs/getting-started");
			}
		});
		lblNewLabel_3.setForeground(SystemColor.textHighlight);
		lblNewLabel_3.setBounds(55, 114, 324, 14);
		getContentPane().add(lblNewLabel_3);

	}

	private void link(String site) {
		Desktop desktop = Desktop.getDesktop();
		try {
			URI uri = new URI(site);
			desktop.browse(uri);
		}catch(Exception e) {
			System.out.print(e);
		}
	}
	
}
