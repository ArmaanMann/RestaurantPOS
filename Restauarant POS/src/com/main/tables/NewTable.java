package com.main.tables;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.main.Frame;

public class NewTable extends JFrame {
	

	private JList<String> tableList;
	private DefaultListModel<String> listModel;
	private JScrollPane tablePane;
	private JPanel panel;
	private JButton addTable, close;
	private static final int SIZE = 400;
	private Frame frame;
	
	public NewTable(Frame frame, DefaultListModel<Table> tList) {
		super("New Table");
		this.frame = frame;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(SIZE, SIZE);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(frame.getIcon());
		
		panel = new JPanel();
		add(panel);
		
		listModel = new DefaultListModel<>();
		for (int i = 1; i <= frame.getTables(); i++) {
			listModel.addElement("Table "+i);
		}
		
        cleanList(tList);
        
 
        tableList = new JList<>(listModel);
        tableList.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JList table = (JList) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.locationToIndex(point);
                if (mouseEvent.getClickCount() == 2) {
                    addTable();
                }
            }
        });
        tablePane = new JScrollPane(tableList);
        tablePane.setPreferredSize(new Dimension(300, 250));
        
        addTable = new JButton("Add Table");
        addTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        addTable();
			}
        	
        });
        
        close = new JButton("Close");
        close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
        	
        });
        
        panel.add(tablePane);
        panel.add(addTable);
		panel.add(close);
        
		setVisible(true);
	}
	
	private void addTable() {
		if (tableList.isSelectionEmpty() == false) {
        	new TableAdder(tableList.getSelectedValue(), tableList.getSelectedIndex());
        }
	}
	
	private void cleanList(DefaultListModel<Table> tList) {
		for (int i = 0; i < tList.getSize(); i++) {
			if (listModel.contains(tList.get(i).getTableNum().strip())) {
				listModel.removeElement(tList.get(i).getTableNum().strip());
			}
		}
	}

	
	
	//Create a new Jframe just in case we want to add more details later
	private class TableAdder extends JFrame {
		
		private JLabel name, mobile;
		private JTextField nameT, mobileT;
		private JButton ok;
		private JPanel panel;
		private String text;
		private int index;
		private Action action;
		
		public TableAdder(String text, int index) {
			super(text);
			
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setSize(SIZE, SIZE / 2);
			setResizable(false);
			setLocationRelativeTo(null);
			
			action = new Action();
			this.text = text;
			this.index = index;
			
			panel = new JPanel();
			add(panel);
			
			name = new JLabel("Cutomer Name        ");
			panel.add(name);
			
			nameT = new JTextField(20);
			panel.add(nameT);
			nameT.addActionListener(action);
			
			mobile = new JLabel("Cutomer Phone        ");
			panel.add(mobile);
			
			mobileT = new JTextField(20);
			panel.add(mobileT);
			mobileT.addActionListener(action);
			
			
			ok = new JButton("OK");
			panel.add(ok);
			
			ok.addActionListener(action);

			
			setVisible(true);
			
			
		}
		
		private void test() {
			frame.addTable(text, nameT.getText(), mobileT.getText());
			listModel.removeElementAt(index);
			dispose();
		}
		
		private class Action implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == nameT)
					mobileT.requestFocus();
				else
					test();
			}
			
		}
		
	}
	

}


