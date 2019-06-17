package com.vadrin.primeart.models;

public class Art {

	private String[] rows;

	public String[] getRows() {
		return rows;
	}

	public void setRows(String[] rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		String toReturn = "";
		for (String thisRow : rows) {
			toReturn = toReturn + thisRow;
		}
		return toReturn;
	}

	public Art(String[] rows) {
		super();
		this.rows = rows;
	}

	public Art() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Art(String input, int cols) {
		super();
		rows = new String[input.length()/cols];
		for(int i=0; i<input.length()/cols; i++){
			rows[i] = input.substring(i*cols, (i*cols)+cols);
		}
	}

}
