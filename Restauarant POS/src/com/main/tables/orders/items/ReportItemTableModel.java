package com.main.tables.orders.items;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ReportItemTableModel extends AbstractTableModel{
	private final List<Item> itemsList;
	
	private final String[] cNames = {"ID", "Name", "Amount"};
	
	private final Class[] cClass = {Integer.class, String.class, Double.class, Boolean.class, List.class};
	
	public ReportItemTableModel(List<Item> itemsList) {
		this.itemsList = itemsList;
	}
	
	@Override
	public String getColumnName(int column) {
		return cNames[column];
	}
	
	public void refresh() {
	}

	/*@Override
	public Class<?> getColumnClass(int columnIndex) {
		return cClass[columnIndex];
	}*/
	
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
			return item.getAmount();
		
		return null;
	}
	


}
