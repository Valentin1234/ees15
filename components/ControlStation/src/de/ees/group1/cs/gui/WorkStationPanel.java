package de.ees.group1.cs.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import de.ees.group1.model.WorkStationType;
import net.miginfocom.swing.MigLayout;

public class WorkStationPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel statusLbl;

	/**
	 * Create the panel.
	 */
	public WorkStationPanel(int id) {
		setBorder(new TitledBorder(null, "Bearbeitungsstation #"+id, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("", "[][][]", "[][]"));
		
		statusLbl = new JLabel("Wartet");
		statusLbl.setPreferredSize(new Dimension(80, -1));
		statusLbl.setHorizontalAlignment(SwingConstants.CENTER);
		statusLbl.setBackground(Color.YELLOW);
		statusLbl.setOpaque(true);
		statusLbl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		add(statusLbl, "span 1 3,grow");
		
		JLabel typeLbl = new JLabel("Typ:");
		add(typeLbl, "cell 1 0");
		
		Vector<WorkStationType> types = new Vector<WorkStationType>(Arrays.asList(WorkStationType.values()));
		types.removeElement(WorkStationType.NONE);
		JComboBox<WorkStationType> typeCB = new JComboBox<WorkStationType>(new DefaultComboBoxModel<WorkStationType>(types));
		add(typeCB, "cell 2 0");
		
		JLabel qualityLbl = new JLabel("Qualität");
		add(qualityLbl, "cell 1 1");
		
		Integer[] quality = { 1, 2, 3, 4};
		JComboBox<Integer> qualityCB = new JComboBox<Integer>(new DefaultComboBoxModel<Integer>(quality));
		add(qualityCB, "cell 2 1");
	}

}
