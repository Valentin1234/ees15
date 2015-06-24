package de.ees.group1.cs.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

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
	
	private JLabel numLabel;
	private JComboBox<String> typeCB;
	private JComboBox<Integer> qualityCB;
	
	private Map<String, WorkStationType> typesMap = new HashMap<String, WorkStationType>() {
		private static final long serialVersionUID = 1L;
	{
		put("Bohren", WorkStationType.DRILL);
		put("Fräsen", WorkStationType.LATHE);
	}};
	
	private Integer[] qualityValues = new Integer[] { 1, 2, 3, 4};

	private JTextField durationTxtField;
	
	KeyAdapter threeNumbersOnly = new KeyAdapter() {
		@Override
		public void keyTyped(KeyEvent e) {
			if(!(e.isActionKey() || (Character.isDigit(e.getKeyChar()) && durationTxtField.getText().length()<3))) {
				e.consume();
			}
		}
	};

	/**
	 * Create the panel.
	 */
	public ProductionStepPanel(int num, ProductionStep step) {
		
		this.step = step;
		
		// layout options
		setLayout(new MigLayout("","[][grow][]","[]"));
		setMinimumSize(new Dimension(250,0));
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		
		Border outer = BorderFactory.createEmptyBorder(2, 0, 2, 0);
		Border inner = BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK);
		Border border = BorderFactory.createCompoundBorder(outer,inner);
		
		JPanel numberPanel = new JPanel(new BorderLayout());
		//numberPanel.setBorder(border);
		numLabel = new JLabel("#"+num, SwingConstants.CENTER);
		numberPanel.add(numLabel, BorderLayout.CENTER);
		
		this.add(numberPanel);
		
		JPanel attribPanel = new JPanel(new MigLayout("","[][][][grow]","push[][][]push"));
		attribPanel.setBorder(border);
		attribPanel.add(new JLabel("Art:"));
		typeCB = new JComboBox<String>(new DefaultComboBoxModel<String>(typesMap.keySet().toArray(new String[3])));
		attribPanel.add(typeCB, "growx, wrap");
		attribPanel.add(new JLabel("Qualität:"));
		qualityCB = new JComboBox<Integer>(new DefaultComboBoxModel<Integer>(qualityValues));
		attribPanel.add(qualityCB, "growx, wrap");
		attribPanel.add(new JLabel("Dauer:"));
		durationTxtField = new JTextField(3);
		durationTxtField.addKeyListener(threeNumbersOnly);
		attribPanel.add(durationTxtField, "grow");
		attribPanel.add(new JLabel("s"));
		
		this.add(attribPanel, "grow");
		
		JPanel btnPanel = new JPanel(new MigLayout("", "[]0", "0[][][]0"));
		//btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
		btnPanel.setBorder(border);
		btnPanel.add(new JButton(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/navigation/Up16.gif"))), "wrap");
		btnPanel.add(new JButton(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Delete16.gif"))), "wrap");
		btnPanel.add(new JButton(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/navigation/Down16.gif"))));
		
		this.add(btnPanel, "grow");
		
	}
	
	public ProductionStep getProductionStep() {
		return step;
	}

}
