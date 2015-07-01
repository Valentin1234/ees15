package de.ees.group1.cs.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

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
	public WorkStationPanel() {
		setBorder(new TitledBorder(null, "Bearbeitungsstation #1", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("", "[][][]", "[][]"));
		
		statusLbl = new JLabel("Wartet");
		statusLbl.setPreferredSize(new Dimension(80, -1));
		statusLbl.setHorizontalAlignment(SwingConstants.CENTER);
		statusLbl.setBackground(Color.YELLOW);
		statusLbl.setOpaque(true);
		statusLbl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
	}

}
