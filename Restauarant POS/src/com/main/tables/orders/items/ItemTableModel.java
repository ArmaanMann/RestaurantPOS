package com.main.tables.orders.items;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ItemTableModel extends AbstractTableModel {
	
	private final List<Item> itemsList;
	
	private final String[] cNames = {"ID", "Name", "Size", "Price"};
	
	private final Class[] cClass = {Integer.class, String.class, Double.class, Boolean.class, List.class};
	
	public ItemTableModel(List<Item> itemsList) {
		this.itemsList = itemsList;
	}
	
	@Override
	public String getColumnName(int column) {
		return cNames[column];
	}
	
	@Override 
	public int getRowCount() {
		return itemsList.size();
	}

	@Override
	public int getColumnCount() {
		return cNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Item item = itemsList.get(rowIndex);
		
		if (columnIndex == 0)
			return item.getId();
		else if (columnIndex == 1)
				return item.getName();
		else if (columnIndex == 2)
			return item.getSize();
		else if (columnIndex == 3)
			return item.getPrice();
		
		return null;
	}

}
