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
 * A {@link DateTimePickerTableModel} for selecting the decade.<br/>
 * All decades of a century plus the previous and following one are selectable.
 * So there are 12 elements which are arranged in 4 colums an 3 rows.
 * 
 * @author Daniel https://github.com/igitti/
 * 
 */
public class DecadeTableModel extends DateTimePickerTableModel {

	private static final int ROWS = 3;
	private static final int COLS = 4;

	private TableCellRenderer tableCellRenderer;
	private String[][] strings = new String[ROWS][COLS];
	private Date[][] dates = new Date[ROWS][COLS];
	private boolean[][] now = new boolean[ROWS][COLS];
	private boolean[][] inCurrent = new boolean[ROWS][COLS];
	
	public DecadeTableModel() {
		this.tableCellRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	            label.setHorizontalAlignment(JLabel.CENTER);
            	label.setForeground(DecadeTableModel.this.isNow(row, column) ? Color.RED : isSelected ? Color.WHITE : DecadeTableModel.this.isInCurrent(row, column) ? Color.BLACK : Color.LIGHT_GRAY);
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
	
	private boolean isNow(int row, int col) {
		return now[row][col];
	}
	
	private boolean isInCurrent(int row, int col) {
		return inCurrent[row][col];
	}
	
	
	@Override
	protected void render() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int beginnOfCurrentDecade = year - year % 10;
		year = this.calendar.get(Calendar.YEAR);
		int beginOfCentury = year - year % 100; //first year of the century
		int decade = beginOfCentury - 10; //begin with one previous
		this.calendar.set(Calendar.YEAR, beginOfCentury);
		Calendar calendar = (Calendar)this.calendar.clone();
		calendar.set(Calendar.YEAR, decade);
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j ++) {
				dates[i][j] = calendar.getTime();
				StringBuilder stringBuilder = new StringBuilder(32);
				stringBuilder.append("<html>").append(decade).append(" -<br/>").append(decade + 9).append("</html>");
				strings[i][j] = stringBuilder.toString();
				now[i][j] = decade == beginnOfCurrentDecade;
				inCurrent[i][j] = decade - (decade % 100) == beginOfCentury; 
				decade += 10;
				calendar.set(Calendar.YEAR, decade);
			}
		}
		fireTableDataChanged();
	}

	@Override
	public String getHeader() {
		int beginOfCentrury = calendar.get(Calendar.YEAR);
		return beginOfCentrury + " - " + (beginOfCentrury + 99);
	}

	@Override
	public String getText() {
		return String.valueOf(calendar.get(Calendar.YEAR) / 100);
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
		calendar.add(Calendar.YEAR, -100);
		render();
	}

	@Override
	public void next() {
		calendar.add(Calendar.YEAR, 100);
		render();
	}

	@Override
	public TableCellRenderer getTableCellRenderer() {
		return tableCellRenderer;
	}

}
