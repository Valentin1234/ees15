package de.ees.group1.cs.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import de.ees.group1.model.ProductionOrder;
import de.ees.group1.model.ProductionStep;
import net.miginfocom.swing.MigLayout;

public class ProductionOrderDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2681813697137652113L;
	private final JPanel contentPanel = new JPanel();
	
	public JTextField txtId;
	JPanel stepListPanel;

	private List<ProductionStepPanel> stepPanels = new LinkedList<ProductionStepPanel>();
	
	private ProductionOrder order;
	private boolean valid;
	
	/**
	 * Create the dialog.
	 */
	public ProductionOrderDialog(ProductionOrder order, JFrame owner) {
		super(owner);
		
		this.order = order;
		
		setTitle("Auftrag erstellen/bearbeiten");
		setBounds(100, 100, 600, 500);
		//setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			JPanel idPanel = new JPanel();
			idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.X_AXIS));
			idPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			{
				JLabel lblId = new JLabel("ID:");
				idPanel.add(lblId);
				
				idPanel.add(Box.createHorizontalStrut(5));
				
				txtId = new JTextField(2);
				txtId.setColumns(3);
				txtId.setMaximumSize(txtId.getPreferredSize());
				txtId.setHorizontalAlignment(JTextField.CENTER);
				txtId.setEditable(false);
				txtId.setText(String.valueOf(order.getId()));
				idPanel.add(txtId);
			}
			contentPanel.add(idPanel);
			
			contentPanel.add(Box.createVerticalStrut(5));
			
			JPanel stepsLblPanel = new JPanel();
			stepsLblPanel.setLayout(new BoxLayout(stepsLblPanel, BoxLayout.X_AXIS));
			stepsLblPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			{
				stepsLblPanel.add(new JLabel("Bearbeitungsschritte:"));
				
				stepsLblPanel.add(Box.createHorizontalGlue());
				
				JButton addStepButton = new JButton(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Add16.gif")));
				addStepButton.setActionCommand("newStep");
				addStepButton.addActionListener(this);
				stepsLblPanel.add(addStepButton);
			}
			contentPanel.add(stepsLblPanel);
			
			contentPanel.add(Box.createVerticalStrut(5));
			
			stepListPanel = new JPanel();
			stepListPanel.setLayout(new MigLayout("wrap 1", "grow", ""));
			//stepListPanel.add(new ProductionStepPanel(1, new ProductionStep()), "grow");
			//stepListPanel.add(new ProductionStepPanel(2, new ProductionStep()), "grow");
			
			JScrollPane stepListScrollPane = new JScrollPane(stepListPanel);
			stepListScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
			stepListScrollPane.setPreferredSize(stepListScrollPane.getMaximumSize());
			contentPanel.add(stepListScrollPane);
			
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		for(ProductionStep step : order) {
			addStep(step);
		}
	}
	
	public boolean isOrderValid() {
		return valid;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand() == "OK") {
			valid = true;
			for(ProductionStepPanel p : stepPanels) {
				valid &= p.validateForm();
			}
			
			if(!valid) {
				JOptionPane.showMessageDialog(this, "Es fehlen noch Daten!");
			} else {
				for(ProductionStepPanel p : stepPanels) {
					order.add(p.getProductionStep());
				}
				this.dispose();
			}
		} else if(e.getActionCommand() == "Cancel") {
			valid = false;
			this.dispose();
		} else if(e.getActionCommand() == "newStep") {
			addStep(new ProductionStep());
		}		
	}
	
	private void addStep(ProductionStep step) {
		
		int stepID = stepPanels.size()+1;
		
		ProductionStepPanel panel = new ProductionStepPanel(stepID, step);
		panel.setListener(new ActionListener() {
			ProductionStepPanel target;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == "DEL") {
					stepListPanel.remove(target);
					stepPanels.remove(target);
					updateStepIndices();
					stepListPanel.revalidate();
					stepListPanel.repaint();
				} else if(e.getActionCommand() == "UP") {
					int id = target.getNumber();
					if(id > 1) {
						stepListPanel.removeAll();
						stepPanels.remove(target);
						stepPanels.add(id - 2, target);
						for(Component c : stepPanels) {
							stepListPanel.add(c, "grow");
						}
						updateStepIndices();
						stepListPanel.revalidate();
						stepListPanel.repaint();
					}
				} else if(e.getActionCommand() == "DOWN") {
						int id = target.getNumber();
						if(id < stepPanels.size()-1) {
							stepListPanel.removeAll();
							stepPanels.remove(target);
							stepPanels.add(id, target);
							for(Component c : stepPanels) {
								stepListPanel.add(c, "grow");
							}
							updateStepIndices();
							stepListPanel.revalidate();
							stepListPanel.repaint();
						}
					}
			}
			
			private ActionListener init(ProductionStepPanel t) {
				target = t;
				return this;
			}
		}.init(panel));
		
		
		stepListPanel.add(panel, "grow");
		stepPanels.add(panel);
		stepListPanel.revalidate();
	}
	
	private void updateStepIndices() {
		int i = 1;
		for(ProductionStepPanel p : stepPanels) {
			p.setNumber(i++);
		}
	}

}
