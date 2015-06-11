package de.ees.group1.cs.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;

public class MainWindow {

	private JFrame frmControlstation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmControlstation.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmControlstation = new JFrame();
		frmControlstation.setTitle("ControlStation");
		frmControlstation.setBounds(100, 100, 480, 400);
		frmControlstation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmControlstation.setJMenuBar(menuBar);
		
		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);
		
		JMenu mnAuftrag = new JMenu("Auftrag");
		menuBar.add(mnAuftrag);
		
		JMenuItem mntmAnlegen = new JMenuItem("anlegen");
		mnAuftrag.add(mntmAnlegen);
		frmControlstation.getContentPane().setLayout(new BoxLayout(frmControlstation.getContentPane(), BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		frmControlstation.getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "anstehende Auftr\u00E4ge", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setLayout(new BorderLayout(0, 0));
		panel.setLayout(new MigLayout("", "[50%][50%]", "[331px]"));
		panel.setLayout(new MigLayout("", "[50%][50%]", "[100%]"));
		panel.add(panel_1, "cell 0 0,grow");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "aktueller Auftrag", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(panel_2, "cell 1 0,grow");
	}
}
