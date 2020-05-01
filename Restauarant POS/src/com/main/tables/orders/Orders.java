package com.main.tables.orders;

import java.util.Arrays;
import java.util.List;

import com.main.tables.orders.items.Item;

public class Orders {
	
	private String name;
	private int quantity, size;
	private List<List<Item>> orders;
	
	public Orders(List<Item> i) {
		orders.add(i);
	}
	
	public List<List<Item>> getOrder() {
		return orders;
	}

}
