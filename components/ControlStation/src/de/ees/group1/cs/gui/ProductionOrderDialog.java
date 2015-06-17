package de.ees.group1.cs.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;

public class ProductionOrderDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2681813697137652113L;
	private final JPanel contentPanel = new JPanel();
	
	public JTextField txtId;

	/**
	 * Create the dialog.
	 */
	public ProductionOrderDialog(int id) {
		setTitle("Auftrag erstellen/bearbeiten");
		setBounds(100, 100, 450, 300);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[]", "[]"));
		{
			JLabel lblId = new JLabel("ID:");
			contentPanel.add(lblId, "cell 0 0");
			
			txtId = new JTextField(2);
			txtId.setEditable(false);
			txtId.setText(String.valueOf(id));
			contentPanel.add(txtId, "wrap");
			
			
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
