package com.main.tables.orders.items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.main.CategorySettings;
import com.main.Frame;

public class ItemSettings extends JFrame {
	
	private Frame frame;
	private CategorySettings catSet;
	private JPanel panel;
	private JTextField id, name, size, price;
	private JButton ok, cancel;
	private String itemString;
	private int amount = 0;
	private Item item;
	private Action a;
	
	public ItemSettings(Frame frame, CategorySettings catSet, Item item) {
		super((item == null) ? "Add " : "Edit "+ "Item");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 250);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(frame.getIcon());
		
		this.frame = frame;
		this.catSet = catSet;
		this.item = item;
		
		a = new Action();
		
		panel = new JPanel();
		add(panel);
		
		JLabel label1 = new JLabel("Use the following text fields, to add or edit your item.");
		JLabel label2 = new JLabel("To add new sizes separate by a comma and add another price to correspond.      ");
		
		panel.add(label1);
		panel.add(label2);
		
		JLabel idLabel = new JLabel("Enter ID");
		JLabel nameLabel = new JLabel("Enter Name");
		JLabel sizeLabel = new JLabel("Enter Size(s)");
		JLabel priceLabel = new JLabel("Enter Price(s)");
		
		id = new JTextField(13);
		name = new JTextField(13);
		size = new JTextField(13);
		price = new JTextField(13);
		
		if (item != null) {
			id.setText(item.getId());
			name.setText(item.getName());
			size.setText(item.getSize());
			price.setText(item.getPrice());
			amount = item.getAmount();
		}

		panel.add(idLabel);
		panel.add(id);
		panel.add(nameLabel);
		panel.add(name);
		panel.add(sizeLabel);
		panel.add(size);
		panel.add(priceLabel);
		panel.add(price);
		
		ok = new JButton("OK");
		cancel = new JButton("Cancel");
		
		ok.addActionListener(a);
		cancel.addActionListener(a);
		
		panel.add(ok);
		panel.add(cancel);
		
		
		
		setVisible(true);
	}
	
	private void setString() {
		String sizeT = size.getText();
		String priceT = price.getText();
		if (priceT.stripLeading().startsWith("$") == false) {
			priceT = "$"+priceT;
		}
		String set = sizeT+", "+priceT;
		
		if (sizeT.contains(",")) {
			set = setSize(sizeT, priceT);
		}
		
		itemString = id.getText()+", "+name.getText()+", "+set+"&"+amount;
		//System.out.println(itemString);
	}
	
	public String setSize(String size, String price) {
		String test = "";
		int index = 0;
		int count = 1;
		for (int i = 0; i < size.length(); i++) {
			if (size.charAt(i) == ',')
				count++;
		}
		
		for (int i = 0; i < count; i++) {
			if (i != count-1) {
				if (price.stripLeading().startsWith("$") == false) 
					price = "$"+price;
				test += " "+size.substring(index, size.indexOf(',')+1).strip();
			
				test+= " "+price.substring(index, price.indexOf(',')+1).strip();
			
				size  = size.substring(size.indexOf(',')+1).strip();;
				price = price.substring(price.indexOf(',')+1).strip();
			}
			else {
				if (price.stripLeading().startsWith("$") == false) 
					price = "$"+price;
				test += " "+size.substring(index).strip();
				
				test+= ", "+price.substring(index).strip();
			}
				
			
		}
		
		return test;
	}
	
	private class Action implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == ok) {
				setString();
				if (item == null) {
					try {
						frame.getData().addItem(catSet.getCategory(), itemString);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					try {
						frame.getData().editItem(catSet.getCategory(), catSet.getLine(), itemString);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				dispose();
				catSet.refresh();
			}
			else if (e.getSource() == cancel)
				dispose();
		}
		
	}

}
