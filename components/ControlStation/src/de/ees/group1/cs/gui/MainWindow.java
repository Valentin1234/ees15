package de.ees.group1.cs.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
		
		//null object to prevent checks for "null"
		listener = new IGUIListener() {
			@Override
			public void orderRemovedAction(int orderID) {}
			@Override
			public void orderCreatedAction(ProductionOrder order) {}
			@Override
			public int getNextOrderId() { return -1;}
			@Override
			public void moveOrderUp(int orderID) {}
			@Override
			public void moveOrderDown(int orderID) {}
			@Override
			public void orderUpdatedAction(ProductionOrder tmp) {}
		};
		
		addOrder(new ProductionOrder(20));
//		orderListPanel.add(new ListedOrderPanel(new ProductionOrder(21)), "grow");
//		orderListPanel.add(new ListedOrderPanel(new ProductionOrder(22)), "grow");
//		orderListPanel.add(new ListedOrderPanel(new ProductionOrder(23)), "grow");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmControlstation = new JFrame();
		frmControlstation.setTitle("ControlStation");
		frmControlstation.setBounds(100, 100, 800, 600);
		frmControlstation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmControlstation.getContentPane().setLayout(new BoxLayout(frmControlstation.getContentPane(), BoxLayout.X_AXIS));
		
		JMenuBar menuBar = new JMenuBar();
		frmControlstation.setJMenuBar(menuBar);
		
		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);
		
		JMenu mnAuftrag = new JMenu("Auftrag");
		menuBar.add(mnAuftrag);
		
		JMenuItem mntmAnlegen = new JMenuItem("anlegen");
		mntmAnlegen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showAddOrderDialog();
			}
		});
		mnAuftrag.add(mntmAnlegen);
		
		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout("", "[50%][50%]", "[100%]"));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		frmControlstation.getContentPane().add(panel);
		
		orderListPanel = new JPanel();
		orderListPanel.setLayout(new MigLayout("wrap 1", "grow", ""));
		
		JScrollPane scrollPane = new JScrollPane(orderListPanel);
		scrollPane.setMinimumSize(new Dimension(300,-1));
		scrollPane.setBorder(new TitledBorder(null, "anstehende Auftr\u00E4ge", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(scrollPane, "cell 0 0,grow");
		
		JPanel panel_2 = new JPanel();
		panel_2.setMinimumSize(new Dimension(300, -1));
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
		ListedOrderPanel panel = new ListedOrderPanel(order);
		panel.addActionListener(new ActionListener() {
			private ListedOrderPanel target;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == "Edit") {
					target.setOrder(showEditOrderDialog(target.getOrder()));
					target.update();
				}
			}
			
			public ActionListener init(ListedOrderPanel target) {
				this.target = target;
				return this;
			}
		}.init(panel));
		orderListPanel.add(panel, "grow");
		orderListPanel.revalidate();
		orderListPanel.repaint();
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
	
	private void showAddOrderDialog() {
		ProductionOrder proto = new ProductionOrder(listener.getNextOrderId());
		ProductionOrderDialog prodOrderDialog = new ProductionOrderDialog(proto, frmControlstation);
		prodOrderDialog.setModal(true);
		prodOrderDialog.setVisible(true);
		
		if(prodOrderDialog.isOrderValid()) {
			listener.orderCreatedAction(proto);
			//just for testing
			addOrder(proto);
		}
	}
	
	private ProductionOrder showEditOrderDialog(ProductionOrder order) {
		ProductionOrder tmp = new ProductionOrder(order);
		ProductionOrderDialog prodOrderDialog = new ProductionOrderDialog(tmp, frmControlstation);
		prodOrderDialog.setModal(true);
		prodOrderDialog.setVisible(true);
		
		if (prodOrderDialog.isOrderValid()) {
			listener.orderUpdatedAction(tmp);
			return tmp;
		}
		return order;
	}
}
