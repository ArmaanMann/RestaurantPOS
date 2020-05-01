package com.main;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.main.tables.Table;
import com.main.tables.orders.items.Item;
import com.main.tables.orders.items.OrderItemTableModel;

public class Data {
	
	
	private static Frame frame;
	private List<String> buttonNames;
	private List<List<Item>> itemsList;
	private int listCount = 0;
	private List<Item> addOrderItems;
	private List<Table> pastTables;
	private List<Item> allItems, amounts;
	private String path = new File("items").getAbsolutePath();
	private File file;

	public Data(Frame frame) {
		this.frame = frame;
		buttonNames = new ArrayList<String>();
		itemsList = new ArrayList<List<Item>>();
		addOrderItems = new ArrayList<Item>();
		pastTables = new ArrayList<Table>();
		allItems = new ArrayList<Item>();
		file = new File(path);
		int count = 0;
		
		for (final File fileEntry : file.listFiles()) {
			System.out.println(fileEntry.getName());
			setButtons(fileEntry.getName());
			itemsList.add(new ArrayList<Item>());
			try {
				readText(fileEntry, count);
			} catch (IOException e) {
				e.printStackTrace();
			}
			count++;
			itemsList.add(new ArrayList<Item>());
		}
		frame.setCategories(count);
		System.out.println("Total Categories are "+count);
		
		

	}
	
	private void readText(File file, int cat) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		int count = 0;
		
