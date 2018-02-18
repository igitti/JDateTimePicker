package eu.boehner.swing.datetimepicker.tablemodel;

import java.util.Calendar;
import java.util.Date;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * A DateTimePickerTableModel defines a display and selection model of a date part.<br/>
 * Date part can be year, month, etc.
 * When one part is selected the value is obtained by calling {@link #getDate(int, int)}
 * and has to be passed to the DateTimePickerTableModel for the next part by {@link #setDate(Date)}.
 * 
 * @author Daniel https://github.com/igitti/
 * 
 */
public abstract class DateTimePickerTableModel extends AbstractTableModel {

	protected Calendar calendar = Calendar.getInstance();

	/** Sets the date for the part to get selected.<br/>
	 * Only those parts of this date matter which are more significant than the part to be selected.
	 * E. g. when the day of month is to be selected only year and month matter.
	 * */
	public void setDate(Date date) {
		calendar.setTime(date);
		render();
	}

	public Date getDate() {
		return calendar.getTime();
	}

	/** Calculate eveything needed for being used as TableModel */
	protected abstract void render();

	public abstract int getRowHeight();
	
	/** Returns a date where the date part is set corresponding to selection */
	public abstract Date getDate(int row, int col);
	
	/**
	 * Tells the model to switch to the previous "selection room".<br/>
	 * E. g. when selecting day (of month) switch to the previous month.
	 */
	public abstract void previous();
	
	/**
	 * Tells the model to switch to the next "selection room".<br/>
	 * E. g. when selecting day (of month) switch to the next month.
	 */
	public abstract void next();
	
	/** Returns a {@link TableCellRenderer} for the model */
	public abstract TableCellRenderer getTableCellRenderer();
	
}
