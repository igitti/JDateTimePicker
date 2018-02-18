package eu.boehner.swing.datetimepicker;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.BorderLayout;

import javax.swing.ListSelectionModel;

import eu.boehner.swing.datetimepicker.model.JDateTimePickerModel;
import eu.boehner.swing.datetimepicker.tablemodel.DateTimePickerTableModel;

/**
 * This class represents the panel containing visible elements for direct selection of a date part.<br/>
 * When a date part gets selected using the current DateTimePickerTableModel the DateTimePickerTableModel for the next part is activated.
 * 
 * @author Daniel https://github.com/igitti/
 */
public class JDateTimePickerSelectionPanel extends JPanel {
	
	private JTable table;
	private JDateTimePickerModel dateTimePickerModel;
	private DateTimePickerTableModel currentDateTimePickerTableModel;
		
	public JDateTimePickerSelectionPanel(JDateTimePickerModel dateTimePickerModel) {
		this.dateTimePickerModel = dateTimePickerModel;
		this.currentDateTimePickerTableModel = dateTimePickerModel.getDefaultDateTimePickerTableModel();
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		add(getTable(), BorderLayout.CENTER);
		Calendar calendar = Calendar.getInstance();
		//calendar.set(Calendar.HOUR_OF_DAY, 0);
		//calendar.set(Calendar.MINUTE, 0);
		//calendar.set(Calendar.SECOND, 0);
		currentDateTimePickerTableModel.setDate(calendar.getTime());
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setShowGrid(false);
			table.setCellSelectionEnabled(true);
			table.setFocusable(false);
			table.setFillsViewportHeight(true);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			updateTableModel();
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (!e.getValueIsAdjusting()) {
						selected(table.getSelectedRow(), table.getSelectedColumn());
					}
				}
			});
		}
		return table;
	}
	
	private void selected(int row, int col) {
		if (row > -1 && col > -1) { //called in JTable.setModel() with (-1, -1)
			Date date = currentDateTimePickerTableModel.getDate(row, col);
			DateTimePickerTableModel dateTimePickerTableModel = dateTimePickerModel.getNextDateTimePickerTableModel(currentDateTimePickerTableModel);
			if (dateTimePickerTableModel == null) { //no next date part to select
				firePropertyChange("value", null, date);
				firePropertyChange("caret", null, dateTimePickerModel.getCaretPosition(currentDateTimePickerTableModel)); //reset caret to this date part
			}
			else { //next date part
				currentDateTimePickerTableModel = dateTimePickerTableModel;
				currentDateTimePickerTableModel.setDate(date);
				firePropertyChange("header", null, dateTimePickerModel.getHeader(currentDateTimePickerTableModel, date));
				firePropertyChange("value", null, date);
				firePropertyChange("text", null, dateTimePickerModel.getText(currentDateTimePickerTableModel, date));
				firePropertyChange("caret", null, dateTimePickerModel.getCaretPosition(currentDateTimePickerTableModel));
				updateTableModel();
			}
		}
	}
	
	private void updateTableModel() {
		table.setModel(currentDateTimePickerTableModel);
		table.setRowHeight(currentDateTimePickerTableModel.getRowHeight());
		//table.setTableHeader(currentDateTimePickerTableModel.isTableHeader() ? tableHeader : null);
		table.setDefaultRenderer(Object.class, currentDateTimePickerTableModel.getTableCellRenderer());
	}

	public void previous() {
		currentDateTimePickerTableModel.previous();
		Date date = currentDateTimePickerTableModel.getDate();
		firePropertyChange("header", null, dateTimePickerModel.getHeader(currentDateTimePickerTableModel, date));
		firePropertyChange("value", null, date);
		firePropertyChange("text", null, dateTimePickerModel.getText(currentDateTimePickerTableModel, date));
		firePropertyChange("caret", null, dateTimePickerModel.getCaretPosition(currentDateTimePickerTableModel));
	}

	public void next() {
		currentDateTimePickerTableModel.next();
		Date date = currentDateTimePickerTableModel.getDate();
		firePropertyChange("header", null, dateTimePickerModel.getHeader(currentDateTimePickerTableModel, date));
		firePropertyChange("value", null, date);
		firePropertyChange("text", null, dateTimePickerModel.getText(currentDateTimePickerTableModel, date));
		firePropertyChange("caret", null, dateTimePickerModel.getCaretPosition(currentDateTimePickerTableModel));
	}
	
	/** previous date part */
	public void up() {
		DateTimePickerTableModel dateTimePickerTableModel = dateTimePickerModel.getPreviousDateTimePickerTableModel(currentDateTimePickerTableModel);
		if (dateTimePickerTableModel != null) {
			Date date = currentDateTimePickerTableModel.getDate();
			currentDateTimePickerTableModel = dateTimePickerTableModel;
			currentDateTimePickerTableModel.setDate(date);
			firePropertyChange("header", null, dateTimePickerModel.getHeader(currentDateTimePickerTableModel, date));
			firePropertyChange("value", null, date);
			firePropertyChange("text", null, dateTimePickerModel.getText(currentDateTimePickerTableModel, date));
			firePropertyChange("caret", null, dateTimePickerModel.getCaretPosition(currentDateTimePickerTableModel));
			updateTableModel();
		}		
	}
	
	public void carretEvent(int position) {
		DateTimePickerTableModel dateTimePickerTableModel = dateTimePickerModel.getDateTimePickerTableModelPerCaretPosition(position);
		if (dateTimePickerTableModel != null) {
			Date date = currentDateTimePickerTableModel.getDate();
			currentDateTimePickerTableModel = dateTimePickerTableModel;
			currentDateTimePickerTableModel.setDate(date);
			firePropertyChange("header", null, dateTimePickerModel.getHeader(currentDateTimePickerTableModel, date));
			firePropertyChange("value", null, date);
			firePropertyChange("text", null, dateTimePickerModel.getText(currentDateTimePickerTableModel, date));
			updateTableModel();
			
		}
	}
	
}
