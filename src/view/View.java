package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Category;
import model.CategoryDAO;
import model.Manufacturer;
import model.ManufacturerDAO;
import model.Product;
import model.ProductDAO;
import model.Warehouse;
import model.WarehouseDAO;

public class View extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String APPLICATION_TITLE = "Warehouse management";
	private static final String BUTTON_SKIP = "Skip";
	private static final String BUTTON_EXPORT = "Export";
	private static final String BUTTON_EDIT = "Edit";
	private static final String BUTTON_ADD = "Add";
	private static final String BUTTON_SEARCH = "Search";
	private static final String BUTTON_DELETE = "Delete";
	private static final String BUTTON_SAVE = "Save";

	private static final String LABEL_PRICE = "Price";
	private static final String LABEL_EXPIRY_DATE = "Expiry date";
	private static final String LABEL_MANUFACTURER = "Manufacturer";
	private static final String LABEL_WAREHOUSE = "Warehouse";
	private static final String LABEL_PRODUCT_ID = "Product ID";
	private static final String LABEL_CATEGORY = "Category";
	private static final String LABEL_AMOUNT = "Amount";
	private static final String LABEL_PRODUCT_NAME = "Product name";
	
	// JPanel
	private JPanel pnlLeft;
	private JPanel pnlContent;
	private JPanel pnlContent_Top;
	private JPanel pnlContent_Center;
	private JPanel pnlContent_Center_Center;
	private JPanel pnlContent_Center_Bottom;
	private JPanel pnlContent_Center_Bottom_Search;
	private JPanel pnlContent_Center_Bottom_Export;
	private JPanel pnlContent_Bottom;

	// JTextField
	private JTextField txtProductId;
	private JTextField txtProductName;
	private JTextField txtExpiryDate;
	private JTextField txtPrice;
	private JTextField txtAmount;
	private JTextField txtSearch;

	// JComboBox
	private JComboBox<?> cboWarehouse;
	private JComboBox<?> cboCategory;
	private JComboBox<?> cboManufacturer;

	// JLabel
	private JLabel lblWarehouse;
	private JLabel lblProductId;
	private JLabel lblCategory;
	private JLabel lblProductName;
	private JLabel lblManufacturer;
	private JLabel lblExpiryDate;
	private JLabel lblPrice;
	private JLabel lblAmount;
	private JLabel lblSearch;

	// JButton
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnSave;
	private JButton btnSkip;
	private JButton btnSearch;
	private JButton btnExport;

	// JTable
	private JTable tblWarehouse;
	private JTable tblProduct;

	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

	// DATA initial
	private ProductDAO productDAOInit = new ProductDAO();
	private WarehouseDAO warehouseDAOInit = new WarehouseDAO();
	private ManufacturerDAO manufacturerDAOInit = new ManufacturerDAO();
	private CategoryDAO categoryDAOInit = new CategoryDAO();

	private List<Category> categories = new ArrayList<>();
	private List<Manufacturer> manufacturers = new ArrayList<>();
	private List<Warehouse> warehouses = new ArrayList<>();
	// private List<Product> productsInit = productDAOInit.getList();

	// Application state
	private int selectedWarehouse = 1;
	private int selectedCategory = 1;
	private int selectedManufacturer = 1;

	public View() {
		setLayout(new BorderLayout());
		setTitle(APPLICATION_TITLE);
		setSize(850, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		// Call to get init data
		// getWarehouse();
		// getProducts();

		leftPanel();
		contentPanel();
		add(pnlContent, BorderLayout.CENTER);

		// disable form
		toggleForm(false);

		// Start program: default warehouse selected = 1;
		// fillToTable(productDAOInit.filterProductByWarehouse(productsInit,
		// String.valueOf(selectedWarehouse)));
		pack();
	}

	private void leftPanel() {
		String column[] = { "No.", "Warehouse" };

		// TODO: tblWarehouse data
		String data[][] = { {}, {} };

		pnlLeft = new JPanel();
		add(pnlLeft, BorderLayout.WEST);

		tblWarehouse = new JTable(new DefaultTableModel(data, column));

		JScrollPane spTonKho = new JScrollPane(tblWarehouse, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spTonKho.setPreferredSize(new Dimension(250, 600));
		getContentPane().add(splitPane);
		splitPane.setLeftComponent(pnlLeft);
		pnlLeft.add(spTonKho);

		// tblWarehouse.addMouseListener(new MouseAdapter() {
		// public void mouseClicked(MouseEvent evt) {
		// tblTonKhoMouseClicked(evt);
		// }
		// });
	}

	private void contentPanel() {
		pnlContent = new JPanel();
		pnlContent.setLayout(new BorderLayout());

		contentTopPanel();
		pnlContent.add(pnlContent_Top, BorderLayout.NORTH);

		contentCenterPanel();
		pnlContent.add(pnlContent_Center, BorderLayout.CENTER);

		contentBottomPanel();
		pnlContent.add(pnlContent_Bottom, BorderLayout.SOUTH);

	}

	private void contentTopPanel() {
		pnlContent_Top = new JPanel();
		pnlContent_Top.setLayout(new FlowLayout(FlowLayout.LEFT));

		btnSkip = new JButton(BUTTON_SKIP);
		btnEdit = new JButton(BUTTON_EDIT);
		btnAdd = new JButton(BUTTON_ADD);
		btnDelete = new JButton(BUTTON_DELETE);
		btnSave = new JButton(BUTTON_SAVE);

		pnlContent_Top.add(btnAdd);
		pnlContent_Top.add(btnEdit);
		pnlContent_Top.add(btnDelete);
		pnlContent_Top.add(btnSave);
		pnlContent_Top.add(btnSkip);


		btnSkip.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO btnBoQua
				cboWarehouse.setEnabled(false);
				cboManufacturer.setEnabled(false);
				cboCategory.setEnabled(false);
				txtPrice.setEnabled(false);
				txtExpiryDate.setEnabled(false);
				txtProductId.setEnabled(false);
				txtAmount.setEnabled(false);
				txtProductName.setEnabled(false);
				txtSearch.setEnabled(false);

			}
		});

		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO btnSua

				// enable form
				toggleForm(true);

				// get text from ma hang hoa field
				String maHangHoa = txtProductId.getText();

				if (maHangHoa.equals("")) {
					JOptionPane.showMessageDialog(null, "Vui long nhap ma hang hoa");
				} else {
					// tim kiem hang hoa theo ma lay duoc trong list
					Product pFound = productDAOInit.find(maHangHoa);

					// get text from field
					String kho = cboWarehouse.getSelectedItem().toString();
					String phanLoai = cboCategory.getSelectedItem().toString();
					String ten = txtProductName.getText();
					String hang = cboManufacturer.getSelectedItem().toString();
					String han = txtExpiryDate.getText();
					String gia = txtPrice.getText();
					String soLuong = txtAmount.getText();

					// pFound.setCode(gia);
					// pFound.setKho(kho);
					// pFound.setPhanloai(phanLoai);
					// pFound.setName(ten);
					// pFound.setHang(hang);
					// pFound.setExpiryDate(han);
					// pFound.setPrice(gia);
					// pFound.setTonKho(soLuong);
					// productDAOInit.update(pFound);
					// for (Product product : productsInit) {
					// System.out.println(product);
					// }
					// fillToTable(productsInit);
				}

			}
		});

		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO btnXoa
				System.out.println("btnXoa");

				// get text from ma hang hoa field
				String maHangHoa = txtProductId.getText();

				if (maHangHoa.equals("")) {
					JOptionPane.showMessageDialog(null, "Vui long nhap ma hang hoa");
				} else {
					// tim kiem hang hoa theo ma lay duoc trong list
					Product pFound = productDAOInit.find(maHangHoa);

					// productDAOInit.remove(pFound);

					// reload table
					// fillToTable(productsInit);
				}
			}
		});

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO btnThem

				// enable form
				toggleForm(true);

				// clear form
				// clearForm();

			}
		});
		

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO btnLuu
				//
				// // get product from form
				// Product productToSave = formToProduct();
				//
				// // change product
				// Warehouse w = warehouseDAOInit.find(warehouses, productToSave.getKho());
				// Manufacturer m = manufacturerDAOInit.find(manufacturers,
				// productToSave.getHang());
				// Category c = categoryDAOInit.find(categories, productToSave.getPhanloai());
				//
				// // set product
				// productToSave.setKho(String.valueOf(w.getId()));
				// productToSave.setHang(String.valueOf(m.getId()));
				// productToSave.setPhanloai(String.valueOf(c.getId()));
				//
				// // insert product
				// boolean isInserted = productDAOInit.insert(productToSave);
				//
				// if (isInserted) {
				// clearForm();
				// List<Product> products = new ArrayList<>();
				// try {
				// products = productDAOInit.getAll();
				// } catch (SQLException ex) {
				// Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
				// }
				//
				// // reload table
				// fillToTable(products);
				//
				// // show dialog
				// JOptionPane.showMessageDialog(null, "Insert successfully!");
				// } else {
				// JOptionPane.showMessageDialog(null, "Insert fail!");
				// }
			}
		});
	
	}

	private void contentCenterPanel() {
		pnlContent_Center = new JPanel();
		pnlContent_Center.setLayout(new BorderLayout());

		contentCenterCenterPanel();
		pnlContent_Center.add(pnlContent_Center_Center, BorderLayout.CENTER);

		contentCenterBottomPanel();
		pnlContent_Center.add(pnlContent_Center_Bottom, BorderLayout.SOUTH);
	}

	private void contentCenterCenterPanel() {
		pnlContent_Center_Center = new JPanel(new GridBagLayout());

		// compoinent Center
		txtPrice = new JTextField(7);
		txtExpiryDate = new JTextField(7);
		txtProductId = new JTextField(7);
		txtAmount = new JTextField(7);
		txtProductName = new JTextField(30);

		lblPrice = new JLabel(LABEL_PRICE, JLabel.LEFT);
		lblExpiryDate = new JLabel(LABEL_EXPIRY_DATE, JLabel.LEFT);
		lblManufacturer = new JLabel(LABEL_MANUFACTURER, JLabel.LEFT);
		lblWarehouse = new JLabel(LABEL_WAREHOUSE);
		lblProductId = new JLabel(LABEL_PRODUCT_ID, JLabel.LEFT);
		lblCategory = new JLabel(LABEL_CATEGORY, JLabel.RIGHT);
		lblAmount = new JLabel(LABEL_AMOUNT, JLabel.LEFT);
		lblProductName = new JLabel(LABEL_PRODUCT_NAME, JLabel.LEFT);

		String[] warehouseName = warehouseDAOInit.getWarehouseName();
		String[] manufacturerName = manufacturerDAOInit.getManufacturerName();
		String[] categoryname = categoryDAOInit.getCategoryName();

		cboWarehouse = new JComboBox(warehouseName);
		cboManufacturer = new JComboBox(manufacturerName);
		cboCategory = new JComboBox(categoryname);

		// end compoinent
		addItem(pnlContent_Center_Center, lblWarehouse, 0, 0, 1, 1, GridBagConstraints.WEST);
		addItem(pnlContent_Center_Center, cboWarehouse, 1, 0, 5, 1, GridBagConstraints.WEST);
		addItem(pnlContent_Center_Center, lblProductId, 0, 1, 1, 1, GridBagConstraints.WEST);
		addItem(pnlContent_Center_Center, txtProductId, 1, 1, 1, 1, GridBagConstraints.WEST);
		addItem(pnlContent_Center_Center, lblCategory, 2, 1, 1, 1, GridBagConstraints.WEST);
		addItem(pnlContent_Center_Center, cboCategory, 3, 1, 3, 1, GridBagConstraints.WEST);
		addItem(pnlContent_Center_Center, lblProductName, 0, 2, 1, 1, GridBagConstraints.WEST);
		addItem(pnlContent_Center_Center, txtProductName, 1, 2, 5, 1, GridBagConstraints.EAST);
		addItem(pnlContent_Center_Center, lblManufacturer, 0, 3, 1, 1, GridBagConstraints.WEST);
		addItem(pnlContent_Center_Center, cboManufacturer, 1, 3, 5, 1, GridBagConstraints.WEST);
		addItem(pnlContent_Center_Center, lblExpiryDate, 0, 4, 1, 1, GridBagConstraints.WEST);
		addItem(pnlContent_Center_Center, txtExpiryDate, 1, 4, 1, 1, GridBagConstraints.WEST);
		addItem(pnlContent_Center_Center, lblPrice, 2, 4, 1, 1, GridBagConstraints.WEST);
		addItem(pnlContent_Center_Center, txtPrice, 3, 4, 1, 1, GridBagConstraints.WEST);
		addItem(pnlContent_Center_Center, lblAmount, 4, 4, 1, 1, GridBagConstraints.WEST);
		addItem(pnlContent_Center_Center, txtAmount, 5, 4, 1, 1, GridBagConstraints.EAST);

		cboWarehouse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				int selectedW = cboWarehouse.getSelectedIndex() + 1;

				// fill to table
				// fillToTable(productDAOInit.filterProductByWarehouse(productsInit,
				// String.valueOf(selectedW)));
			}
		});
		
	}

	private void contentCenterBottomPanel() {
		pnlContent_Center_Bottom = new JPanel(new BorderLayout());

		contentCenterBottomSearch();
		pnlContent_Center_Bottom.add(pnlContent_Center_Bottom_Search, BorderLayout.WEST);
		
		contentCenterBottomExport();
		pnlContent_Center_Bottom.add(pnlContent_Center_Bottom_Export, BorderLayout.CENTER);
	}

	private void contentCenterBottomSearch() {
		pnlContent_Center_Bottom_Search = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		txtSearch = new JTextField(10);
		lblSearch = new JLabel("Tìm kiếm", JLabel.LEFT);
		btnSearch = new JButton(BUTTON_SEARCH);
		
		pnlContent_Center_Bottom_Search.add(lblSearch);
		pnlContent_Center_Bottom_Search.add(txtSearch);
		pnlContent_Center_Bottom_Search.add(btnSearch);
		
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO btnTim
				String search = txtSearch.getText();
				if (search.equals("")) {

				}

				// fillToTable(productDAOInit.findBy(productsInit, search));
			}
		});

	}
	

	private void contentCenterBottomExport() {
		pnlContent_Center_Bottom_Export = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		btnExport = new JButton(BUTTON_EXPORT);
		pnlContent_Center_Bottom_Export.add(btnExport);
		

		btnExport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO btnKetXuat
				System.out.println("btnKetXuat");
			}
		});
	}
	
	private void contentBottomPanel() {
		pnlContent_Bottom = new JPanel();

		String data[][] = { { "", "", "", "" }, { "", "", "", "" }, { "", "", "", "" }, { "", "", "", "" } };

		String column[] = { LABEL_PRODUCT_ID, LABEL_PRODUCT_NAME, LABEL_EXPIRY_DATE, LABEL_AMOUNT };
		tblProduct = new JTable(new DefaultTableModel(data, column));
		tblProduct.setSize(600, 300);
		JScrollPane spKetXuat = new JScrollPane(tblProduct, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		spKetXuat.setPreferredSize(new Dimension(600, 300));
		pnlContent_Bottom.add(spKetXuat);

		getContentPane().add(splitPane);
		splitPane.setRightComponent(pnlContent);
		
		// tblProduct.addMouseListener(new MouseAdapter() {
		// public void mouseClicked(MouseEvent evt) {
		// tblKetXuatMouseClicked(evt);
		// }
		// });
	}

	// private void getProducts() {
	// try {
	// productsInit = productDAOInit.getAll();
	// } catch (SQLException ex) {
	// Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
	// }
	// }

	private Product formToProduct() {
		// get text from field
		String maHangHoa = txtProductId.getText();
		String kho = cboWarehouse.getSelectedItem().toString();
		String phanLoai = cboCategory.getSelectedItem().toString();
		String ten = txtProductName.getText();
		String hang = cboManufacturer.getSelectedItem().toString();
		String han = txtExpiryDate.getText();
		String gia = txtPrice.getText();
		String soLuong = txtAmount.getText();

		// TODO validate data

		Product p = new Product();
		// p.setCode(maHangHoa);
		// p.setKho(kho);
		// p.setPhanloai(phanLoai);
		// p.setName(ten);
		// p.setHang(hang);
		// p.setExpiryDate(han);
		// p.setPrice(gia);
		// p.setTonKho(soLuong);

		return p;
	}

	private void toggleForm(boolean isEnable) {
		btnSave.setEnabled(isEnable);
		btnSkip.setEnabled(isEnable);
		btnSave.setEnabled(isEnable);
		txtPrice.setEnabled(isEnable);
		txtExpiryDate.setEnabled(isEnable);
		txtProductId.setEnabled(isEnable);
		txtAmount.setEnabled(isEnable);
		txtProductName.setEnabled(isEnable);
		cboManufacturer.setEnabled(isEnable);
		cboCategory.setEnabled(isEnable);
		cboWarehouse.setEnabled(isEnable);
	}

	// private void tblTonKhoMouseClicked(MouseEvent evt) {
	// int selectedRow = tblWarehouse.getSelectedRow();
	// String category = (String) tblWarehouse.getValueAt(selectedRow, 0);
	// fillToTable(productDAOInit.filterProductByWarehouse(productsInit, category));
	// }

	// private void tblKetXuatMouseClicked(MouseEvent evt) {
	//
	// // get selected product
	// int selectedRow = tblProduct.getSelectedRow();
	// String code = (String) tblProduct.getValueAt(selectedRow, 0);
	//
	// // Tim product by code on click
	// Product p = productDAOInit.find(productsInit, code);
	//
	// System.out.println(p);
	// // Fill product to form
	// filProductToForm(p);
	// }

	// private void clearForm() {
	// txtPrice.setText("");
	// txtExpiryDate.setText("");
	// txtProductId.setText("");
	// txtAmount.setText("");
	// txtProductName.setText("");
	// }

	// private void filProductToForm(Product p) {
	// txtProductId.setText(p.getCode().trim());
	// txtPrice.setText(p.getPrice().trim());
	// txtExpiryDate.setText(p.getExpiryDate().trim());
	// txtAmount.setText(p.getTonKho().trim());
	// txtProductName.setText(p.getName().trim());
	//
	// // TODO set 3 combobox
	// cboWarehouse.setSelectedIndex(Integer.parseInt(p.getKho()) - 1);
	// cboManufacturer.setSelectedIndex(Integer.parseInt(p.getHang()) - 1);
	// cboCategory.setSelectedIndex(Integer.parseInt(p.getPhanloai()) - 1);
	// }

	private void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align) {
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = x;
		gc.gridy = y;
		gc.gridwidth = width;
		gc.gridheight = height;
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.anchor = align;
		gc.fill = GridBagConstraints.HORIZONTAL;
		p.add(c, gc);
	}

	// private void fillToTable(List<Product> list) {
	// DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
	// model.setRowCount(0);
	// for (Product p : list) {
	// Object[] row = new Object[] { p.getCode(), p.getName(), p.getExpiryDate(),
	// p.getTonKho() };
	// model.addRow(row);
	// }
	// }

}
