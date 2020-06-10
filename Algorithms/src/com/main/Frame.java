package com.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Frame extends JFrame {
	
	private JButton newArray, resetArray, sort;
	private JLabel speed;
	private JComboBox<String> sortList;
	private Action a;
	private Panel panel;
	private String[] sortStrings = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Quick Sort"};
	
	public Frame() {
		super("Algorithms");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(917, 500);
		setLayout(new GridLayout(2, 1));
		ImageIcon icon = new ImageIcon("algo icon.png");
		setIconImage(icon.getImage());
		
		a = new Action();
		
		panel = new Panel();
		add(panel);

		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.LIGHT_GRAY);
		
		newArray = new JButton("New Array");
		newArray.addActionListener(a);
		
		panel1.add(newArray);
		
		resetArray = new JButton("Reset Array");
		resetArray.addActionListener(a);
		
		panel1.add(resetArray);
		
		sortList = new JComboBox<String>(sortStrings);
		panel1.add(sortList);
		
		
		sort = new JButton("Sort");
		sort.addActionListener(a);
		
		panel1.add(sort);
		
		
		
		SpinnerModel model =
		        new SpinnerNumberModel(1.00, //initial value
		                               0.25, //min
		                               1.75, //max
		                               0.25); 
		JSpinner lol = new JSpinner(model);
		lol.setPreferredSize(new Dimension(50, 25));
		JFormattedTextField loltext =((JSpinner.DefaultEditor) lol.getEditor()).getTextField();
		loltext.setEditable(false);
		lol.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				panel.setTime((double)model.getValue());
			}
			
		});
		speed = new JLabel("Speed x");
		panel1.add(speed);
		
		panel1.add(lol);
		
		JTextArea text = new JTextArea(5, 30);
		
		text.setText("\n Set a new array, choose an algorithm and click Sort!"
				+ "\n You can reuse the same array by clicking Reset Array."
				+ "\n                    Adjust the speed of the algorithm!");
		text.setFont(new Font("Arial", Font.ITALIC, 25));
		text.setWrapStyleWord(true);
		text.setLineWrap(true);
		text.setOpaque(false);
		text.setEditable(false);
		text.setFocusable(false);
		text.setBorder(UIManager.getBorder("Label.border"));
		
		panel1.add(text);
		
		add(panel);
		add(panel1);
		
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		Frame frame = new Frame();
	}
	
	private class Action implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == newArray) {
				panel.setOld(false);
				panel.setArray();
			} 
			else if (e.getSource() == sort) {
				panel.sortGo(sortList.getSelectedIndex());
			}
			else if (e.getSource() == resetArray) {
				panel.setArray();
			}
				
		}
		
	}

}
