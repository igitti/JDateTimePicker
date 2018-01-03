package eu.boehner.swing.datetimepicker;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import eu.boehner.swing.datetimepicker.model.JDateTimePickerModel;

import javax.swing.JButton;

/**
 * This class represents the panel containing visible elements for the {@link JDateTimePicker}.
 * 
 * @author Daniel https://github.com/igitti/
 *
 */
public class JDateTimePickerPanel extends JPanel {

	private JPanel headerPanel;
	private JLabel headerLabel;
	private JButton headerPrevButton;
	private JButton headerNextButton;
	
	private JDateTimePickerModel dateTimePickerModel;
	private JDateTimePickerDatePartPanel dateTimePickerDatePartPanel;
	
	public JDateTimePickerPanel(JDateTimePickerModel dateTimePickerModel, PropertyChangeListener valuePropertyChangeListener, PropertyChangeListener textPropertyChangeListener, PropertyChangeListener caretPropertyChangeListener) {
		this.dateTimePickerModel = dateTimePickerModel;
		initialize();
		JDateTimePickerDatePartPanel dateTimePickerDatePartPanel = getDateTimePickerDatePartPanel();
		if (valuePropertyChangeListener != null) {
			dateTimePickerDatePartPanel.addPropertyChangeListener("value", valuePropertyChangeListener);
		}
		if (textPropertyChangeListener != null) {
			dateTimePickerDatePartPanel.addPropertyChangeListener("text", textPropertyChangeListener);
		}
		if (caretPropertyChangeListener != null) {
			dateTimePickerDatePartPanel.addPropertyChangeListener("caret", caretPropertyChangeListener);
		}
	}
	
	public void carretEvent(int position) {
		dateTimePickerDatePartPanel.carretEvent(position);
	}

	private void initialize() {
		setFocusable(false);
		setPreferredSize(new Dimension(240, 160));
		setLayout(new BorderLayout(0, 0));
		add(getHeaderPanel(), BorderLayout.NORTH);
		add(getDateTimePickerDatePartPanel(), BorderLayout.CENTER);
	}
	
	private JPanel getHeaderPanel() {
		if (headerPanel == null) {
			headerPanel = new JPanel();
			headerPanel.setFocusable(false);
			headerPanel.setLayout(new BorderLayout(0, 0));
			headerPanel.add(getHeaderPrevButton(), BorderLayout.WEST);
			headerPanel.add(getHeaderLabel(), BorderLayout.CENTER);
			headerPanel.add(getHeaderNextButton(), BorderLayout.EAST);
		}
		return headerPanel;
	}
	
	private JLabel getHeaderLabel() {
		if (headerLabel == null) {
			headerLabel = new JLabel();
			headerLabel.setFocusable(false);
			headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
			MouseListener mouseListener = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					getDateTimePickerDatePartPanel().up();
				}
			};
			headerLabel.addMouseListener(mouseListener);

		}
		return headerLabel;
	}
	
	private JButton getHeaderPrevButton() {
		if (headerPrevButton == null) {
			headerPrevButton = new JButton("◄");
			headerPrevButton.setFocusable(false);
			headerPrevButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getDateTimePickerDatePartPanel().previous();
				}
			});
		}
		return headerPrevButton;
	}
	
	private JButton getHeaderNextButton() {
		if (headerNextButton == null) {
			headerNextButton = new JButton("►");
			headerNextButton.setFocusable(false);
			headerNextButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getDateTimePickerDatePartPanel().next();
				}
			});
		}
		return headerNextButton;
	}

	private JDateTimePickerDatePartPanel getDateTimePickerDatePartPanel() {
		if (dateTimePickerDatePartPanel == null) {
			dateTimePickerDatePartPanel = new JDateTimePickerDatePartPanel(dateTimePickerModel);
			
			PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					getHeaderLabel().setText(evt.getNewValue().toString());
				}
			};
			dateTimePickerDatePartPanel.addPropertyChangeListener("header", propertyChangeListener);
		}
		return dateTimePickerDatePartPanel;
	}
}
