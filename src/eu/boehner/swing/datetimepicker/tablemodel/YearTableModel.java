package eu.boehner.swing.datetimepicker.tablemodel;

import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * A {@link DateTimePickerTableModel} for selecting the year.<br/>
 * All years of a decade plus its previous and following one are selectable.
 * So there are 12 elements which are arranged in 4 colums an 3 rows.
 * 
 * @author Daniel https://github.com/igitti/
 * 
 */
public class YearTableModel extends DateTimePickerTableModel {

	private static final int ROWS = 3;
	private static final int COLS = 4;

	private TableCellRenderer tableCellRenderer;
	private String[][] strings = new String[ROWS][COLS];
	private Date[][] dates = new Date[ROWS][COLS];
	private boolean[][] now = new boolean[ROWS][COLS];
	private boolean[][] inCurrent = new boolean[ROWS][COLS];
	
	public YearTableModel() {
		this.tableCellRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	            label.setHorizontalAlignment(JLabel.CENTER);
            	label.setForeground(YearTableModel.this.isNow(row, column) ? Color.RED : isSelected ? Color.WHITE : YearTableModel.this.isInCurrent(row, column) ? Color.BLACK : Color.LIGHT_GRAY);
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
		return strings[rowIndex][columnIndex];
	}
	
	@Override
	public String getColumnName(int column) {
		return null;
	}
	
	public boolean isNow(int row, int col) {
		return now[row][col];
	}
	
	public boolean isInCurrent(int row, int col) {
		return inCurrent[row][col];
	}
	
	@Override
	protected void render() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int year = calendar.get(Calendar.YEAR);
		int decade = year - year % 10;
		year = decade - 1; //begin with one year before
		Calendar calendar = (Calendar)this.calendar.clone();
		calendar.set(Calendar.YEAR, year);
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j ++) {
				strings[i][j] = String.valueOf(year);
				dates[i][j] = calendar.getTime();
				now[i][j] = year == currentYear;
				inCurrent[i][j] = year - (year % 10) == decade; 
				calendar.set(Calendar.YEAR, ++year);
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
		calendar.add(Calendar.YEAR, -10);
		render();
	}

	@Override
	public void next() {
		calendar.add(Calendar.YEAR, 10);
		render();
	}

	@Override
	public TableCellRenderer getTableCellRenderer() {
		return tableCellRenderer;
	}

}
