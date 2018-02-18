package eu.boehner.swing.datetimepicker.model;

import java.text.Format;
import java.util.Date;

import eu.boehner.swing.datetimepicker.JDateTimePicker;
import eu.boehner.swing.datetimepicker.tablemodel.DateTimePickerTableModel;

/**
 * A JDateTimePickerModel represents an input and a display model for a {@link JDateTimePicker}.<br/>
 * It defines format and order of date parts to be selected. Date parts are {@link DateTimePickerTableModel}s.
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

	/**
	 * A string representation of the date parts that have been selected to this DateTimePickerTableModel.<br/>
	 * E. g. year and month when selecting the day.
	 * */
	public abstract String getHeader(DateTimePickerTableModel dateTimePickerTableModel, Date date);
	
	/**
	 * A string representation of the date parts that have been selected to this DateTimePickerTableModel.<br/>
	 * E. g. year and month when selecting the day
	 * The differnce to {@link #getHeader()} is that here delimiters for the next part should be added, e. g. a colon after hours
	 * */
	public abstract String getText(DateTimePickerTableModel dateTimePickerTableModel, Date date);
	
}
