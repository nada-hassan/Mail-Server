package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import mailServer.App;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Scrollbar;
import java.awt.ScrollPane;

public class Start {
	protected JFrame frmMailServer;
	private JTextField username;
	private JPasswordField passwordField;
	private static String mail;
	public void setMail(String x) {
		mail=x;
	}
	public static String getMail() {
		return mail;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start window = new Start();
					window.frmMailServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Start() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		App a = new App();
		frmMailServer = new JFrame();
		frmMailServer.setIconImage(Toolkit.getDefaultToolkit().getImage(Start.class.getResource("/gui/icon.jpg")));
		frmMailServer.setFont(new Font("Elephant", Font.BOLD, 15));
		frmMailServer.setTitle("Mail Server");
		frmMailServer.setBounds(400, 130, 759, 640);
		frmMailServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMailServer.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Register now !");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmMailServer.dispose();
				Sign_up z = new Sign_up();
				z.setVisible(true);
			}
		});
		btnNewButton.setForeground(new Color(0, 0, 205));
		btnNewButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 12));
		btnNewButton.setBounds(363, 525, 130, 13);
		frmMailServer.getContentPane().add(btnNewButton);
		
		username = new JTextField();
		username.setBounds(245, 275, 264, 44);
		frmMailServer.getContentPane().add(username);
		username.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(245, 345, 264, 44);
		frmMailServer.getContentPane().add(passwordField);
		
		JLabel usericon = new JLabel("");
		usericon.setIcon(new ImageIcon(Start.class.getResource("/gui/user.jpg")));
		usericon.setBounds(175, 275, 45, 40);
		frmMailServer.getContentPane().add(usericon);
		
		JLabel passicon = new JLabel("");
		passicon.setIcon(new ImageIcon(Start.class.getResource("/gui/pass.jpg")));
		passicon.setBounds(186, 345, 29, 40);
		frmMailServer.getContentPane().add(passicon);
		
		JButton login = new JButton("Login");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = username.getText(),
						password= passwordField.getText();

				
				boolean x = a.signin(email, password);
				if(x) {
					setMail(email);
					frmMailServer.dispose();
					Home z = new Home();
					z.setVisible(true);
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Invalid Login Details !","Login Error",JOptionPane.ERROR_MESSAGE);;
					username.setText(null);
					passwordField.setText(null);
				}
						
			}
			
		});
		login.setBackground(new Color(176, 224, 230));
		login.setFont(new Font("Wide Latin", Font.BOLD, 12));
		login.setBounds(245, 431, 102, 31);
		frmMailServer.getContentPane().add(login);
		
		JButton Reset = new JButton("Reset");
		Reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username.setText(null);
				passwordField.setText(null);
			}
		});
		Reset.setFont(new Font("Wide Latin", Font.BOLD, 12));
		Reset.setBackground(new Color(176, 224, 230));
		Reset.setBounds(407, 431, 102, 31);
		frmMailServer.getContentPane().add(Reset);
		
		JLabel msg = new JLabel("You don't have an account ?");
		msg.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		msg.setBounds(185, 511, 184, 40);
		frmMailServer.getContentPane().add(msg);
		
		JLabel mail = new JLabel("");
		mail.setIcon(new ImageIcon(Start.class.getResource("/gui/tenor.gif")));
		mail.setBounds(114, -128, 498, 498);
		frmMailServer.getContentPane().add(mail);
		
		JLabel back = new JLabel("");
		back.setIcon(new ImageIcon(Start.class.getResource("/gui/back.png")));
		back.setBounds(-11, 0, 934, 602);
		frmMailServer.getContentPane().add(back);
	}
}
