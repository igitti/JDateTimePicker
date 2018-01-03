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
 * A {@link DateTimePickerTableModel} for selecting the hour.<br/>
 * All hours of a day are selectable.
 * So there are 24 elements which are arranged in 6 colums an 4 rows.
 * 
 * @author Daniel https://github.com/igitti/
 * 
 */
public class HourTableModel extends DateTimePickerTableModel {

	private static final int ROWS = 4;
	private static final int COLS = 6;
	private static final SimpleDateFormat HEADER_SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat TEXT_SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //Set all parts to not force setting the rest

	private TableCellRenderer tableCellRenderer;
	private String[][] values = new String[ROWS][COLS];
	private Date[][] dates = new Date[ROWS][COLS];
	
	public HourTableModel() {
		this.tableCellRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	            label.setHorizontalAlignment(JLabel.CENTER);
            	label.setForeground(isSelected ? Color.WHITE : Color.BLACK);
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
	protected void render() {
		Calendar calendar = (Calendar)this.calendar.clone();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j ++) {
				values[i][j] = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
				dates[i][j] = calendar.getTime();
				calendar.add(Calendar.HOUR_OF_DAY, 1);
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
		return TEXT_SIMPLE_DATE_FORMAT.format(calendar.getTime());
	}

	@Override
	public int getRowHeight() {
		return 30;
	}

	@Override
	public Date getDate(int row, int col) {
		return dates[row][col];
	}

	@Override
	public void previous() {
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		render();
	}

	@Override
	public void next() {
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		render();
	}

	@Override
	public TableCellRenderer getTableCellRenderer() {
		return tableCellRenderer;
	}

}
