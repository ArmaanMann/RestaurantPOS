package com.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.metal.DefaultMetalTheme;

import com.main.tables.Bill;
import com.main.tables.NewTable;
import com.main.tables.Table;
import com.main.tables.orders.ModifyOrder;
import com.main.tables.orders.NewOrder;
import com.main.tables.orders.items.Item;


public class Frame extends JFrame {
	
	private final static String RESTAURANT_NAME = "Restaurant POS System - Armaan Mann";
	public final static int fWidth = 750, fHeight = 500;
	public static int TABLE_NUMS = 10;
	private static Frame f;
	private static Data d;
	private JPanel leftPanel, rightPanel, rUpPanel, rDownPanel;
	private JButton newTable, orderAdd, orderModify, orderBill, history, reports, settings, remove;
	private JList<Table> tableList;
	private DefaultListModel<Table> listModel;
	private JScrollPane tablePane;
	private Action a;
	private NewTable newT;
	private NewOrder newOrder;
	private boolean empty = true;
	private int categories = 0;
	
	public Frame() { //setting up the main frame
		super(RESTAURANT_NAME);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(fWidth, fHeight - 100);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(getIcon());
		
		
		setLayout(new GridLayout(1,1));
		
		leftPanel = new JPanel();
		rightPanel = new JPanel(new GridLayout(2, 1));

		add(leftPanel);
		add(rightPanel);
		
		Action a = new Action();
		
		
		//RIGHT PANEL
		rUpPanel = new JPanel(new GridBagLayout());
		rDownPanel = new JPanel();
		
		rightPanel.add(rUpPanel);
		rightPanel.add(rDownPanel);
		
		newTable = new JButton("New Table");
		newTable.setPreferredSize(new Dimension(200, 75));

		newTable.addActionListener(a);
		
		rUpPanel.add(newTable);
		
		history = new JButton("Past Orders");
		history.setPreferredSize(new Dimension(125, 40));
		//setButton(history);
		reports = new JButton("Reports");
		reports.setPreferredSize(new Dimension(125, 40));
		settings = new JButton("Settings");
		settings.setPreferredSize(new Dimension(125, 30));
		
		history.addActionListener(a);
		reports.addActionListener(a);
		settings.addActionListener(a);
		
		JLabel ewlabel = new JLabel();
		ewlabel.setPreferredSize(new Dimension(250, 40));
		
		rDownPanel.add(history);
		rDownPanel.add(reports);
		rDownPanel.add(ewlabel);
		rDownPanel.add(settings);
		
		

	    //LEFT PANEL
		listModel = new DefaultListModel<>();
        listModel.addElement(new Table("Empty", "Django", ""));
        List<Item> wow = new ArrayList<Item>();
        wow.add(new Item("101","Big Mac","0","$4.99"));
        wow.add(new Item("201","Simple Fries","0","$0.99"));
        wow.add(new Item("301","Coke","Large","$1.49"));
        wow.add(new Item("301","Coke","Medium","$1.25"));
        listModel.get(0).addOrder(wow);
 
        tableList = new JList<>(listModel);
        tablePane = new JScrollPane(tableList);
        tablePane.setPreferredSize(new Dimension(300, 250));
        
        JLabel tableTitles = new JLabel("Current Tables");
        tableList.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JList table = (JList) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.locationToIndex(point);
                if (mouseEvent.getClickCount() == 2) {
                   	handle();
                }
            }
        });
        tableTitles.setFont(new Font("Verdana", Font.BOLD, 15));
        tableList.setSelectedIndex(0);
        
        orderAdd = new JButton("Add Order");
        orderModify = new JButton("Modify Order");
        orderBill = new JButton("Print Bill");
        remove = new JButton("Remove Table");
        
        leftPanel.add(tableTitles);
        leftPanel.add(tablePane);
        leftPanel.add(orderAdd);
        leftPanel.add(orderModify);
        leftPanel.add(orderBill);
        leftPanel.add(remove);
        
        orderAdd.addActionListener(a);
		orderModify.addActionListener(a);
		orderBill.addActionListener(a);
		remove.addActionListener(a);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				getData().savePastTables();
				dispose();
				System.out.println("Closing Application");
			}
		});

		setVisible(true);
	}
	
	public void addTable(String tableName, String name, String num) {
		if (empty) {
			listModel.removeAllElements();
			empty = false;
		}
		try {
		Integer.parseInt(num);
		} catch (NumberFormatException e) {
			num = "";
		}
		
		listModel.addElement(new Table(tableName, name, num));
		tableList.setSelectedIndex(0);
	}
	
	public int getTables() {
		return TABLE_NUMS;
	}
	
	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()) {
				if (info.getName() == "Nimbus") {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
				else if (info.getName() == "Windows") {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
				
			}
		} catch (Exception e) {}
		f = new Frame();
		f.loadData();
	}
	
	private boolean isEmpty() {
		if (tableList.isSelectionEmpty())
			tableList.setSelectedIndex(0);
		return !empty && !tableList.isSelectionEmpty();
	}
	
	public int getCategories() {
		return categories;
	}
	
	public void setCategories(int categories) {
		this.categories = categories;
	}
	
	public void refreshTable() {
		
		if (listModel.getSize() <= 0) {
		listModel = new DefaultListModel<>();
        listModel.addElement(new Table("Empty", "", ""));
        empty = true;
		}
        tableList = new JList<>(listModel);
        tableList.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JList table = (JList) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.locationToIndex(point);
                if (mouseEvent.getClickCount() == 2) {
                   	handle();
                }
            }
        });
        tablePane.setViewportView(tableList);
        tableList.setSelectedIndex(0);
        //System.out.println("model size = "+listModel.getSize()+" list size = "+tableList.getSelectedValues().length);
	}
	
	public void setButton(JButton b) {
		  b.setBackground(new Color(59, 89, 182));
	        b.setForeground(Color.WHITE);
	        b.setFocusPainted(false);
	        b.setFont(new Font("Tahoma", Font.BOLD, 12));
	}
	
	//handles all txt file bs
	public void loadData() {
		d = new Data(f);
		d.loadPastTables();
	}
	
	public Data getData() {
		return d;
	}

	public Image getIcon() {
		ImageIcon icon = new ImageIcon("posicon.png");
		return icon.getImage();
	}
	
	private boolean fileExists() {
		File temp = new File("pastFile");
		System.out.println(temp.exists());
		return temp.exists();
	}
	
	private void addTable() {
		if (isEmpty()) {
			newOrder = new NewOrder(f, tableList.getSelectedValue());
		}
	}
	
	private void modifyTable() {
		ModifyOrder modify = new ModifyOrder(f, tableList.getSelectedValue());
	}
	
	private void handle() {
		if (isEmpty()) {
			System.out.println(tableList.getSelectedValue()+"");
			if (tableList.getSelectedValue().getOrdersAmount() > 0)
				modifyTable();
			else 
				addTable();
		}
	}
	
	public void setTableNo(String num) {
		int test = TABLE_NUMS;
		
		try {
		test = Integer.parseInt(num);
		} catch (NumberFormatException e) {
			System.out.println("NOT A NUMBER");
		}
		if (empty == false && test < TABLE_NUMS) {
			JOptionPane.showMessageDialog(f, "You must clear out all tables currently in use. Or add a higher amount.");
			return;
		}
		
		TABLE_NUMS = test;
	}
	
	public boolean getEmpty() {
		return empty;
	}
	
	private class Action implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == newTable) {
				newT = new NewTable(f, listModel);
			}
			else if (e.getSource() == orderAdd) {
				addTable();
			} 
			else if (e.getSource() == orderModify) {
				handle();
			} 
			else if (e.getSource() == orderBill) {
				if (isEmpty())
					if (tableList.getSelectedValue().getOrdersAmount() > 0) {
					Bill bill = new Bill(f, tableList.getSelectedValue());
					int test = tableList.getSelectedIndex();
					getData().addTable(tableList.getSelectedValue());
					listModel.remove(test);
					refreshTable();
					}
			}
			else if (e.getSource() == history) {
				if (getData().getPastTables().size() > 0) {
					History history = new History(f);
				}
			}
			else if (e.getSource() == reports) {
				Reports report = new Reports(f);
			} 
			else if (e.getSource() == settings) {
				Settings settings = new Settings(f);
			}
			else if (e.getSource() == remove) {
				if (isEmpty()) {
					if (tableList.getSelectedValue().getOrdersAmount() > 0) {
						JOptionPane.showMessageDialog(f, "Please remove all orders from the table via modify order first!");
						return;
					}
					listModel.remove(tableList.getSelectedIndex());
					refreshTable();
				}
			}
		}	
	}
}