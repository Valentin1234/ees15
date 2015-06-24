package de.ees.group1.cs.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import de.ees.group1.model.ProductionStep;
import net.miginfocom.swing.MigLayout;

public class ProductionOrderDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2681813697137652113L;
	private final JPanel contentPanel = new JPanel();
	
	public JTextField txtId;
	JPanel stepListPanel;

	/**
	 * Create the dialog.
	 */
	public ProductionOrderDialog(int id) {
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
				//lblId.setAlignmentX(Component.LEFT_ALIGNMENT);
				idPanel.add(lblId);
				
				idPanel.add(Box.createHorizontalStrut(5));
				
				txtId = new JTextField(2);
				txtId.setColumns(3);
				txtId.setMaximumSize(txtId.getPreferredSize());
				txtId.setHorizontalAlignment(JTextField.CENTER);
				txtId.setEditable(false);
				txtId.setText(String.valueOf(id));
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
				
				stepsLblPanel.add(new JButton(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Add16.gif"))));
			}
			contentPanel.add(stepsLblPanel);
			
			contentPanel.add(Box.createVerticalStrut(5));
			
			stepListPanel = new JPanel();
			stepListPanel.setLayout(new MigLayout("wrap 1", "grow", ""));
			stepListPanel.add(new ProductionStepPanel(1, new ProductionStep()), "grow");
			stepListPanel.add(new ProductionStepPanel(2, new ProductionStep()), "grow");
			
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
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
