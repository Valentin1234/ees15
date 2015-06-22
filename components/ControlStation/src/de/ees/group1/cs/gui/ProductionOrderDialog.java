package de.ees.group1.cs.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			JPanel idPanel = new JPanel();
			idPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			idPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			JLabel lblId = new JLabel("ID:");
			idPanel.add(lblId);
			
			txtId = new JTextField(2);
			txtId.setEditable(false);
			txtId.setText(String.valueOf(id));
			idPanel.add(txtId);
			
			contentPanel.add(idPanel);
			
			JLabel lblSteps = new JLabel("Bearbeitungsschritte:");
			lblSteps.setAlignmentX(Component.LEFT_ALIGNMENT);
			contentPanel.add(lblSteps);
			
			JPanel stepListPanel = new JPanel();
			
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
