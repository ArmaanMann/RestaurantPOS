package com.main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.main.tables.orders.items.ReportItemTableModel;

public class Reports extends JFrame {
	
	private Frame frame;
	private JPanel panel;
	private JScrollPane pane;
	private JTable table;
	private ReportItemTableModel model;
	private JButton back;
	
	public Reports(Frame frame) {
		super("Reports");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(frame.getIcon());
		
		panel = new JPanel();
		
		add(panel);
		
		model = new ReportItemTableModel(frame.getData().getAllItems());
		
		table = new JTable(model);
		
		pane = new JScrollPane(table);
		pane.setPreferredSize(new Dimension(350, 300));
		
		panel.add(pane);
		
		back = new JButton("Close");
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
			
		});
		
		panel.add(back);
		
		setVisible(true);
	}

}
