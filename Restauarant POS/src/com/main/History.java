package com.main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.main.tables.Table;
import com.main.tables.orders.items.OrderItemTableModel;

public class History extends JFrame {
	
	private Frame frame;
	private JPanel panelLeft, panelRight;
	private JList<Table> list;
	private DefaultListModel<Table> listModel;
	private JScrollPane paneL, paneR;
	private JTable table;
	private OrderItemTableModel model;
	private int index = 0;
	private String fileName = "pastFile"; 
	private JButton back;
	
	public History(Frame frame) {
		super("Past Orders");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(frame.fWidth, frame.fHeight - 150);
		setResizable(false);
		setLocationRelativeTo(null);	
		setLayout(new GridLayout(1, 1));
		setIconImage(frame.getIcon());
		
		this.frame = frame;
		
		panelLeft = new JPanel();
		panelRight = new JPanel();
		
		add(panelLeft);
		add(panelRight);
		
		//LEFT PANEL FIRST
		
		setListModel();
		
		list = new JList<>(listModel);
		list.setSelectedIndex(index);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				refreshTables(list.getSelectedIndex());
			}
			
		});
		
		paneL = new JScrollPane(list);
		paneL.setPreferredSize(new Dimension(200, 250));
		
		back = new JButton("Close");
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		panelLeft.add(paneL);
		panelLeft.add(back);
		
		
		//RIGHT PANEL
		model = new OrderItemTableModel(frame.getData().getPastTables().get(index).getAllOrders());
		
		
		table = new JTable(model);
		
		paneR = new JScrollPane(table);
		paneR.setPreferredSize(new Dimension(frame.fWidth / 2 - 20, 250));
		
		panelRight.add(paneR);
		
		
		setVisible(true);
		
	}
	
	private void refreshTables(int index) {
		this.index = index;
		try {
		model = new OrderItemTableModel(frame.getData().getPastTables().get(index).getAllOrders());
		} catch (NullPointerException e) {
			System.out.println("No current orders on table");
		}
		table = new JTable(model);
		paneR.setViewportView(table);
	}
	
	private void setListModel() {
		if (listModel == null)
		listModel = new DefaultListModel<Table>();
		for (int i = 0; i < frame.getData().getPastTables().size(); i++) {
			listModel.addElement(frame.getData().getPastTables().get(i));
		}
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

	
  
}