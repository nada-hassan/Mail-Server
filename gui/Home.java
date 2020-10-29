package gui;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.FileChooserUI;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import eg.edu.alexu.csd.datastructure.linkedList.cs76_cs88.DoublyLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.cs76_cs88.SingleLinked;
import eg.edu.alexu.csd.datastructure.queue.Queue;

import java.awt.ComponentOrientation;
import java.awt.Desktop;

import mailServer.*;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.ListSelectionModel;
import java.awt.Component;
import java.awt.Toolkit;
import javax.swing.AbstractListModel;
import javax.swing.DebugGraphics;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Home extends JFrame {

	private JPanel contentPane;
	private JTextField txtSearch;
	private JTable table;
	private JTextField recievers;
	private JTextField subject;
	private JTextField fname;
	private String email=Start.getMail();
	private Folder f=new Folder();
	private JTextField textField;
	private JTextField sub;
	private JTextField from;
	private JTextField date;
	private JTextField priority;
	private IMail[] mailsinfo;
	private int flag=0,page=1,limit=1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				   Home frame = new Home();
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
	public Home() {
		f.setMail(email);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/gui/icon.jpg")));
		setTitle("Home");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 890, 684);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton profilebutton = new JButton("");
		profilebutton.setBorderPainted(false);
		profilebutton.setBounds(10, 10, 50, 50);
		profilebutton.setBackground(new Color(245, 255, 250));
		profilebutton.setIcon(new ImageIcon(Home.class.getResource("/gui/user.jpg")));
		contentPane.add(profilebutton);
		
		JLabel lblNewLabel = new JLabel(email);
		lblNewLabel.setBounds(70, 26, 170, 21);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_2 = new JButton("Sign Out");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home.super.dispose();
				Start s = new Start();
				s.main(null);
			}
		});
		btnNewButton_2.setForeground(new Color(255, 0, 0));
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_2.setBounds(284, 10, 85, 50);
		btnNewButton_2.setBackground(new Color(230, 230, 250));
		contentPane.add(btnNewButton_2);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home.super.dispose();
				Home x = new Home();
				x.setVisible(true);
			}
		});
		btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRefresh.setForeground(new Color(0, 128, 0));
		btnRefresh.setBounds(409, 10, 85, 50);
		btnRefresh.setBackground(new Color(230, 230, 250));
		contentPane.add(btnRefresh);
		
		JComboBox search = new JComboBox();
		search.setBounds(580, 70, 85, 33);
		search.setModel(new DefaultComboBoxModel(new String[] {"Date", "Body", "Sender", "Reciever", "Subject", "Attachments"}));
		search.setSelectedIndex(0);
		contentPane.add(search);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(70, 70, 343, 33);
		txtSearch.setHorizontalAlignment(SwingConstants.LEFT);
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSearch.setText("   search");
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("According To :");
		lblNewLabel_1.setBounds(480, 60, 90, 50);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblNewLabel_1);
		
		JLayeredPane layers = new JLayeredPane();
		layers.setBounds(33, 113, 626, 524);
		contentPane.add(layers);
		layers.setLayout(new CardLayout(0, 0));

		JPanel up = new JPanel();
		layers.add(up);
		txtSearch.setEnabled(false);
		search.setEnabled(false);
		JPanel maillist = new JPanel();
		layers.add(maillist);
		maillist.setLayout(null);
		
		JButton btnNewButton_3 = new JButton("Filter");
		btnNewButton_3.setBackground(new Color(176, 224, 230));
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_3.setBounds(10, 18, 85, 37);
		maillist.add(btnNewButton_3);
		
		JComboBox sort = new JComboBox();
		sort.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sort.setModel(new DefaultComboBoxModel(new String[] {"Date", "Sender", "Reciever", "Body", "Subject", "Attachments","Priority"}));
		sort.setSelectedIndex(0);
		sort.setBounds(406, 30, 107, 21);
		maillist.add(sort);
		
		JLabel lblNewLabel_2 = new JLabel("Sort by :");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(329, 22, 67, 33);
		maillist.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel(Integer.toString(page)+" / "+Integer.toString(limit));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setBounds(244, 487, 45, 27);
		maillist.add(lblNewLabel_3);
		
		JButton next = new JButton("");

		next.setBorderPainted(false);
		next.setIcon(new ImageIcon(Home.class.getResource("/gui/n.jpg")));
		next.setBounds(299, 491, 25, 25);
		maillist.add(next);
		
		JButton prev = new JButton("");
		prev.setBorderPainted(false);
		prev.setIcon(new ImageIcon(Home.class.getResource("/gui/p.png")));
		prev.setBounds(209, 491, 25, 25);
		maillist.add(prev);
		
		JButton btnMove = new JButton("Move");
		btnMove.setBackground(new Color(176, 224, 230));
		btnMove.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnMove.setBounds(105, 18, 85, 37);
		maillist.add(btnMove);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setForeground(new Color(255, 0, 0));
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDelete.setBackground(new Color(176, 224, 230));
		btnDelete.setBounds(209, 18, 85, 37);
		maillist.add(btnDelete);
		
		
		
		JButton btnNewButton_1 = new JButton("Sort");
		btnNewButton_1.setBackground(new Color(176, 224, 230));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.setBounds(533, 30, 67, 21);
		maillist.add(btnNewButton_1);
		
		JPanel newmail = new JPanel();
		layers.add(newmail);
		newmail.setLayout(null);
		
		JComboBox pr = new JComboBox();
		pr.setForeground(Color.BLUE);
		pr.setFont(new Font("Tahoma", Font.BOLD, 16));
		pr.setModel(new DefaultComboBoxModel(new String[] {"1 (highest)", "2", "3", "4 (lowest)"}));
		pr.setSelectedIndex(0);
		pr.setBounds(84, 88, 139, 21);
		newmail.add(pr);
		
		JTextArea body = new JTextArea();
		body.setFont(new Font("Monospaced", Font.PLAIN, 17));
		body.setText("Body");
		body.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		body.setBounds(10, 119, 606, 309);
		newmail.add(body);
		
		JLabel lblNewLabel_4 = new JLabel("To  :");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(10, 10, 56, 30);
		newmail.add(lblNewLabel_4);
		
		recievers = new JTextField();
		recievers.setFont(new Font("Tahoma", Font.PLAIN, 17));
		recievers.setHorizontalAlignment(SwingConstants.LEFT);
		recievers.setText("    seprate emails by (,)");
		recievers.setBounds(86, 10, 530, 30);
		newmail.add(recievers);
		recievers.setColumns(10);
		
		subject = new JTextField();
		subject.setFont(new Font("Tahoma", Font.PLAIN, 17));
		subject.setColumns(10);
		subject.setBounds(86, 50, 530, 30);
		newmail.add(subject);
		
		JLabel lblSubject = new JLabel("Subject :");
		lblSubject.setHorizontalAlignment(SwingConstants.LEFT);
		lblSubject.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSubject.setBounds(10, 50, 66, 30);
		newmail.add(lblSubject);
		
		JLabel lblNewLabel_5 = new JLabel("Attachments :");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_5.setBounds(10, 438, 93, 31);
		newmail.add(lblNewLabel_5);
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.setFont(new Font("Dialog", Font.BOLD, 12));
		btnCancel.setBackground(new Color(176, 224, 230));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home.super.dispose();
				Home x = new Home();
				x.setVisible(true);
			}
		});
		btnCancel.setBounds(425, 493, 85, 21);
		newmail.add(btnCancel);
		

		JTextPane l= new JTextPane();
		l.setBackground(new Color(245, 245, 245));
		l.setEditable(false);
		l.setBounds(10, 467, 366, 47);
		JScrollPane scrollPane1 = new JScrollPane(l,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane1.setBounds(10, 467, 366, 47);
		newmail.add(scrollPane1);
		
		JButton btnRemove = new JButton("Remove All");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				l.setText(null);
				
			}
		});
		btnRemove.setFont(new Font("Dialog", Font.BOLD, 12));
		btnRemove.setBackground(new Color(176, 224, 230));
		btnRemove.setBounds(213, 444, 99, 21);
		newmail.add(btnRemove);
		
		JLabel lblP = new JLabel("Priority  :");
		lblP.setHorizontalAlignment(SwingConstants.LEFT);
		lblP.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblP.setBounds(10, 81, 66, 30);
		newmail.add(lblP);
		

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            // create an object of JFileChooser class 
	            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); 
	  
	            // allow multiple file selection 
	            j.setMultiSelectionEnabled(true); 
	  
	            // invoke the showsSaveDialog function to show the save dialog 
	            int r = j.showSaveDialog(null); 
	  
	            if (r == JFileChooser.APPROVE_OPTION) { 
	                // get the selelcted files 
	                 File[] files = j.getSelectedFiles(); 
	  
	                int t = 0; 
	                // set the label to the path of the selected files 
	                while (t++ < files.length) 
	                    l.setText((l.getText() + "\n" + files[t - 1]).strip()); 
	            } 
	            // if the user cancelled the operation 
	            else
	                l.setText("the user cancelled the operation"); 
			}
		});
		btnAdd.setFont(new Font("Dialog", Font.BOLD, 12));
		btnAdd.setBackground(new Color(176, 224, 230));
		btnAdd.setHorizontalAlignment(SwingConstants.LEFT);
		btnAdd.setBounds(127, 444, 66, 21);
		newmail.add(btnAdd);
		
		JButton btnNewButton_4 = new JButton("Send");
		btnNewButton_4.setFont(new Font("Dialog", Font.BOLD, 12));
		btnNewButton_4.setBackground(new Color(176, 224, 230));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App app= new App();
				Mail m = new Mail();
				 m.setbody(body.getText().replaceAll("\n", "-*-"));
				m.setFrom(email);
				m.setSubject(subject.getText());
				SingleLinked att=new SingleLinked();
				Queue r = new Queue();
				String[] str = recievers.getText().split(",");
				for(int i =0 ; i<str.length;i++) {
					r.enqueue(str[i]);
				}
				String[] files = l.getText().split("\n");
				if(files!=null) {
				for(int i=0;i<files.length;i++) {
					att.add(new File(files[i]));
				}
				m.setAttachments(att);
				}
				m.setTo(r);
				m.setPriority(pr.getSelectedIndex()+1);
				
				boolean x =app.compose(m);
				if(x) {
					JOptionPane.showMessageDialog(null, "Sent Successfully ","Send mail",JOptionPane.INFORMATION_MESSAGE);;
					Home n = new Home();
					Home.super.dispose();
					n.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "Something went wrong ","Error",JOptionPane.ERROR_MESSAGE);;
				}
			}
		});
		btnNewButton_4.setBounds(531, 493, 85, 21);
		newmail.add(btnNewButton_4);
		
		JPanel newfolder = new JPanel();
		newfolder.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		newfolder.setAlignmentX(Component.RIGHT_ALIGNMENT);
		layers.add(newfolder);
		newfolder.setLayout(null);
		
		fname = new JTextField();
		fname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fname.setHorizontalAlignment(SwingConstants.LEFT);
		fname.setBounds(208, 207, 334, 44);
		newfolder.add(fname);
		fname.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Enter Folder Name  :");
		lblNewLabel_7.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_7.setBounds(25, 204, 173, 44);
		newfolder.add(lblNewLabel_7);
		
		JButton btnNewButton_5 = new JButton("Cancel");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home.super.dispose();
				Home x = new Home();
				x.setVisible(true);
			}
		});
		btnNewButton_5.setBackground(new Color(230, 230, 250));
		btnNewButton_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_5.setForeground(new Color(255, 0, 0));
		btnNewButton_5.setBounds(334, 321, 96, 36);
		newfolder.add(btnNewButton_5);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = fname.getText();
				if(name.compareTo("")==0) {
					JOptionPane.showMessageDialog(null, "Please enter name"," Error",JOptionPane.WARNING_MESSAGE);;
				}
				else {
				try {
					f.setMail(email);
					f.create(name);
					JOptionPane.showMessageDialog(null, "Folder was created Successfully "
							,"Create Folder",JOptionPane.INFORMATION_MESSAGE);;
						Home.super.dispose();
						Home x = new Home();
						x.setVisible(true);

				}catch(RuntimeException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage()," Error",JOptionPane.WARNING_MESSAGE);;
				}
				}
			}
		});
		btnCreate.setBackground(new Color(230, 230, 250));
		btnCreate.setForeground(new Color(0, 128, 0));
		btnCreate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCreate.setBounds(446, 321, 96, 36);
		newfolder.add(btnCreate);
		
		JPanel showmail = new JPanel();
		layers.add(showmail);
		showmail.setLayout(null);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.setBackground(new Color(230, 230, 250));
		btnNewButton.setForeground(new Color(0, 0, 139));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtSearch.setEnabled(true);
				search.setEnabled(true);
				layers.removeAll();
				layers.add(maillist);
				layers.repaint();
				layers.revalidate();
			}
		});
		btnNewButton.setBounds(531, 493, 85, 21);
		
		showmail.add(btnNewButton);
		
		JLabel lblNewLabel_6 = new JLabel("Subject :");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_6.setBounds(10, 10, 71, 31);
		showmail.add(lblNewLabel_6);
		
		sub = new JTextField();
		sub.setHorizontalAlignment(SwingConstants.CENTER);
		sub.setDisabledTextColor(new Color(0, 0, 0));
		sub.setEditable(false);
		sub.setFont(new Font("Tahoma", Font.PLAIN, 18));
		sub.setBounds(91, 13, 468, 28);
		showmail.add(sub);
		sub.setColumns(10);
		
		JLabel lblFrom = new JLabel("  From :");
		lblFrom.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFrom.setBounds(10, 51, 71, 31);
		showmail.add(lblFrom);
		
		from = new JTextField();
		from.setHorizontalAlignment(SwingConstants.CENTER);
		from.setDisabledTextColor(new Color(0, 0, 0));
		from.setEditable(false);
		from.setFont(new Font("Tahoma", Font.PLAIN, 18));
		from.setColumns(10);
		from.setBounds(91, 54, 468, 28);
		showmail.add(from);
		
		JLabel lblDate = new JLabel("  Date :");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDate.setBounds(10, 100, 71, 31);
		showmail.add(lblDate);
		
		date = new JTextField();
		date.setHorizontalAlignment(SwingConstants.CENTER);
		date.setDisabledTextColor(new Color(0, 0, 0));
		date.setEditable(false);
		date.setFont(new Font("Tahoma", Font.PLAIN, 18));
		date.setColumns(10);
		date.setBounds(91, 103, 138, 28);
		showmail.add(date);
		
		JLabel lblPriority = new JLabel("  Priority :");
		lblPriority.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPriority.setBounds(340, 100, 71, 31);
		showmail.add(lblPriority);
		
		priority = new JTextField();
		priority.setHorizontalAlignment(SwingConstants.CENTER);
		priority.setDisabledTextColor(new Color(0, 0, 0));
		priority.setEditable(false);
		priority.setFont(new Font("Tahoma", Font.PLAIN, 18));
		priority.setColumns(10);
		priority.setBounds(421, 103, 138, 28);
		showmail.add(priority);
		
		JTextPane textbody = new JTextPane();
		textbody.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textbody.setDisabledTextColor(new Color(0, 0, 0));
		textbody.setEditable(false);
		textbody.setBounds(10, 141, 606, 234);
		showmail.add(textbody);
		
		JLabel lblNewLabel_8 = new JLabel("Attachments :");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_8.setBounds(10, 385, 111, 21);
		showmail.add(lblNewLabel_8);
		
		JList attlist = new JList();
		attlist.setBackground(new Color(245, 245, 245));
		attlist.setBounds(10, 417, 362, 97);
		showmail.add(attlist);
		
		
		JButton newfolderbutton = new JButton("");
		newfolderbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layers.removeAll();
				layers.add(newfolder);
				layers.repaint();
				layers.revalidate();
				txtSearch.setEnabled(false);
				search.setEnabled(false);
				
			}
		});
	
				
		
		JList list = new JList();
		JList list2 = new JList();
		//list selection
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					txtSearch.setEnabled(true);
					search.setEnabled(true);
					App a = new App();
					list2.removeAll();
					layers.removeAll();
					layers.add(maillist);
					layers.repaint();
					layers.revalidate();
					Set s = new Set();
					Folder f = new Folder();
					Sort so= new Sort();
					Filter fi=new Filter();
					so.settype((String) sort.getSelectedItem());
					f.setFolderName(((String) list.getSelectedValue()).strip());
					f.setMail(email);
					f.readIndex();
					s.setFolder(f);
					s.setSort(so);
					if(flag==1) {
						flag=0;
						fi.setSearch(search.getSelectedItem().toString(), txtSearch.getText().strip());
						txtSearch.setText(null);
						f.readIndex();
						fi.setLists(f.getBody(), f.getx());
					}
					else {
						fi=null;
					}
					s.setFilter(fi);
					a.intializeSet(s);
					limit=f.x.size()/10;
					if(f.getx().size()%10!=0) {
						limit++;
					}
					if(limit==0) {
						limit=1;
					}
					mailsinfo=a.listEmails(page);
					String[] str=a.stringmail();
					list2.setModel(new AbstractListModel() {
						String[] values =str;
					public int getSize() {
							return values.length;
						}
					public Object getElementAt(int index) {
							return values[index];
						}
				});
			
					list2.setFixedCellHeight(41);
					list2.setFont(new Font("Tahoma", Font.BOLD, 17));
					list2.setBounds(10, 65, 606, 416);
					maillist.add(list2);
				
			}

			}
		});
		list.setSelectionBackground(new Color(173, 216, 230));
		list.setFont(new Font("Tahoma", Font.BOLD, 24));
		list.setForeground(new Color(0, 191, 255));
		list.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel() {
			String[] values = f.getnames();
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		list.setFixedCellHeight(55);		
		list.setBackground(new Color(245, 245, 245));
		list.setBounds(696, 168, 170, 469);
		list.setBorder(new LineBorder(new Color(230, 230, 250), 13, true));
		
		//list2 selection
		list2.addMouseListener(new MouseAdapter() {
			@Override 
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && !e.isConsumed()) {
				     e.consume();
				     if(list2.getSelectedValue().toString().compareTo("  -  ")!=0) {
				    	Mail mail=(Mail) mailsinfo[list2.getSelectedIndex()];
				     //handle double click event.
						layers.removeAll();
						layers.add(showmail);
						layers.repaint();
						layers.revalidate();
						txtSearch.setEnabled(false);
						search.setEnabled(false);
						sub.setText(mail.getSubject());
						from.setText(mail.getFrom());
						date.setText(mail.getDate());
						priority.setText(Integer.toString(mail.getPriority()));
						textbody.setText(mail.getbody().replaceAll( "-\\*-","\n"));
						int size = mail.getAttachments().size();
						String[] att=new String[size];
						for(int i=0;i<size;i++) {
							att[i]=(String) mail.getAttachments().get(i);
						}
						attlist.setModel(new AbstractListModel() {
							String[] values = att;
							public int getSize() {
								return values.length;
							}
							public Object getElementAt(int index) {
								return values[index];
							}
						});
						
				}
				  else {
				    	 JOptionPane.showMessageDialog(null, "Select a valid mail !\nThere's no mail here","Error",JOptionPane.WARNING_MESSAGE);;
				     }
				}
			}
		});

		
		//attachment selection
		attlist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && !e.isConsumed()) {
				     e.consume();
				     try {
				    	 Mail mail =(Mail) mailsinfo[list2.getSelectedIndex()];
				    	 File fileToOpen = new File("src/contacts/"+email+"/"+list.getSelectedValue().toString().strip()+"/"+mail.getFolderName()+
				    			 "/attachments/"+attlist.getSelectedValue().toString());
				    	 		Desktop.getDesktop().open(fileToOpen);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						 JOptionPane.showMessageDialog(null, "File may be deleted or moved","Error",JOptionPane.WARNING_MESSAGE);;
					}	
				}
				}
			
		});
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page++;
				if(page<=limit) {
					int t=limit;
					int temp =list.getSelectedIndex();
					list.setSelectedIndex(temp+1);
					list.setSelectedIndex(temp-1);
					limit=t;
					list.setSelectedIndex(temp);
					layers.removeAll();
					layers.add(maillist);
					layers.repaint();
					layers.revalidate();

				}
				else {
					page--;
				}
			}
		});
		
		prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page--;
				if(page>=1&&page<limit) {
					int temp =list.getSelectedIndex();
					list.setSelectedIndex(temp+1);
					list.setSelectedIndex(temp-1);
					list.setSelectedIndex(temp);
					layers.removeAll();
					layers.add(maillist);
					layers.repaint();
					layers.revalidate();

				}
				else {
					page++;
				}
			}
		});
		
		btnMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] indices=list2.getSelectedIndices();
				if(indices.length==0) {
					 JOptionPane.showMessageDialog(null, "You didn't select ant mails\nctrl+click to select multiple mails","Error",JOptionPane.WARNING_MESSAGE);;
				}
				else {
					String[] folders=f.getnames();
					JComboBox folder = new JComboBox(folders);
				    JOptionPane.showMessageDialog(null,folder, "Destination Folder", JOptionPane.QUESTION_MESSAGE);
				    Folder des =new Folder();
				    des.setFolderName(folder.getSelectedItem().toString().strip());
					DoublyLinkedList mails=new DoublyLinkedList();
					for(int i=0;i<indices.length;i++) {
						Mail m=(Mail) mailsinfo[indices[i]];
						String x="src/contacts/"+email+"/"+list.getSelectedValue().toString().strip()+"/"+m.getFolderName();
						mails.add(x);
					}
					App a= new App();
					a.moveEmails(mails,des);
					 JOptionPane.showMessageDialog(null, "Moved Successfully","Move",JOptionPane.INFORMATION_MESSAGE);
					 Home z = new Home();
					 Home.super.dispose();
					 z.setVisible(true);
				     
				}
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] indices=list2.getSelectedIndices();
				if(indices.length==0) {
					 JOptionPane.showMessageDialog(null, "You didn't select any mails\nctrl+click to select multiple mails","Error",JOptionPane.WARNING_MESSAGE);;
				}
				else {
				DoublyLinkedList mails=new DoublyLinkedList();
				for(int i=0;i<indices.length;i++) {
					Mail m=(Mail) mailsinfo[indices[i]];
					String x="src/contacts/"+email+"/"+list.getSelectedValue().toString().strip()+"/"+m.getFolderName();
					mails.add(x);
				}
				App a= new App();
				a.deleteEmails(mails);
				 JOptionPane.showMessageDialog(null, "Deleted Successfully","Delete",JOptionPane.INFORMATION_MESSAGE);
				 Home z = new Home();
				 Home.super.dispose();
				 z.setVisible(true);
				}
			}
		});
		
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			Filter filter=new Filter();
			Folder folder=new Folder();
			Folder des = new Folder();
			App a = new App();
			String[] folders=f.getnames();
			String[] mode = {"Sender","Subject"};

			JPanel o = new JPanel();
			o.setBorder(new EmptyBorder(5, 5, 5, 5));
			o.setLayout(null);

			JLabel lblNewLabel_1 = new JLabel("Choose and Fill");
			lblNewLabel_1.setForeground(new Color(0, 0, 255));
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setBounds(113, 10, 205, 26);
			o.add(lblNewLabel_1);
			
			JLabel lblNewLabel = new JLabel("Filter According to :");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel.setBounds(10, 67, 145, 20);
			o.add(lblNewLabel);
			
			JComboBox fmode = new JComboBox(mode);
			fmode.setFont(new Font("Tahoma", Font.PLAIN, 14));
			fmode.setSelectedIndex(0);
			fmode.setBounds(165, 68, 205, 21);
			o.add(fmode);
			
			JComboBox fname = new JComboBox(folders);
			fname.setFont(new Font("Tahoma", Font.PLAIN, 14));
			fname.setSelectedIndex(0);
			fname.setBounds(165, 121, 205, 21);
			o.add(fname);
			
			JLabel lblDestinationFolder = new JLabel("Destination Folder :");
			lblDestinationFolder.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblDestinationFolder.setBounds(10, 120, 145, 20);
			o.add(lblDestinationFolder);
			
			JTextField fkey = new JTextField();
			fkey.setBounds(165, 182, 205, 20);
			o.add(fkey);
			fkey.setColumns(10);
			
			JLabel lblEnterKey = new JLabel("Enter Key      :");
			lblEnterKey.setHorizontalAlignment(SwingConstants.CENTER);
			lblEnterKey.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblEnterKey.setBounds(10, 180, 145, 20);
			o.add(lblEnterKey);
			
			JButton btnNewButton = new JButton("Ok");
			btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnNewButton.setBounds(319, 232, 85, 21);
			o.add(btnNewButton);
			
			
	        JDialog diag = new JDialog();
	        diag.getContentPane().add(o);
	        diag.setTitle("Filter");
	        diag.pack();
	        diag.setBounds(390, 200, 450, 300);
	        diag.setVisible(true);
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					folder.setFolderName(list.getSelectedValue().toString().strip());
					des.setFolderName( fname.getSelectedItem().toString().strip());
					folder.setMail(email);
					des.setMail(email);
					folder.setDes(des);
					folder.readIndex();
					des.readIndex();
					String key =fkey.getText().strip();
					if(key==null) {
						JOptionPane.showMessageDialog(null, "Enter key please","Error",JOptionPane.ERROR_MESSAGE);
					}
					else {
						filter.setFilter(fmode.getSelectedItem().toString().strip(), key);
						a.setViewingOptions(folder, filter, null);
						diag.dispose();
						JOptionPane.showMessageDialog(null, "Filterd successfully","Filter",JOptionPane.INFORMATION_MESSAGE);
						Home x =new Home();
						Home.super.dispose();
						x.setVisible(true);
					}
				}
			});
			}
		});

		JScrollPane scrollPane = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(696, 168, 170, 469);
		contentPane.add(scrollPane);
		
		JScrollPane scrollPane2 = new JScrollPane(textbody,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane2.setBounds(10, 141, 606, 234);
		showmail.add(scrollPane2);
		
		JScrollPane scrollPane3 = new JScrollPane(attlist,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane3.setBounds(10, 417, 362, 97);
		showmail.add(scrollPane3);
		
		
		newfolderbutton.setBounds(775, 113, 48, 45);
		newfolderbutton.setIcon(new ImageIcon(Home.class.getResource("/gui/newfolder.png")));
		newfolderbutton.setBackground(new Color(245, 255, 250));
		contentPane.add(newfolderbutton);
		
		JButton NewmailButton = new JButton("");
		NewmailButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layers.removeAll();
				layers.add(newmail);
				layers.repaint();
				layers.revalidate();
				txtSearch.setEnabled(false);
				search.setEnabled(false);
			}
		});
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int temp =list.getSelectedIndex();
				list.setSelectedIndex(temp+1);
				list.setSelectedIndex(temp-1);
				list.setSelectedIndex(temp);
				layers.removeAll();
				layers.add(maillist);
				layers.repaint();
				layers.revalidate();
				
			}
		});
		NewmailButton.setBounds(717, 113, 48, 45);
		NewmailButton.setIcon(new ImageIcon(Home.class.getResource("/gui/newmail.png")));
		NewmailButton.setBackground(new Color(245, 255, 250));
		contentPane.add(NewmailButton);
		
		JButton btnNewButton_6 = new JButton("GO");
		btnNewButton_6.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_6.setBackground(new Color(176, 224, 230));
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int temp=list.getSelectedIndex();
				list.setSelectedIndex(temp+1);
				list.setSelectedIndex(temp-1);
				flag=1;
				list.setSelectedIndex(temp);
				layers.removeAll();
				layers.add(maillist);
				layers.repaint();
				layers.revalidate();
			}
		});
		btnNewButton_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_6.setBounds(419, 73, 51, 27);
		contentPane.add(btnNewButton_6);

		JLabel backg=new JLabel("");
		backg.setIcon(new ImageIcon(Home.class.getResource("/gui/unnamed.jpg")));
		backg.setBounds(-12, -45, 1120, 700);
		contentPane.add(backg);
	}
	}