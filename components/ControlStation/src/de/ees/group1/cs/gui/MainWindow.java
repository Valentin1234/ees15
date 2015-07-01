package de.ees.group1.cs.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import de.ees.group1.model.ProductionOrder;

public class MainWindow {

	private JFrame frmControlstation;
	private OrdersPanel ordersPanel;
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
			@Override
			public void activeOrderCanceledAction() {}
		};
		
		//TODO: just for testing
		addOrderPanel(new ProductionOrder(20));
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
		
		JMenu mnCom = new JMenu("Verbindung");
		menuBar.add(mnCom);
		
		JMenuItem mntmMAC = new JMenuItem("mit NXT verbinden");
		mntmMAC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		mnCom.add(mntmMAC);
		
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
		panel.setLayout(new MigLayout("", "[50%][50%]", "[][][][][grow]"));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		frmControlstation.getContentPane().add(panel);
		
		ordersPanel = new OrdersPanel(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showAddOrderDialog();
			}
		});
		panel.add(ordersPanel, "cell 0 0,grow, span 1 5");
		
		JPanel actOrderPanel = new ActiveOrderPanel(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.activeOrderCanceledAction();
			}
		});
		panel.add(actOrderPanel, "cell 1 0,grow");
	}
	
	public void registerGUIListener(IGUIListener listener) {
		this.listener = listener;
	}
	
	public void updateOrderList(List<ProductionOrder> list) {
		ListedOrderPanel[] orderPanels = ordersPanel.getOrderPanels();
		int i = 0;
		for(ProductionOrder order : list) {
			if(orderPanels.length < i+1) {
				addOrderPanel(order);
			} else {
				orderPanels[i].setOrder(order);
				orderPanels[i].update();				
			}
			i++;
		}
		//remove excessive panels
		for(;i < orderPanels.length; i++) {
			ordersPanel.removeOrderPanel(orderPanels[i]);
		}
		
	}
	
	public void updateActiveOrder(ProductionOrder order) {
		
	}
	
	private void addOrderPanel(ProductionOrder order) {
		ListedOrderPanel panel = new ListedOrderPanel(order);
		panel.addActionListener(new ActionListener() {
			private ListedOrderPanel target;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == "Edit") {
					//TODO gui update just for testing (should be triggered from listener)
					target.setOrder(showEditOrderDialog(target.getOrder()));
					target.update();
				} else if (e.getActionCommand() == "Up") {
					listener.moveOrderUp(target.getOrder().getId());
				} else if (e.getActionCommand() == "Down") {
					listener.moveOrderDown(target.getOrder().getId());
				} else if (e.getActionCommand() == "Del") {
					listener.orderRemovedAction(target.getOrder().getId());
				}
			}
			
			public ActionListener init(ListedOrderPanel target) {
				this.target = target;
				return this;
			}
		}.init(panel));
		ordersPanel.addOrderPanel(panel);
	}
	
	private void showAddOrderDialog() {
		ProductionOrder proto = new ProductionOrder(listener.getNextOrderId());
		ProductionOrderDialog prodOrderDialog = new ProductionOrderDialog(proto, frmControlstation);
		prodOrderDialog.setLocationRelativeTo(frmControlstation);
		prodOrderDialog.setModal(true);
		prodOrderDialog.setVisible(true);
		
		if(prodOrderDialog.isOrderValid()) {
			listener.orderCreatedAction(proto);
			//TODO just for testing (should be done by the listener)
			addOrderPanel(proto);
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
