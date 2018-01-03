package eu.boehner.swing.datetimepicker.model;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import eu.boehner.swing.datetimepicker.tablemodel.DateTimePickerTableModel;
import eu.boehner.swing.datetimepicker.tablemodel.DayTableModel;
import eu.boehner.swing.datetimepicker.tablemodel.DecadeTableModel;
import eu.boehner.swing.datetimepicker.tablemodel.HourTableModel;
import eu.boehner.swing.datetimepicker.tablemodel.MinuteTableModel;
import eu.boehner.swing.datetimepicker.tablemodel.MonthTableModel;
import eu.boehner.swing.datetimepicker.tablemodel.SecondTableModel;
import eu.boehner.swing.datetimepicker.tablemodel.YearTableModel;

/**
 * A {@link JDateTimePickerModel} with the format {@code yyyy-MM-dd HH:mm:ss}.
 * 
 * @author igitti https://github.com/igitti/
 */
public class JDateTimePickerYearToSecondModel implements JDateTimePickerModel {
	
	/** The format. It is quite important to go from most significant (yyyy) to least significant (ss). */
	protected static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	protected List<TableModelAndCaretPosition> tableModelAndCaretPositions;
	protected DateTimePickerTableModel initialDateTimePickerTableModel;

	public JDateTimePickerYearToSecondModel() {
		tableModelAndCaretPositions = new LinkedList<>();
		tableModelAndCaretPositions.add(new TableModelAndCaretPosition(new DecadeTableModel(), new CaretPosition(-1, -1)));
		initialDateTimePickerTableModel = new YearTableModel();
		tableModelAndCaretPositions.add(new TableModelAndCaretPosition(initialDateTimePickerTableModel, new CaretPosition(0, 4)));
		tableModelAndCaretPositions.add(new TableModelAndCaretPosition(new MonthTableModel(), new CaretPosition(5, 7)));
		tableModelAndCaretPositions.add(new TableModelAndCaretPosition(new DayTableModel(), new CaretPosition(8, 10)));
		tableModelAndCaretPositions.add(new TableModelAndCaretPosition(new HourTableModel(), new CaretPosition(11, 13)));
		tableModelAndCaretPositions.add(new TableModelAndCaretPosition(new MinuteTableModel(), new CaretPosition(14, 16)));
		tableModelAndCaretPositions.add(new TableModelAndCaretPosition(new SecondTableModel(), new CaretPosition(17, 19)));
	}
	
	@Override
	public Format getFormat() {
		return TIMESTAMP_FORMAT;
	}

	@Override
	public DateTimePickerTableModel getDefaultDateTimePickerTableModel() {
		return initialDateTimePickerTableModel;
	}
	
	@Override
	public DateTimePickerTableModel getNextDateTimePickerTableModel(DateTimePickerTableModel dateTimePickerTableModel) {
		DateTimePickerTableModel result = null;
		ListIterator<TableModelAndCaretPosition> listIterator = tableModelAndCaretPositions.listIterator();
		while (listIterator.hasNext()) {
			if (listIterator.next().getDateTimePickerTableModel() == dateTimePickerTableModel) {
				if (listIterator.hasNext()) {
					result = listIterator.next().getDateTimePickerTableModel();
				}
				break;
			}
		}
		return result;
	}

	@Override
	public DateTimePickerTableModel getPreviousDateTimePickerTableModel(DateTimePickerTableModel dateTimePickerTableModel) {
		DateTimePickerTableModel result = null;
		ListIterator<TableModelAndCaretPosition> listIterator = tableModelAndCaretPositions.listIterator(tableModelAndCaretPositions.size());
		while (listIterator.hasPrevious()) {
			if (listIterator.previous().getDateTimePickerTableModel() == dateTimePickerTableModel) {
				if (listIterator.hasPrevious()) {
					result = listIterator.previous().getDateTimePickerTableModel();
				}
				break;
			}
		}
		return result;
	}

	@Override
	public DateTimePickerTableModel getDateTimePickerTableModelPerCaretPosition(int position) {
		DateTimePickerTableModel dateTimePickerTableModel = null;
		for (TableModelAndCaretPosition tableModelAndCaretPosition: tableModelAndCaretPositions) {
			CaretPosition caretPosition = tableModelAndCaretPosition.getCaretPosition();
			if (caretPosition.getFrom() <= position && caretPosition.getTo() >= position) {
				dateTimePickerTableModel = tableModelAndCaretPosition.getDateTimePickerTableModel();
			}
		}
		return dateTimePickerTableModel;
	}
	
	@Override
	public CaretPosition getCaretPosition(DateTimePickerTableModel dateTimePickerTableModel) {
		CaretPosition result = null;
		for (TableModelAndCaretPosition tableModelAndCaretPosition: tableModelAndCaretPositions) {
			if (tableModelAndCaretPosition.getDateTimePickerTableModel() == dateTimePickerTableModel) {
				result = tableModelAndCaretPosition.getCaretPosition();
			}
		}
		return result;
	}
	
}
