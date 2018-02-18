package eu.boehner.swing.datetimepicker;

import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import eu.boehner.swing.datetimepicker.model.CaretPosition;
import eu.boehner.swing.datetimepicker.model.JDateTimePickerModel;

/**
 * A JDataTimePicker is a {@link JFormattedTextField} showing a popup window for selecting
 * date/time by clicking or touching.<br/>
 * When a selection is made (e. g. year) the next part is activated and gets ready for selection (e. g. month).
 * 
 * @author Daniel https://github.com/igitti/
 */
public class JDateTimePicker extends JFormattedTextField {
	
	protected JDateTimePickerModel dateTimePickerModel;
	protected JDateTimePickerPanel dateTimePickerPanel;
	protected volatile boolean doCarretEvent = true;
	protected volatile boolean doPorpertyUpdate = true;
	
	public JDateTimePicker(JDateTimePickerModel dateTimePickerModel) {
		super(dateTimePickerModel.getFormat());
		this.dateTimePickerModel = dateTimePickerModel;
		
		final PropertyChangeListener valuePropertyChangeListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Object object =  evt.getNewValue();
				if (object instanceof Date) {
					if (doPorpertyUpdate) {
						doCarretEvent = false;
						JDateTimePicker.this.setValue(object);
						doCarretEvent = true;
					}
				}
			}
		};
		
		final PropertyChangeListener textPropertyChangeListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (doPorpertyUpdate) {
					doCarretEvent = false;
					JDateTimePicker.this.setText(evt.getNewValue().toString());
					doCarretEvent = true;
				}
			}
		};
		
		final PropertyChangeListener caretPropertyChangeListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (doPorpertyUpdate) {
					doCarretEvent = false;
					CaretPosition caretPosition = (CaretPosition)evt.getNewValue();
					JDateTimePicker.this.setSelectionStart(caretPosition.getFrom());
					JDateTimePicker.this.setSelectionEnd(caretPosition.getTo());
					doCarretEvent = true;
				}
			}
		};
		
		addFocusListener(new FocusListener() {
			private Popup popup;
			@Override
			public void focusLost(FocusEvent e) {
				if (popup != null) {
					popup.hide();
					popup = null;
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				Point point = getLocationOnScreen();
				dateTimePickerPanel = new JDateTimePickerPanel(dateTimePickerModel, valuePropertyChangeListener, textPropertyChangeListener, caretPropertyChangeListener);
				popup = PopupFactory.getSharedInstance().getPopup(JDateTimePicker.this, dateTimePickerPanel, point.x, point.y + JDateTimePicker.this.getHeight());
				popup.show();
			}
		});
		
		addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				if (doCarretEvent) {
					doPorpertyUpdate = false;
					dateTimePickerPanel.carretEvent(e.getDot());
					doPorpertyUpdate = true;
				}
			}
		});
	}
	
	
}
