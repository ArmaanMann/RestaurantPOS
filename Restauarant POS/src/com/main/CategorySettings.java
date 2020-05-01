package com.main;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.main.tables.orders.items.Item;
import com.main.tables.orders.items.ItemSettings;
import com.main.tables.orders.items.ItemTableModel;

public class CategorySettings extends JFrame {
	
	private Frame frame;
	private CategorySettings catSet;
	private int category = 0;
	private int line = 0;
	private JTable table;
	private ItemTableModel model;
	private JPanel panel;
	private JScrollPane pane;
	private JButton add, remove, edit;
	private Action a;
	private List<Item> itemList;
	
	public CategorySettings(Frame frame, int cat) {
		super("Category - "+frame.getData().getButtons().get(cat));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 450);
		setResizable(false);
		setIconImage(frame.getIcon());
		setLocationRelativeTo(null);
		
		this.frame = frame;
		this.category = cat;
		catSet = this;
		
		a = new Action();
		
		panel = new JPanel();
		
		add(panel);
		
		//model = new ItemTableModel(frame.getData().getList(cat));
		model = new ItemTableModel(setList(frame.getData().getList(cat)));
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
		pane.setPreferredSize(new Dimension(400, 350));
		
		panel.add(pane);
		
		add = new JButton("Add Item");
		remove = new JButton("Remove Item");
		edit = new JButton("Edit Item");
		
		add.addActionListener(a);
		remove.addActionListener(a);
		edit.addActionListener(a);
		
		panel.add(add);
		panel.add(remove);
		panel.add(edit);
		
		
		
		setVisible(true);
	}
	
	private List<Item> setList(List<Item> items) {
		itemList = new ArrayList<Item>();
		itemList.clear();
		String id = "";
		Item item = null;
		for (int i = 0; i < items.size(); i++) {
			if (id != items.get(i).getId()) {
				if (item != null)
					itemList.add(item);
				id = items.get(i).getId();
				item = new Item(items.get(i).getId(),items.get(i).getName(),items.get(i).getSize(),items.get(i).getPrice());
				if (i == items.size() - 1) 
					itemList.add(item);
			}
			else {
				item.setSize(item.getSize()+", "+items.get(i).getSize());
				item.setPrice(item.getPrice()+", "+items.get(i).getPrice());
				if (i == items.size() - 1) 
					itemList.add(item);
			}
		}
		return itemList;
	}
	
	public int getCategory() {
		return category;
	}
	
	public int getLine() {
		return line;
	}
	
	private void editItem() {
		if (table.getSelectionModel().isSelectionEmpty() == false) {
			line = table.getSelectedRow();
			ItemSettings itemSetting = new ItemSettings(frame, catSet, itemList.get(line));
		}
	}
	
	private class Action implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == add) {
				ItemSettings itemSetting = new ItemSettings(frame, catSet, null);
			}
			else if (e.getSource() == remove) {
				if (table.getSelectionModel().isSelectionEmpty() == false) {
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to remove "+table.getValueAt(table.getSelectedRow(), 1)+"? This cannot be undone.","Warning", JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_OPTION) {
					try {
						frame.getData().removeItem(category, table.getSelectedRow());
						refresh();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					}
				}
			}
			else if (e.getSource() == edit) {
				editItem();
			}
		}
		
	}
	
	public void refresh() {
		frame.loadData();
		CategorySettings s = new CategorySettings(frame, category);
		dispose();
	}
	
	
}
