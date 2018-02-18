package eu.boehner.swing.datetimepicker.tablemodel;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * A {@link DateTimePickerTableModel} for selecting the month.<br/>
 * All months of a year are selectable.
 * So there are 12 elements which are arranged in 4 colums an 3 rows.
 * 
 * @author Daniel https://github.com/igitti/
 * 
 */
public class MonthTableModel extends DateTimePickerTableModel {

	private static final int ROWS = 3;
	private static final int COLS = 4;

	private TableCellRenderer tableCellRenderer;
	private String[][] values = new String[ROWS][COLS];
	private Date[][] dates = new Date[ROWS][COLS];
	private boolean[][] now = new boolean[ROWS][COLS];
	
	public MonthTableModel() {
		this.tableCellRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	            label.setHorizontalAlignment(JLabel.CENTER);
            	label.setForeground(MonthTableModel.this.isNow(row, column) ? Color.RED : isSelected ? Color.WHITE : Color.BLACK);
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

	public boolean isNow(int row, int col) {
		return now[row][col];
	}
	

	@Override
	protected void render() {
		Calendar current = Calendar.getInstance();
		int currentMonth = current.get(Calendar.MONTH);
		int currentYear = current.get(Calendar.YEAR);
		Calendar calendar = (Calendar)this.calendar.clone();
		int month = 0;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j ++) {
				calendar.set(Calendar.MONTH, month);
				values[i][j] = simpleDateFormat.format(calendar.getTime());
				dates[i][j] = calendar.getTime();
				now[i][j] = month == currentMonth && calendar.get(Calendar.YEAR) == currentYear;
				month += 1;
			}
		}
		fireTableDataChanged();
	}

	@Override
	public int getRowHeight() {
		return 40;
	}

	@Override
	public Date getDate(int row, int col) {
		return dates[row][col];
	}

	@Override
	public void previous() {
		calendar.add(Calendar.YEAR, -1);
		render();
	}

	@Override
	public void next() {
		calendar.add(Calendar.YEAR, 1);
		render();
	}

	@Override
	public TableCellRenderer getTableCellRenderer() {
		return tableCellRenderer;
	}

}
