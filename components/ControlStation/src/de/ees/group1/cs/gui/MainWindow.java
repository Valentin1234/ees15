package de.ees.group1.cs.gui;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import de.ees.group1.model.ProductionOrder;

public class MainWindow {

	private JFrame frmControlstation;
	private JPanel orderListPanel;
	private IGUIListener listener;

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
		panel.setLayout(new MigLayout("", "[50%][50%]", "[100%]"));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		frmControlstation.getContentPane().add(panel);
		
		orderListPanel = new JPanel();
		orderListPanel.setBorder(new TitledBorder(null, "anstehende Auftr\u00E4ge", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		orderListPanel.setLayout(new MigLayout("wrap 1", "[fill]", ""));
		panel.add(orderListPanel, "cell 0 0,grow");
		
		orderListPanel.add(new ListedOrderPanel(new ProductionOrder(20)));
		orderListPanel.add(new ListedOrderPanel(new ProductionOrder(21)));
		orderListPanel.add(new ListedOrderPanel(new ProductionOrder(22)));
		orderListPanel.add(new ListedOrderPanel(new ProductionOrder(23)));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "aktueller Auftrag", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(panel_2, "cell 1 0,grow");
		panel_2.setLayout(new MigLayout("", "[][]", "[][][]"));
		
		JLabel lblNewLabel = new JLabel("Auftragsnummer:");
		panel_2.add(lblNewLabel, "cell 0 0");
		
		JLabel lblNewLabel_1 = new JLabel("akt. Bearb.-schritt:");
		panel_2.add(lblNewLabel_1, "cell 0 1");
	}
	
	public void registerGUIListener(IGUIListener listener) {
		this.listener = listener;
	}
	
	public void addOrder(ProductionOrder order) {
		orderListPanel.add(new ListedOrderPanel(order));
	}
	
	public void removeOrder(int id) {
		for(Component c : orderListPanel.getComponents()) {
			if(((ListedOrderPanel)c).getID() == id) {
				orderListPanel.remove(c);
				break;
			}
		}
	}
	
	public void updateActiveOrder(ProductionOrder order) {
		
	}
}
