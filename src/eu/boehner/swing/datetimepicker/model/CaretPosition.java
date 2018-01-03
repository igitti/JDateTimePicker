package eu.boehner.swing.datetimepicker.model;

/**
 * A simple class for holding a from and a to caret position for a date part within a pattern. 
 * 
 * @author Daniel https://github.com/igitti/
 */
public class CaretPosition {
	
	private int from;
	private int to;
	
	public CaretPosition(int from, int to) {
		this.from = from;
		this.to = to;
	}
	
	public int getFrom() {
		return from;
	}
	
	public int getTo() {
		return to;
	}
	
}