package com.main.tables.orders.items;

import java.io.Serializable;

public class Item implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id, name, size, price;
	private int quantity = 1;
	private int amount, line, category;
	
	public Item(String id, String name, String size, String price) {
		this.id = id;
		this.name = name;
		this.size = size;
		this.price = price;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSize() {
		return size;
	}
	
	public String getPrice() {
		return price;
	}
	
	public String getQuantity() {
		return quantity+"";
	}
	
	public int getQuantityNum() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setLine(int line) {
		this.line = line;
	}
	
	public int getLine() {
		return line;
	}
	
	public void setCategory(int category) {
		this.category = category;
	}
	
	public int getCategory() {
		return category;
	}
	
	public void setSize(String size) {
		this.size = size;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
