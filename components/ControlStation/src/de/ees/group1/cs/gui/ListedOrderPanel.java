package de.ees.group1.cs.gui;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import de.ees.group1.model.ProductionOrder;

public class ListedOrderPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3066810592948806853L;
	
	private int id;

	/**
	 * Create the panel.
	 */
	public ListedOrderPanel(ProductionOrder order) {
		
		// layout options
		setLayout(new MigLayout("", "[][][right,grow][]", "[]"));
		setMinimumSize(new Dimension(250,0));
		
		add(new JLabel("ID:"));
		
		id = order.getId();
		JTextField TxtId = new JTextField(String.format("%2s", order.getId()), 3);
		TxtId.setEditable(false);
		add(TxtId);
		
		JButton editBtn = new JButton(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Edit16.gif")));
		add(editBtn);
		JButton removeBtn = new JButton(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Delete16.gif")));
		add(removeBtn, "wrap");
		
		add(new JLabel("#Schritte:"));
		
		JTextField numStepsTxt = new JTextField(String.valueOf(order.size()), 3);
		numStepsTxt.setEditable(false);
		add(numStepsTxt);
	}
	
	public int getID() {
		return id;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(getParent().getSize().width, super.getPreferredSize().height);
	}

}
