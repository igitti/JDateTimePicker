package eu.boehner.swing.datetimepicker.model;

import eu.boehner.swing.datetimepicker.tablemodel.DateTimePickerTableModel;

/**
 * A simple class for bringing together a {@link DateTimePickerTableModel} and a {@link CaretPosition}
 * 
 * @author Daniel https://github.com/igitti/
 *
 */
public class TableModelAndCaretPosition {
	
	private DateTimePickerTableModel dateTimePickerTableModel;
	private CaretPosition caretPosition;
	
	public TableModelAndCaretPosition(DateTimePickerTableModel dateTimePickerTableModel, CaretPosition caretPosition) {
		this.dateTimePickerTableModel = dateTimePickerTableModel;
		this.caretPosition = caretPosition;
	}
	
	public DateTimePickerTableModel getDateTimePickerTableModel() {
		return dateTimePickerTableModel;
	}

	public CaretPosition getCaretPosition() {
		return caretPosition;
	}
	
}