		while ((line = br.readLine()) != null) {
			readItems(line, count, cat);
			count++;
		}
		br.close();
	}
	
	private void setLists() {
		
	}
	public List<Item> getAddOrderList() {
		return addOrderItems;
	}
	
	public void clearOrderList() {
		addOrderItems = new ArrayList<Item>();
	}
	
	public void addTable(Table table) {
		table.setDate();
		pastTables.add(0, table);
	}
	
	public List<Table> getPastTables() {
		return pastTables;
	}
	
	public void savePastTables() {
		try {
			FileOutputStream fos = new FileOutputStream("pastFile");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(pastTables);
			oos.writeObject(frame.getTables());
			oos.close();
			fos.close();
			System.out.println("Saving model files...");
		} catch (IOException ioe) {
			System.out.println("Error saving files.");
			File file = new File("pastFile");
			file.delete();
			ioe.printStackTrace();
		}
	}
	
	
	public void loadPastTables() {
		try {
			FileInputStream fis = new FileInputStream("pastFile");
			ObjectInputStream ois = new ObjectInputStream(fis);
			pastTables = (List<Table>) ois.readObject();
			frame.TABLE_NUMS = (Integer) ois.readObject();
			ois.close();
			fis.close();
			System.out.println("Loading files... ");
		} catch (Exception e) {
			System.out.println("Error getting files.");
		}
	}
	
	private void setButtons(String text) {
		String add = text.substring(text.indexOf("-")+1, text.indexOf(".")).strip();
		add = add.substring(0, 1).toUpperCase() + add.substring(1);
		//System.out.println("File name = "+add);
		
		buttonNames.add(add);
	}
	
	public List<String> getButtons() {
		return buttonNames;
	}
	
	public List<Item> getList(int data) {
		return itemsList.get(data);
	}
	
	private int getTest(String text) {
		int a = text.indexOf('&');
		int test = Integer.parseInt(text.substring(a+1));
		//System.out.println("Times ordered = "+test);
		return test;
	}
	
	private void addItemReport(int amount) {
		
	}
	
	private void readItems(String text, int test, int cat) {
		String id = "", name = "", size = "", price = "";
		int index = 0;
		String[] lol = {id, name, size, price};
		int count = 0;
		boolean skip = false, done = false;
		//System.out.println(text);
		int a = text.indexOf('&');
		for (int i = 0; i < a; i++) {
			if (!skip) {
				if (text.charAt(i) == ',' || i == a - 1) {
					lol[count] = text.substring(index, i+1).replaceFirst(",", "").strip();
					count++;
					index = i+1;
				}
				if (count == 4 && index+1 < a) {
					count = 2;
					skip = true;
					addItems(lol, text, test, cat, false);
				} else if (count == 4 && index+1 < a == false) {
					addItems(lol, text, test, cat, true);
					listCount++;
				}
			}
				
			else {
				if (text.charAt(i) == ',' || i == a - 1) {
					lol[count] = text.substring(index, i+1).replaceFirst(",", "").strip();
					count++;
					index = i+1;
				}
				if (count == 4 && index+1 < a) {
					count = 2;
					index = i+1;
					addItems(lol, text, test, cat, false);
				} else if (count == 4 && index+1 < a == false) {
					addItems(lol, text, test, cat, true);
					listCount++;
				}
			}
		}

	}
	
	public List<Item> getAllItems() {
		return allItems;
	}
	
	public void setNewOrders() throws IOException {
		String content = "";
		for (final File fileEntry : file.listFiles()) {
			BufferedReader br = new BufferedReader(new FileReader(fileEntry));
			String line;
			int count = 0;
			
			while ((line = br.readLine()) != null) {
				if (count < 1) {
				content += line.substring(0, line.indexOf('&')) + System.lineSeparator();
				}
			}
			System.out.println("New Content is "+content);
			if (content == "") {
				System.out.println("WOW");
			}
			count++;
			content = "";
			br.close();
		}
	}
	
	public void setNewOrders(List<Item> amounts) throws IOException {
		this.amounts = amounts;
		BufferedReader br = null;
		FileWriter writer;
		String line;
		int acount = 0;
		int test = 0;
		for (final File fileEntry : file.listFiles()) {
			br = new BufferedReader(new FileReader(fileEntry));
			int count = 0;
			String content = "";
		//	System.out.println("File reader ran");
			while ((line = br.readLine()) != null) {
			//	System.out.println("Line is "+line);
				if (orderBool(acount, count)) {
					line = line.substring(0, line.indexOf('&')+1)+""+amounts.get(test).getAmount();
					test++;
				}
				//System.out.println("Line is "+line);
				content += line + System.lineSeparator();
				count++;
			}
			writer = new FileWriter(fileEntry);
			writer.write(content);
			writer.flush();
			writer.close();
			acount++;
		}
		br.close();
		
		
	}
	
	public void addItem(int cat, String item) throws IOException {
		BufferedReader br = null;
		FileWriter writer;
		String line;
		int acount = 0;
		for (final File fileEntry : file.listFiles()) {
			br = new BufferedReader(new FileReader(fileEntry));
			int count = 0;
			String content = "";
			if (acount == cat) {
				while ((line = br.readLine()) != null) {
					content += line + System.lineSeparator();
					count++;
				}
				content += item;
				writer = new FileWriter(fileEntry);
				writer.write(content);
				writer.flush();
				writer.close();
			}
			acount++;
		}
		br.close();
		
	}
	
	public void removeItem(int cat, int lineNum) throws IOException {
		BufferedReader br = null;
		FileWriter writer;
		String line;
		int acount = 0;
		System.out.println("Line num is "+lineNum+" Cat is "+cat);
		for (final File fileEntry : file.listFiles()) {
			br = new BufferedReader(new FileReader(fileEntry));
			int count = 0;
			String content = "";
			if (acount == cat) {
			while ((line = br.readLine()) != null) {
				if (count == lineNum) {
					
				}
				else {
				content += line + System.lineSeparator();
				}
				count++;
			}
			writer = new FileWriter(fileEntry);
			writer.write(content);
			writer.flush();
			writer.close();
			}
			acount++;
		}
		br.close();
	}
	
	public void editItem(int cat, int lineNum, String item) throws IOException {
		BufferedReader br = null;
		FileWriter writer;
		String line;
		int acount = 0;
		for (final File fileEntry : file.listFiles()) {
			br = new BufferedReader(new FileReader(fileEntry));
			int count = 0;
			String content = "";
			if (acount == cat) {
			while ((line = br.readLine()) != null) {
				if (count == lineNum)
				content += item + System.lineSeparator();
				else
				content += line + System.lineSeparator();
				
				count++;
			}
			writer = new FileWriter(fileEntry);
			writer.write(content);
			writer.flush();
			writer.close();
			}
			acount++;
		}
		br.close();
	}
	
	private String itemString(Item item) {
		return item.getId()+", "+item.getName()+", "+item.getSize()+", ";
	}
	
	
	private boolean orderBool(int category, int line) {
		for (int i = 0; i < amounts.size(); i++) {
			if (amounts.get(i).getCategory() == category && amounts.get(i).getLine() == line) {
				System.out.println("OH TRUUUUUES");
				return true;
			}
		}
		return false;
	}
	
	public String getPath() {
		return path;
	}
 	
	private void addItems(String[] lol, String text, int test, int cat, boolean all) {
		Item item = new Item(lol[0], lol[1], lol[2], lol[3]);
		//System.out.println("ID = "+lol[0]+", Name = "+lol[1]+", Size = "+lol[2]+", Price = "+lol[3]);
		item.setAmount(getTest(text));
		item.setCategory(cat);
		item.setLine(test);
		//System.out.println("count "+cat+" item id ="+item.getId());
		itemsList.get(cat).add(item);
		if (all)
		allItems.add(item);
	}
}
