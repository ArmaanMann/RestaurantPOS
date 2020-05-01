package com.main.tables.orders.items;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.main.Frame;
import com.main.tables.orders.NewOrder;

public class ItemsWindow extends JFrame implements ActionListener {
	
	private Frame frame;
	private NewOrder newOrder;
	private JPanel panel;
	private JTable table;
	private JScrollPane pane;
	private JButton ok, cancel;
	private ItemTableModel model;
	private int data;
	
	
	public ItemsWindow(Frame frame, NewOrder newOrder, String name, int data) {
		super(name);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(frame.getIcon());
		
		this.frame = frame;
		this.newOrder = newOrder;
		this.data = data;
		
		panel = new JPanel();
		
		add(panel);
		
		model = new ItemTableModel(frame.getData().getList(data));
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				JTable tables = (JTable) e.getSource();
				Point point = e.getPoint();
				int row = tables.getSelectedRow();
				if (e.getClickCount() == 2) {
					addItem(frame.getData().getList(data).get(row));
					dispose();
				}
			}
		});
		
		pane = new JScrollPane(table);
		pane.setPreferredSize(new Dimension(350, 300));
		
		panel.add(pane);
		
		ok = new JButton("OK");
		cancel = new JButton("Cancel");
		
		ok.addActionListener(this);
		cancel.addActionListener(this);
		
		panel.add(ok);
		panel.add(cancel);
		
		setVisible(true);
	}
	
	private void addItem(Item item) {
		String s = JOptionPane.showInputDialog("Enter a quantity");
		 int quantity = 1;
		 if (s == null) return;
		 if (!s.isEmpty()) {
			 try {
				 quantity = Integer.parseInt(s);
			 } catch (NumberFormatException e) {
				 System.out.println("Not a number");
				 quantity = 1;
			 }
			 System.out.println("quantity = "+quantity);
		 
		 }
		 item.setQuantity(quantity);
		 
		 frame.getData().getAddOrderList().add(item);
		 newOrder.setTable();
		 newOrder.setOrder(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			if (table.getSelectionModel().isSelectionEmpty() == false) {
				addItem(frame.getData().getList(data).get(table.getSelectedRow()));
				dispose();
			}
		}
		else if (e.getSource() == cancel)
			dispose();
	}

}
