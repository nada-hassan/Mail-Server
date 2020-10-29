package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mailServer.*;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Sign_up extends JFrame {

	private JPanel contentPane;
	private JTextField fname;
	private JTextField lname;
	private JTextField email;
	private JTextField username;
	private JTextField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sign_up frame = new Sign_up();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Sign_up() {
		setFont(new Font("Elephant", Font.BOLD, 15));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sign_up.class.getResource("/gui/icon.jpg")));
		setTitle("Sign Up");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 130, 614, 591);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		fname = new JTextField();
		fname.setForeground(Color.BLACK);
		fname.setBounds(196, 116, 225, 31);
		contentPane.add(fname);
		fname.setColumns(10);
		
		JLabel l1 = new JLabel("First Name");
		l1.setForeground(Color.BLUE);
		l1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 15));
		l1.setBounds(76, 116, 93, 31);
		contentPane.add(l1);
		
		lname = new JTextField();
		lname.setForeground(Color.BLACK);
		lname.setColumns(10);
		lname.setBounds(196, 193, 225, 31);
		contentPane.add(lname);
		
		JLabel l = new JLabel("Last Name");
		l.setForeground(Color.BLUE);
		l.setFont(new Font("Yu Gothic Medium", Font.BOLD, 15));
		l.setBounds(76, 195, 93, 31);
		contentPane.add(l);
		
		email = new JTextField();
		email.setForeground(Color.BLACK);
		email.setColumns(10);
		email.setBounds(196, 270, 225, 31);
		contentPane.add(email);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.BLUE);
		lblEmail.setFont(new Font("Yu Gothic Medium", Font.BOLD, 15));
		lblEmail.setBounds(76, 272, 93, 31);
		contentPane.add(lblEmail);
		
		username = new JTextField();
		username.setForeground(Color.BLACK);
		username.setColumns(10);
		username.setBounds(196, 347, 225, 31);
		contentPane.add(username);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.BLUE);
		lblUsername.setFont(new Font("Yu Gothic Medium", Font.BOLD, 15));
		lblUsername.setBounds(76, 349, 93, 31);
		contentPane.add(lblUsername);
		
		password = new JTextField();
		password.setForeground(Color.BLACK);
		password.setColumns(10);
		password.setBounds(196, 424, 225, 31);
		contentPane.add(password);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.BLUE);
		lblPassword.setFont(new Font("Yu Gothic Medium", Font.BOLD, 15));
		lblPassword.setBounds(76, 426, 93, 31);
		contentPane.add(lblPassword);
		
		JButton btnNewButton = new JButton("Sign up");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Contact x = new Contact();
				App a = new App();
				x.setfirstname(fname.getText());
				x.setlastname(lname.getText());
				x.setname(username.getText());
				x.setemail(email.getText());
				x.setpassword(password.getText());
				try {
				boolean z = a.signup(x);
				if(z) {
					JOptionPane.showMessageDialog(null, "Signed Up Successfully ","Sign Up",JOptionPane.INFORMATION_MESSAGE);;
					//frame.dispose();
					Start s = new Start();
					s.frmMailServer.setVisible(true);
					Sign_up.super.dispose();
				}
				}catch(RuntimeException e1){
					JOptionPane.showMessageDialog(null, e1.getMessage(),"Sign Up Error",JOptionPane.WARNING_MESSAGE);;
				}
				
			}
		});
		btnNewButton.setForeground(new Color(0, 0, 128));
		btnNewButton.setBackground(new Color(176, 224, 230));
		btnNewButton.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnNewButton.setBounds(258, 483, 93, 40);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Registiration Form");
		lblNewLabel.setForeground(new Color(0, 0, 205));
		lblNewLabel.setFont(new Font("Viner Hand ITC", Font.BOLD, 23));
		lblNewLabel.setBounds(196, 31, 225, 47);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("*");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBounds(50, 270, 19, 31);
		contentPane.add(lblNewLabel_1);
		
		JLabel label = new JLabel("*");
		label.setForeground(Color.RED);
		label.setFont(new Font("Tahoma", Font.BOLD, 18));
		label.setBounds(50, 344, 19, 31);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("*");
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_1.setBounds(50, 424, 19, 31);
		contentPane.add(label_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Sign_up.class.getResource("/gui/signupbavk.jpg")));
		lblNewLabel_2.setBounds(-69, 0, 849, 600);
		contentPane.add(lblNewLabel_2);
	}
}
