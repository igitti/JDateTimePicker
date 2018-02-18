package eu.boehner.swing.datetimepicker.model;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

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
 * @author Daniel https://github.com/igitti/
 */
public class DecadeYearMonthDayHourMinuteSecondModel implements JDateTimePickerModel {

	protected static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	protected LinkedHashMap<DateTimePickerTableModel, TableModelUsage> tableModelUsages;
	protected DateTimePickerTableModel initialDateTimePickerTableModel;

	public DecadeYearMonthDayHourMinuteSecondModel() {
		DateTimePickerTableModel decadeDateTimePickerTableModel = new DecadeTableModel();
		TableModelUsage decadeTableModelUsage = new TableModelUsage(decadeDateTimePickerTableModel, new CaretPosition(-1, -1)) {
			@Override
			public String getHeader(Date date) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				return year / 100 + "__";
			}
			@Override
			public String getText(Date date) {
				return FORMAT.format(date);
			}
		};
		
		DateTimePickerTableModel yearDateTimePickerTableModel = new YearTableModel();
		TableModelUsage yearTableModelUsage = new TableModelUsage(yearDateTimePickerTableModel, new CaretPosition(0, 4)) {
			@Override
			public String getHeader(Date date) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				return year / 10 + "_";
			}
			@Override
			public String getText(Date date) {
				return FORMAT.format(date);
			}
		};
		initialDateTimePickerTableModel = yearDateTimePickerTableModel;

		DateTimePickerTableModel monthDateTimePickerTableModel = new MonthTableModel();
		TableModelUsage monthTableModelUsage = new TableModelUsage(monthDateTimePickerTableModel, new CaretPosition(5, 7)) {
			Format format = new SimpleDateFormat("yyyy-");
			@Override
			public String getHeader(Date date) {
				return format.format(date);
			}
			@Override
			public String getText(Date date) {
				return FORMAT.format(date);
			}
		};
		
		DateTimePickerTableModel dayDateTimePickerTableModel = new DayTableModel();
		TableModelUsage dayTableModelUsage = new TableModelUsage(dayDateTimePickerTableModel, new CaretPosition(8, 10)) {
			Format format = new SimpleDateFormat("yyyy-MM-");
			@Override
			public String getHeader(Date date) {
				return format.format(date);
			}
			@Override
			public String getText(Date date) {
				return FORMAT.format(date);
			}
		};
		
		DateTimePickerTableModel hourDateTimePickerTableModel = new HourTableModel();
		TableModelUsage hourTableModelUsage = new TableModelUsage(hourDateTimePickerTableModel, new CaretPosition(11, 13)) {
			Format format = new SimpleDateFormat("yyyy-MM-dd");
			@Override
			public String getHeader(Date date) {
				return format.format(date);
			}
			@Override
			public String getText(Date date) {
				return FORMAT.format(date);
			}
		};
		
		DateTimePickerTableModel minuteDateTimePickerTableModel = new MinuteTableModel();
		TableModelUsage minuteTableModelUsage = new TableModelUsage(minuteDateTimePickerTableModel, new CaretPosition(14, 16)) {
			Format format = new SimpleDateFormat("yyyy-MM-dd HH:");
			@Override
			public String getHeader(Date date) {
				return format.format(date);
			}
			@Override
			public String getText(Date date) {
				return FORMAT.format(date);
			}
		};
		
		DateTimePickerTableModel secondDateTimePickerTableModel = new SecondTableModel();
		TableModelUsage secondTableModelUsage = new TableModelUsage(secondDateTimePickerTableModel, new CaretPosition(17, 19)) {
			Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:");
			@Override
			public String getHeader(Date date) {
				return format.format(date);
			}
			@Override
			public String getText(Date date) {
				return FORMAT.format(date);
			}
		};
		
		tableModelUsages = new LinkedHashMap<>();
		
		decadeTableModelUsage.setNext(yearTableModelUsage);
		tableModelUsages.put(decadeDateTimePickerTableModel, decadeTableModelUsage);
		
		yearTableModelUsage.setPrevious(decadeTableModelUsage);
		yearTableModelUsage.setNext(monthTableModelUsage);
		tableModelUsages.put(yearDateTimePickerTableModel, yearTableModelUsage);
		
		monthTableModelUsage.setPrevious(yearTableModelUsage);
		monthTableModelUsage.setNext(dayTableModelUsage);
		tableModelUsages.put(monthDateTimePickerTableModel, monthTableModelUsage);
		
		dayTableModelUsage.setPrevious(monthTableModelUsage);
		dayTableModelUsage.setNext(hourTableModelUsage);
		tableModelUsages.put(dayDateTimePickerTableModel, dayTableModelUsage);
		
		hourTableModelUsage.setPrevious(dayTableModelUsage);
		hourTableModelUsage.setNext(minuteTableModelUsage);
		tableModelUsages.put(hourDateTimePickerTableModel, hourTableModelUsage);
		
		minuteTableModelUsage.setPrevious(hourTableModelUsage);
		minuteTableModelUsage.setNext(secondTableModelUsage);
		tableModelUsages.put(minuteDateTimePickerTableModel, minuteTableModelUsage);
		
		secondTableModelUsage.setPrevious(minuteTableModelUsage);
		tableModelUsages.put(secondDateTimePickerTableModel, secondTableModelUsage);
	}

	@Override
	public Format getFormat() {
		return FORMAT;
	}

	@Override
	public DateTimePickerTableModel getDefaultDateTimePickerTableModel() {
		return initialDateTimePickerTableModel;
	}

	@Override
	public DateTimePickerTableModel getNextDateTimePickerTableModel(DateTimePickerTableModel dateTimePickerTableModel) {
		DateTimePickerTableModel result = null;
		TableModelUsage tableModelUsage = tableModelUsages.get(dateTimePickerTableModel);
		if (tableModelUsage != null) {
			if ((tableModelUsage = tableModelUsage.getNext()) != null) {
				result = tableModelUsage.getDateTimePickerTableModel();
			}
		}
		return result;
	}

	@Override
	public DateTimePickerTableModel getPreviousDateTimePickerTableModel(DateTimePickerTableModel dateTimePickerTableModel) {
		DateTimePickerTableModel result = null;
		TableModelUsage tableModelUsage = tableModelUsages.get(dateTimePickerTableModel);
		if (tableModelUsage != null) {
			if ((tableModelUsage = tableModelUsage.getPrevious()) != null) {
				result = tableModelUsage.getDateTimePickerTableModel();
			}
		}
		return result;
	}

	@Override
	public DateTimePickerTableModel getDateTimePickerTableModelPerCaretPosition(int position) {
		DateTimePickerTableModel dateTimePickerTableModel = null;
		for (TableModelUsage tableModelUsage: tableModelUsages.values()) {
			CaretPosition caretPosition = tableModelUsage.getCaretPosition();
			if (caretPosition.getFrom() <= position && caretPosition.getTo() >= position) {
				dateTimePickerTableModel = tableModelUsage.getDateTimePickerTableModel();
			}
		}
		return dateTimePickerTableModel;
	}

	@Override
	public CaretPosition getCaretPosition(DateTimePickerTableModel dateTimePickerTableModel) {
		CaretPosition result = null;
		for (TableModelUsage tableModelUsage: tableModelUsages.values()) {
			if (tableModelUsage.getDateTimePickerTableModel() == dateTimePickerTableModel) {
				result = tableModelUsage.getCaretPosition();
			}
		}
		return result;
	}

	@Override
	public String getHeader(DateTimePickerTableModel dateTimePickerTableModel, Date date) {
		TableModelUsage tableModelUsage = tableModelUsages.get(dateTimePickerTableModel);
		return tableModelUsage == null ? null : tableModelUsage.getHeader(date);
	}

	@Override
	public String getText(DateTimePickerTableModel dateTimePickerTableModel, Date date) {
		TableModelUsage tableModelUsage = tableModelUsages.get(dateTimePickerTableModel);
		return tableModelUsage == null ? null : tableModelUsage.getText(date);
	}

}
