package org.sodeja.swing.component.form;

public class FormPanelGridData {
	private Integer row;
	private Integer columnsCount;
	private boolean fillEmpty = false;
	
	public FormPanelGridData() {
		row = 0;
		columnsCount = 0;
	}

	public Integer getColumnsCount() {
		return columnsCount;
	}

	public void setColumnsCount(Integer cols) {
		this.columnsCount = cols;
	}

	public Integer getRow() {
		return row;
	}
	
	public void nextRow() {
		row += 1;
	}
	
	public void moveRows(int count) {
		row += count;
	}

	public boolean isFillEmpty() {
		return fillEmpty;
	}

	public void setFillEmpty(boolean fillEmpty) {
		this.fillEmpty = fillEmpty;
	}
}
