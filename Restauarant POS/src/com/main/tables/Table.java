package com.main.tables;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.main.Frame;
import com.main.tables.orders.ModifyOrder;
import com.main.tables.orders.items.Item;

public class Table implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name, tableNo, phone;
	private boolean clear = false;
	private int orders = 0;
	private List<List<Item>> orderList;
	private ModifyOrder modify;
	private List<Item> empty = new ArrayList<Item>();
	private List<Item> allOrders;
	private String date = "";
	
	public Table(String tableNo, String name, String phone) {
		this.tableNo = tableNo;
		this.name = name;
		this.phone = phone;
		orderList = new ArrayList<List<Item>>();
		allOrders = new ArrayList<Item>();
	}
	
	public void addOrder(List<Item> oList) {
		orderList.add(oList);
		orders++;
	}
	
	public void removeOrder(int orderNum, int itemNum) {
		orderList.get(orderNum).remove(itemNum);
		if (orderList.get(orderNum).size() == 0) {
			if (orderList.size() == 1) {
				modify.dispose();
				orderList = new ArrayList<List<Item>>();
				System.out.println("All Orders have been deleted");
				orders = 0;
			} else {
			orderList.remove(orderNum);
			modify.setIndex(modify.getIndex() - 1);
			System.out.println("Order has been deleted");
			if (orders > 0)
				orders--;
			}
		}
	}
	
	public void removeAllOrder(int orderNum) {
		if (orderList.size() == 1) {
			modify.dispose();
			orderList = new ArrayList<List<Item>>();
			System.out.println("All Orders have been deleted");
			orders = 0;
		} else {
		orderList.remove(orderNum);
		modify.setIndex(modify.getIndex() - 1);
		System.out.println("Order has been deleted");
		if (orders > 0)
			orders--;
		}
	}
	
	public void printOrder() {
	}
	
	public void editQuantity(int orderNum, int item, int quantity) {
		orderList.get(orderNum).get(item).setQuantity(quantity);
	}
	
	public void setModify(ModifyOrder modify) {
		this.modify = modify;
	}
	
	public List<Item> getOrder(int index) {
		if (orderList.size() == 0) {
			orderList.add(empty);
		}
		return orderList.get(index);
	}
	
	public List<List<Item>> getOrders() {
		return orderList;
	}
	
	public int getOrdersAmount() {
		return orders;
	}
	
	public List<Item> getAllOrders() {
		return allOrders;
	}
	
	public void saveAllOrders() {
		
	}
	
	public String getTableNum() {
		return tableNo;
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public boolean isClear() {
		return clear;
	}
	
	public void setClear() {
		clear = true;
	}
	
	public String toString() {
		if (tableNo == "Empty")
			return tableNo;
		String o = orders == 0 ? "NO CURRENT ORDERS" : "Current orders are "+orders;
		if (date.isEmpty())
			return tableNo+" - "+o;
		else 
			return tableNo;
		
	}
	
	public void setDate() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		date = dtFormat.format(now);
		tableNo +=", "+date;
	}
	
	public void printBill() {
		DecimalFormat dFormat = new DecimalFormat("#.##");
		double total = 0;
		

		System.out.println("-");
		System.out.println("Bill for Table No. "+tableNo);
		System.out.println("-");
		System.out.println("-");
		System.out.println("Customer Name = "+getName());
		System.out.println("-");
		System.out.println("ID..."+"Name..."+"Size..."+"Price..."+"Quantity...");
		System.out.println("-");
		
		for (int i = 0; i < orderList.size(); i++) {
			int test = orderList.get(i).size();
			for (int j = 0; j < test; j++) {
				Item item = orderList.get(i).get(j);
				total += Double.parseDouble(item.getPrice().substring(1)) * Integer.parseInt(item.getQuantity());
				System.out.println(item.getId()+"..."+item.getName()+"..."+item.getSize()+"..."+item.getPrice()+"..."+item.getQuantity());
			}
		}
		String t = dFormat.format(total);
		System.out.println("-");
		System.out.println("Total is $"+t);
	}

}
