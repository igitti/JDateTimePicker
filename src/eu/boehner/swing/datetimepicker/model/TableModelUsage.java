package eu.boehner.swing.datetimepicker.model;

import java.util.Date;

import eu.boehner.swing.datetimepicker.tablemodel.DateTimePickerTableModel;

/**
 * A simple class gathering what is needed when a {@link DateTimePickerTableModel} used in a {@link JDateTimePickerModel}.
 * 
 * @author Daniel https://github.com/igitti/
 *
 */
public abstract class TableModelUsage {
	
	private DateTimePickerTableModel dateTimePickerTableModel;
	private CaretPosition caretPosition;
	private TableModelUsage previous;
	private TableModelUsage next;
	
	public TableModelUsage(DateTimePickerTableModel dateTimePickerTableModel, CaretPosition caretPosition) {
		this.dateTimePickerTableModel = dateTimePickerTableModel;
		this.caretPosition = caretPosition;
	}
	
	public DateTimePickerTableModel getDateTimePickerTableModel() {
		return dateTimePickerTableModel;
	}

	public CaretPosition getCaretPosition() {
		return caretPosition;
	}

	public TableModelUsage getPrevious() {
		return previous;
	}

	public void setPrevious(TableModelUsage previous) {
		this.previous = previous;
	}

	public TableModelUsage getNext() {
		return next;
	}

	public void setNext(TableModelUsage next) {
		this.next = next;
	}

	public abstract String getHeader(Date date);

	public abstract String getText(Date date);
	
}