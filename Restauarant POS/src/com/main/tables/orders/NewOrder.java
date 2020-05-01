package com.main.tables.orders;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.main.Frame;
import com.main.tables.Table;
import com.main.tables.orders.items.Item;
import com.main.tables.orders.items.ItemTableModel;
import com.main.tables.orders.items.ItemsWindow;
import com.main.tables.orders.items.OrderItemTableModel;


public class NewOrder extends JFrame {
	
	private Frame frame;
	private NewOrder newOrder;
	private JPanel leftP, rightP;
	private JScrollPane pane;
	private JButton remove, ok, edit;
	private JButton[] buttons;
	private OrderItemTableModel model;
	private JTable table;
	private List<String> buttonsList;
	private ButtonAction bAction;
	private Table tableMain;
	private boolean order;
	private Action a;
	
	public NewOrder(Frame frame, Table tableMain) {
		super("Add Order - "+tableMain.getTableNum());
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(frame.fWidth, frame.fHeight);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(frame.getIcon());
		
		this.frame = frame;
		this.tableMain = tableMain;
		newOrder = this;
		order = false;
		a = new Action();
		
		buttonsList = frame.getData().getButtons();
		bAction = new ButtonAction();
		
		setLayout(new GridLayout(1, 1));
		
		
		leftP = new JPanel();
		rightP = new JPanel(new GridLayout(5, 5));
		
		add(leftP);
		add(rightP);
		
		//LEFT PANEL

		model = new OrderItemTableModel(frame.getData().getAddOrderList());
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				JTable tables = (JTable) e.getSource();
				Point point = e.getPoint();
				int row = tables.getSelectedRow();
				if (e.getClickCount() == 2) 
					editItem();
			}
		});
		
		pane = new JScrollPane(table);
		pane.setPreferredSize(new Dimension(frame.fWidth / 2 - 20, frame.fHeight - 100));
		
		remove = new JButton("Remove Item");
		edit = new JButton("Edit Item");
		ok = new JButton("OK");
		
		leftP.add(pane);
		leftP.add(ok);
		leftP.add(edit);
		leftP.add(remove);
		
		ok.addActionListener(a);
		edit.addActionListener(a);
		remove.addActionListener(a);
		
		//RIGHT PANEL
		buttons = new JButton[frame.getCategories()];
		
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(buttonsList.get(i));
			rightP.add(buttons[i]);
			buttons[i].addActionListener(bAction);
		}
		setVisible(true); 	
	}
	
	public JTable getTable() {
		return table;
	}
	
	public void setTable() {
		if (model.getRowCount() == 0)
			order = false;
		model.fireTableDataChanged();
		frame.refreshTable();
	}
	
	public void setTable(int num) {
		model.fireTableDataChanged();
		table.setRowSelectionInterval(num, num);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				JTable tables = (JTable) e.getSource();
				Point point = e.getPoint();
				int row = tables.getSelectedRow();
				if (e.getClickCount() == 2) 
					editItem();
			}
		});
		
		pane.setViewportView(table);
		frame.refreshTable();
	}
	
	public void setOrder(boolean bool) {
		order = bool;
	}
	
	private void editItem() {
		if (table.getSelectionModel().isSelectionEmpty() == false) {
			 String s = JOptionPane.showInputDialog("Enter a quantity");
			 int quantity = 1;
			 if (s == null)
				 return; 
			 if (!s.isEmpty())
			 try {
				 quantity = Integer.parseInt(s);
			 } catch (NumberFormatException a) {
				 System.out.println("Not a number");
				 quantity = 1;
			 }
			frame.getData().getAddOrderList().get(table.getSelectedRow()).setQuantity(quantity);
			setTable(table.getSelectedRow());
		}
	}
	
	
	private class ButtonAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < buttons.length; i++) 
			if (e.getSource() == buttons[i]) {
				ItemsWindow iWindow = new ItemsWindow(frame, newOrder, buttonsList.get(i), i);
			}
			
		}
		
	}
	
	private class Action implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == ok) {
				if (order) {
					tableMain.addOrder(frame.getData().getAddOrderList());
					frame.getData().clearOrderList();
					setTable();
				}
				dispose();
			}
			else if (e.getSource() == remove) {
				if (table.getSelectionModel().isSelectionEmpty() == false) {
						frame.getData().getAddOrderList().remove(table.getSelectedRow());
						setTable();
				}
			}
			else if (e.getSource() == edit) {
				editItem();
			}
		}
		
	}
}
