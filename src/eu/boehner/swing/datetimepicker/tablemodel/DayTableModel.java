package eu.boehner.swing.datetimepicker.tablemodel;

import java.awt.Color;
import java.awt.Component;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * A {@link DateTimePickerTableModel} for selecting the day of month.<br/>
 * All days of a month are arranged with the first column holding the first day of week.
 * 
 * @author Daniel https://github.com/igitti/
 * 
 */
public class DayTableModel extends DateTimePickerTableModel {

	private static final int ROWS = 6;
	private static final int COLS = 7;
	private static final SimpleDateFormat HEADER_SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-");
	private static final SimpleDateFormat TEXT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-");
	private static final String[] WEEKDAYS = new DateFormatSymbols().getShortWeekdays();

	private TableCellRenderer tableCellRenderer;
	private String[][] values = new String[ROWS][COLS];
	private Date[][] dates = new Date[ROWS][COLS];
	private boolean[][] inMonth = new boolean[ROWS][COLS];
	private boolean[][] now = new boolean[ROWS][COLS];
	
	public DayTableModel() {
		this.tableCellRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	            label.setHorizontalAlignment(JLabel.CENTER);
            	label.setForeground(DayTableModel.this.isNow(row, column) ? Color.RED : isSelected ? Color.WHITE : DayTableModel.this.isInMonth(row, column) ? Color.BLACK : Color.LIGHT_GRAY);
            	return label;
			}
		};
	}
	
	@Override
	public int getRowCount() {
		return ROWS;
	}

	@Override
	public int getColumnCount() {
		return COLS;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return values[rowIndex][columnIndex];
	}
	
	@Override
	public String getColumnName(int column) {
		return WEEKDAYS[column];
	}
	
	public boolean isNow(int row, int col) {
		return now[row][col];
	}
	
	public boolean isInMonth(int row, int col) {
		return inMonth[row][col];
	}
	
	@Override
	protected void render() {
		Calendar calendar = Calendar.getInstance();
		int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
		int currentMonth = calendar.get(Calendar.MONTH);
		int currentYear = calendar.get(Calendar.YEAR);
		calendar = (Calendar)this.calendar.clone();
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		//begin with that day that is first day of week in which is the first of day of month
		//e. g. first day of week = monday, 2015-08-01 = saturday --> begin 5 days before
		int beginDaysBefore = (calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek() + 7) % 7; //+7 to get a positive result (% is remainder and not modulus, http://stackoverflow.com/questions/4403542/how-does-java-do-modulus-calculations-with-negative-numbers) 
		calendar.add(Calendar.DAY_OF_MONTH, -beginDaysBefore);

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j ++) {
				values[i][j] = String.valueOf(calendar.get(Calendar.DATE));
				dates[i][j] = calendar.getTime();
				inMonth[i][j] = calendar.get(Calendar.MONTH) == month;
				now[i][j] = calendar.get(Calendar.DAY_OF_MONTH) == currentDay && calendar.get(Calendar.MONTH) == currentMonth && calendar.get(Calendar.YEAR) == currentYear ;
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		fireTableDataChanged();
	}
	
	@Override
	public String getHeader() {
		return HEADER_SIMPLE_DATE_FORMAT.format(calendar.getTime());
	}

	@Override
	public String getText() {
		return TEXT_DATE_FORMAT.format(calendar.getTime());
	}

	@Override
	public int getRowHeight() {
		return 19;
	}

	@Override
	public Date getDate(int row, int col) {
		return dates[row][col];
	}

	@Override
	public void previous() {
		calendar.add(Calendar.MONTH, -1);
		render();
	}

	@Override
	public void next() {
		calendar.add(Calendar.MONTH, 1);
		render();
	}

	@Override
	public TableCellRenderer getTableCellRenderer() {
		return tableCellRenderer;
	}

}
