package com.main.tables.orders;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.main.Frame;
import com.main.tables.Table;
import com.main.tables.orders.items.OrderItemTableModel;

public class ModifyOrder extends JFrame implements ActionListener {
	
	private Frame frame;
	private Table tableMain;
	private JPanel panelLeft, panelRight;
	private JList list;
	private DefaultListModel<String> listModel;
	private JTable table;
	private JScrollPane paneL, paneR;
	private OrderItemTableModel model;
	private JButton ok, edit, remove, removeOrder;
	private int index = 0;
	
	public ModifyOrder(Frame frame, Table tableMain) {
		super("Modify Order - "+tableMain.getTableNum());
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(frame.fWidth, frame.fHeight - 150);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(1, 1));
		setIconImage(frame.getIcon());
		
		this.frame = frame;
		this.tableMain = tableMain;
		this.tableMain.setModify(this);
		
		panelLeft = new JPanel();
		panelRight = new JPanel();
		
		add(panelLeft);
		add(panelRight);
		
		//LEFT PANEL FIRST
		
		setListModel();
		
		list = new JList<>(listModel);
		list.setSelectedIndex(0);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				refreshTables(list.getSelectedIndex());
			}
			
		});
		
		paneL = new JScrollPane(list);
		paneL.setPreferredSize(new Dimension(200, 250));
		
		panelLeft.add(paneL);
		
		ok = new JButton("Close");
		ok.addActionListener(this);
		
		removeOrder = new JButton("Remove Order");
		removeOrder.addActionListener(this);
		
		panelLeft.add(removeOrder);
		panelLeft.add(ok);
		
		//RIGHT PANEL
		model = new OrderItemTableModel(tableMain.getOrder(index));
		
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				JTable tables = (JTable) e.getSource();
				Point point = e.getPoint();
				int row = tables.rowAtPoint(point);
				if (e.getClickCount() == 2) {
					edit();
				}
			}
		});
		
		paneR = new JScrollPane(table);
		paneR.setPreferredSize(new Dimension(frame.fWidth / 2 - 20, 250));
		
		panelRight.add(paneR);
		
		edit = new JButton("Edit Quantity");
		remove = new JButton("Remove");
		
		edit.addActionListener(this);
		remove.addActionListener(this);
		
		panelRight.add(edit);
		panelRight.add(remove);
		
		setVisible(true);
	}
	
	private void setListModel() {
		listModel = new DefaultListModel<>();
		for (int i = 0; i < tableMain.getOrdersAmount(); i++)
			listModel.addElement("Order "+(i+1));

		if (paneL != null) {
		list = new JList<>(listModel);
		list.setSelectedIndex(index);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				refreshTables(list.getSelectedIndex());
			}
			
		});
		paneL.setViewportView(list);
		}
			
	}
	
	private void refreshTables(int index) {
		this.index = index;
		try {
		model = new OrderItemTableModel(tableMain.getOrder(index));
		} catch (NullPointerException e) {
			System.out.println("No current orders on table");
		}
		table = new JTable(model);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				JTable tables = (JTable) e.getSource();
				Point point = e.getPoint();
				int row = tables.rowAtPoint(point);
				if (e.getClickCount() == 2) {
					edit();
				}
			}
		});
		frame.refreshTable();
		paneR.setViewportView(table);
	}
	
	public void setIndex(int index) {
		System.out.println("index was changed");
		this.index = (index > 0 ? index : 0);
	}
	
	public int getIndex() {
		return index;
	}
	
	private void edit() {
		if (list.isSelectionEmpty() == false) {
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
		 System.out.println("quantity = "+quantity);
		 tableMain.editQuantity(table.getSelectedRow(), list.getSelectedIndex(), quantity);
		 refreshTables(index);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			dispose();
		} else if (e.getSource() == edit) {
			edit();
		} else if (e.getSource() == remove) {
			if (table.getSelectionModel().isSelectionEmpty() == false) {
				tableMain.removeOrder(index, table.getSelectedRow());
				refreshTables(index);
				setListModel();
			}
		} else if (e.getSource() == removeOrder) {
			tableMain.removeAllOrder(index);
			refreshTables(index);
			setListModel();
		} 
	}

}
