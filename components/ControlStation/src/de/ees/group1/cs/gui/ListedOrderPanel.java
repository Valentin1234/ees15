package de.ees.group1.cs.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
	
	private ProductionOrder order;

	private int id;

	private JButton editBtn;

	private JButton upBtn;

	private JButton removeBtn;

	private JButton downBtn;

	private JTextField idTxt;

	private JTextField numStepsTxt;

	/**
	 * Create the panel.
	 */
	public ListedOrderPanel(ProductionOrder order) {
		
		this.order = order;
		
		// layout options
		setLayout(new MigLayout("", "[][][right,grow][]", "[]"));
		setMinimumSize(new Dimension(250,0));
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		
		add(new JLabel("ID:"));
		
		id = order.getId();
		idTxt = new JTextField(String.format("%2s", order.getId()), 3);
		idTxt.setEditable(false);
		add(idTxt);
		
		editBtn = new JButton(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Edit16.gif")));
		editBtn.setActionCommand("Edit");
		add(editBtn);
		
		upBtn = new JButton(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/navigation/Up16.gif")));
		upBtn.setActionCommand("Up");
		add(upBtn, "wrap");
		
		add(new JLabel("#Schritte:"));
		
		numStepsTxt = new JTextField(String.valueOf(order.size()), 3);
		numStepsTxt.setEditable(false);
		add(numStepsTxt);
		
		removeBtn = new JButton(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Delete16.gif")));
		removeBtn.setActionCommand("Del");
		add(removeBtn);
		
		downBtn = new JButton(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/navigation/Down16.gif")));
		downBtn.setActionCommand("Down");
		add(downBtn);
	}
	
	public void addActionListener(ActionListener listener) {
		editBtn.addActionListener(listener);
		upBtn.addActionListener(listener);
		removeBtn.addActionListener(listener);
		downBtn.addActionListener(listener);
	}
	
	public ProductionOrder getOrder() {
		return order;
	}
	
	public void setOrder(ProductionOrder order) {
		this.order = order;
	}

	public int getID() {
		return id;
	}
	
	public void update() {
		idTxt.setText(String.format("%2s", order.getId()));
		numStepsTxt.setText(String.valueOf(order.size()));
	}
}
