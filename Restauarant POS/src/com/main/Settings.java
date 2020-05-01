package com.main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class Settings extends JFrame {

	private Frame frame;
	private JPanel rPanel, lPanel;
	private JScrollPane pane;
	private JList<String> catList;
	private DefaultListModel<String> catModel;
	private List<String> buttonsList;
	private JButton[] buttons;
	private ButtonAction bAction;
	private Action action;
	private JButton add, remove, edit, tableNo;
	private Font font = new Font("Verdana", Font.BOLD, 12);
	
	public Settings(Frame frame) {
		super("Settings");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(1, 1));
		setIconImage(frame.getIcon());
		
		this.frame = frame;
		buttonsList = frame.getData().getButtons();
		action = new Action();
		bAction = new ButtonAction();
		
		rPanel = new JPanel();
		lPanel = new JPanel();
		
		add(lPanel);
		add(rPanel);
		
		//LEFT PANEL
		JLabel catLabel = new JLabel("Select a category from the list and ");
		catLabel.setFont(font);
		JLabel catLabel1 = new JLabel("then press one of the buttons below.");
		catLabel1.setFont(font);
		lPanel.add(catLabel);
		lPanel.add(catLabel1);
		
		catModel = new DefaultListModel<String>();
		catModel.addAll(buttonsList);
		
		catList = new JList<String>(catModel);
		catList.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				JList list = (JList) e.getSource();
				Point point = e.getPoint();
				int row = list.getSelectedIndex();
				if (e.getClickCount() == 2)
					editCat();
			}
		});
		catList.setSelectedIndex(0);
		
		pane = new JScrollPane(catList);
		pane.setPreferredSize(new Dimension(200, 200));
		
		lPanel.add(pane);
		
		add = new JButton("Add Category");
		remove = new JButton("Remove Category");
		edit = new JButton("Edit Category");
		tableNo = new JButton("Edit Table Numbers");
		
		lPanel.add(add);
		lPanel.add(remove);
		lPanel.add(edit);
		lPanel.add(tableNo);
		
		add.addActionListener(action);
		remove.addActionListener(action);
		edit.addActionListener(action);
		tableNo.addActionListener(action);
		
		
		//RIGHT PANEL
		
		JLabel label = new JLabel("Click a category to add or remove items.");
		label.setFont(font);
		rPanel.add(label);
		
		buttons = new JButton[frame.getCategories()];
		
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(buttonsList.get(i));
			buttons[i].addActionListener(bAction);
			rPanel.add(buttons[i]);
		}
		
		
		setVisible(true);
	}
	
	private void addCategory(String catName) {
		if (catName.isEmpty() || catName == null)
			return;
		File folder = new File(frame.getData().getPath());
		File file = new File(folder, (frame.getCategories()+1)+" - "+catName+".txt");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		saveSettings();
	}
	
	private void removeCategory(int cat) {
		String test = (cat+1)+"";
		File folder = new File(frame.getData().getPath());
		File fileDelete = null;
		int count = 0;
		for (final File file : folder.listFiles()) {
			if (count == cat) {
				fileDelete = file;
			}
			count++;
		}
		System.out.println("File to be deleted is "+fileDelete.toString());
		fileDelete.delete();
		saveSettings();
	}
	
	private void editCategory(int cat, String catName) {
		String test = (cat+1)+"";
		File folder = new File(frame.getData().getPath());
		int count = 0;
		for (final File file : folder.listFiles()) {
			if (count == cat) {
				file.renameTo(new File(folder, test+" - "+catName+".txt"));
			}
			count++;
		}
		saveSettings();
	}
	
	private void saveSettings() {
		frame.loadData();
		Settings s = new Settings(frame);
		dispose();
	}
	
	private void editCat() {
		if (catList.getSelectionModel().isSelectionEmpty() == false) {
		String s = JOptionPane.showInputDialog("Enter a category name.");
		if (s != null)
		editCategory(catList.getSelectedIndex(), s);
		}
	}
	
	
	
	private class ButtonAction implements ActionListener {
		

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < buttons.length; i++) 
				if (e.getSource() == buttons[i]) {
					CategorySettings catSet = new CategorySettings(frame, i);
					return;
				} 
		}
	}
	
	private class Action implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == add) {
				String s = JOptionPane.showInputDialog("Enter a category name.");
				if (s != null)
				addCategory(s);
			}
			else if (e.getSource() == remove) {
				int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to remove "+catList.getSelectedValue()+"? This cannot be undone.","Warning", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
				removeCategory(catList.getSelectedIndex());
				}
			}
			else if (e.getSource() == edit) {
				editCat();
			}
			else if (e.getSource() == tableNo) {
				String s = JOptionPane.showInputDialog("Current Tables: "+Frame.TABLE_NUMS+". Enter the amount of tables you'd like to change to.");
				if (s != null) {
					frame.setTableNo(s);
				}
			}
		}
		
	}
}
