package de.ees.group1.cs.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import de.ees.group1.model.WorkstationType;

public class WorkstationPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel statusLbl;

	/**
	 * Create the panel.
	 */
	public WorkstationPanel(int id, ItemListener typeListener, ItemListener qualityListener) {
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
		
		Vector<WorkstationType> types = new Vector<WorkstationType>(Arrays.asList(WorkstationType.values()));
		types.removeElement(WorkstationType.NONE);
		JComboBox<WorkstationType> typeCB = new JComboBox<WorkstationType>(new DefaultComboBoxModel<WorkstationType>(types));
		typeCB.addItemListener(typeListener);
		add(typeCB, "cell 2 0");
		
		JLabel qualityLbl = new JLabel("Qualität");
		add(qualityLbl, "cell 1 1");
		
		Integer[] quality = { 1, 2, 3, 4};
		JComboBox<Integer> qualityCB = new JComboBox<Integer>(new DefaultComboBoxModel<Integer>(quality));
		add(qualityCB, "cell 2 1");
	}
	
	public void setStatus(Object status) {
		//TODO: show current status on GUI
	}
	
	

}
