package com.main.tables;

import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.main.Frame;
import com.main.tables.orders.items.Item;

public class Bill extends JFrame {
	
	private Table table;
	private Frame frame;
	private JTextPane textArea;
	private JPanel panel;
	private JScrollPane pane;
	private List<Item> orderUp;
	
	public Bill(Frame frame, Table table) {
		super("Print Bill");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(frame.getIcon());
		
		this.frame = frame;
		this.table = table;
		orderUp = new ArrayList<Item>();
		
		panel = new JPanel();
		
		add(panel);
		
		textArea = new JTextPane();
		textArea.setEditable(false);
		Font font = new Font("Consolas", Font.BOLD, 10);
		textArea.setFont(font);
		
		
		pane = new JScrollPane(textArea);
		pane.setPreferredSize(new Dimension(400, 500));
		
		panel.add(pane);
		
		setBill();
		
		setVisible(true);
	}
	
	private void setDocument() {
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setAlignment(set, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), set, false);
	}
	
	private void addNewOrder(Item item) {
		orderUp.add(item);
	}
	
	private void setBill() {
		DecimalFormat dFormat = new DecimalFormat("#.##");
		double total = 0;
		

		String string = "Bill for "+table.getTableNum() + "\n"
				+ "Customer: "+table.getName().strip()
				+ "\n ---------------------------------------------- \n"
				+ "| ID |         Name         |  Size  | Price | Qty. |"
				+ "\n -------------------------------------------------------- \n";
		
		for (int i = 0; i < table.getOrders().size(); i++) {
			int test = table.getOrders().get(i).size();
			for (int j = 0; j < test; j++) {
				Item item = table.getOrders().get(i).get(j);
				item.setAmount(item.getQuantityNum()+item.getAmount());
				addNewOrder(item);
				table.getAllOrders().add(item);
				total += Double.parseDouble(item.getPrice().substring(1)) * Integer.parseInt(item.getQuantity());
				string += "|"+pad(item.getId(), 5)+"|"+pad(item.getName(), 22)+"|"+pad(item.getSize().strip().substring(0, 1), 6)+"|"+pad(item.getPrice(), 9)+"|"+pad(item.getQuantity(), 3) + "|\n";
				
			}
		}
		String t = dFormat.format(total);
		string += "\n Total Bill for table is: $"+t+""
				+ "\n"
				+ "\n"
				+ "Thank you for dining with us.";
		
		textArea.setText(string);
		
		setDocument();
		try {
			frame.getData().setNewOrders(orderUp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String padRight(String s, int n) {
	     return String.format("%-" + n + "s", s);  
	}

	public static String padLeft(String s, int n) {
	    return String.format("%" + n + "s", s);  
	}
	
	public static String pad(String s, int n) {
		int b = s.length();
		if (b%2 == 1)
			b += 1;
		int a = (n - b) / 2;
		String test;
		test = String.format("%-" + (a+b) + "s", s); 
		test = String.format("%" + n + "s", test);  
		return test;
	}
	
	

}
