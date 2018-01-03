package eu.boehner.swing.datetimepicker.model;

import java.text.Format;

import eu.boehner.swing.datetimepicker.JDateTimePicker;
import eu.boehner.swing.datetimepicker.tablemodel.DateTimePickerTableModel;

/**
 * A JDateTimePickerModel represents an input and display model for a {@link JDateTimePicker}.<br/>
 * It defines the format and an order of date parts to be selected. Date parts are {@link DateTimePickerTableModel}s.
 * It is quite important to go from a significant to less significant part.
 * 
 * @author Daniel https://github.com/igitti/
 * 
 */
public interface JDateTimePickerModel {
	
	/** Returns the format of a given JDateTimePickerModel. */
	public Format getFormat();

	/** Returns the DateTimePickerTableModel for the initial date part to be selected.*/
	public DateTimePickerTableModel getDefaultDateTimePickerTableModel();

	/**
	 * Returns the next DateTimePickerTableModel for selecting the next date part.
	 * 
	 * @param dateTimePickerTableModel The DateTimePickerTableModel whose subsequent DateTimePickerTableModel to be returned.
	 */
	public DateTimePickerTableModel getNextDateTimePickerTableModel(DateTimePickerTableModel dateTimePickerTableModel);

	/**
	 * Returns the previous DateTimePickerTableModel for selecting the previous date part.
	 * 
	 * @param dateTimePickerTableModel The DateTimePickerTableModel whose preceding DateTimePickerTableModel to be returned.
	 */
	public DateTimePickerTableModel getPreviousDateTimePickerTableModel(DateTimePickerTableModel dateTimePickerTableModel);

	/** Returns a DateTimePickerTableModel corresponding to a caret position in this format. */
	public DateTimePickerTableModel getDateTimePickerTableModelPerCaretPosition(int position);

	/** Returns a {@link CaretPosition} correspondig to a DateTimePickerTableModel in this format. */
	public CaretPosition getCaretPosition(DateTimePickerTableModel dateTimePickerTableModel);

}
