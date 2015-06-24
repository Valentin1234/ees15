package de.ees.group1.cs.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;
import de.ees.group1.model.ProductionStep;
import de.ees.group1.model.WorkStationType;

public class ProductionStepPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ProductionStep step;
	private int number;

	private JLabel numLabel;
	private JComboBox<WorkStationType> typeCB;
	private JComboBox<Integer> qualityCB;
	private Border badValueBorder = BorderFactory
			.createLineBorder(Color.RED, 1);
	private Border goodValueBorder = BorderFactory
			.createEmptyBorder(1, 1, 1, 1);

	private Integer[] qualityValues = new Integer[] { -1, 1, 2, 3, 4 };

	private JTextField durationTxtField;

	KeyAdapter threeNumbersOnly = new KeyAdapter() {
		@Override
		public void keyTyped(KeyEvent e) {
			if (!(e.isActionKey() || (Character.isDigit(e.getKeyChar()) && durationTxtField
					.getText().length() < 3))) {
				e.consume();
			}
		}
	};

	private JButton downButton;

	private JButton deleteButton;

	private JButton upButton;

	/**
	 * Create the panel.
	 */
	public ProductionStepPanel(int num, ProductionStep step) {

		this.number = num;
		this.step = step;

		// layout options
		setLayout(new MigLayout("", "[][grow][]", "[]"));
		setMinimumSize(new Dimension(250, 0));
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));

		Border outer = BorderFactory.createEmptyBorder(2, 0, 2, 0);
		Border inner = BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK);
		Border border = BorderFactory.createCompoundBorder(outer, inner);

		JPanel numberPanel = new JPanel(new BorderLayout());
		{
			numLabel = new JLabel("#" + num, SwingConstants.CENTER);
			numberPanel.add(numLabel, BorderLayout.CENTER);
		}
		this.add(numberPanel);

		JPanel attribPanel = new JPanel(new MigLayout("", "[][][][grow]",
				"push[][][]push"));
		attribPanel.setBorder(border);
		attribPanel.add(new JLabel("Art:"));
		{
			typeCB = new JComboBox<WorkStationType>(
					new DefaultComboBoxModel<WorkStationType>(
							WorkStationType.values()));
			typeCB.setBorder(goodValueBorder);
			attribPanel.add(typeCB, "growx, wrap");
			attribPanel.add(new JLabel("Qualität:"));
			qualityCB = new JComboBox<Integer>(
					new DefaultComboBoxModel<Integer>(qualityValues));
			qualityCB.setBorder(goodValueBorder);
			attribPanel.add(qualityCB, "growx, wrap");
			attribPanel.add(new JLabel("Dauer:"));
			durationTxtField = new JTextField(3);
			durationTxtField.setBorder(goodValueBorder);
			durationTxtField.addKeyListener(threeNumbersOnly);
			attribPanel.add(durationTxtField, "grow");
			attribPanel.add(new JLabel("s"));
		}
		this.add(attribPanel, "grow");

		JPanel btnPanel = new JPanel(new MigLayout("", "[]0", "0[][][]0"));
		btnPanel.setBorder(border);
		{
			upButton = new JButton(new ImageIcon(getClass().getResource(
					"/toolbarButtonGraphics/navigation/Up16.gif")));
			upButton.setActionCommand("UP");
			
			btnPanel.add(upButton, "wrap");

			deleteButton = new JButton(new ImageIcon(getClass().getResource(
					"/toolbarButtonGraphics/general/Delete16.gif")));
			deleteButton.setActionCommand("DEL");
			btnPanel.add(deleteButton, "wrap");

			downButton = new JButton(new ImageIcon(getClass().getResource(
					"/toolbarButtonGraphics/navigation/Down16.gif")));
			downButton.setActionCommand("DOWN");
			
			btnPanel.add(downButton);
		}
		this.add(btnPanel, "grow");

		updateGui();
	}
	
	public void setListener(ActionListener listener) {
		downButton.addActionListener(listener);
		deleteButton.addActionListener(listener);
		upButton.addActionListener(listener);
	}

	public void setNumber(int num) {
		number = num;
		numLabel.setText("#" + num);
	}

	public int getNumber() {
		return number;
	}

	public ProductionStep getProductionStep() {
		return step;
	}

	public boolean validateForm() {
		boolean valid = true;

		if (typeCB.getSelectedItem() == WorkStationType.NONE) {
			typeCB.setBorder(badValueBorder);
			valid = false;
		} else {
			typeCB.setBorder(goodValueBorder);
		}

		if ((Integer) qualityCB.getSelectedItem() == -1) {
			qualityCB.setBorder(badValueBorder);
			valid = false;
		} else {
			qualityCB.setBorder(goodValueBorder);
		}

		if (durationTxtField.getText().length() == 0) {
			durationTxtField.setBorder(badValueBorder);
			valid = false;
		} else {
			durationTxtField.setBorder(goodValueBorder);
		}

		return valid;
	}

	private void updateGui() {
		// select type
		typeCB.setSelectedItem(step.getType());

		// set quality
		qualityCB.setSelectedItem(step.getMinQualityLevel());

		// set duration
		int duration = step.getWorkTimeSeconds();
		durationTxtField.setText(duration > 0 ? String.valueOf(duration) : "");
	}
}
