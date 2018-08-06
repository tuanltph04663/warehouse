package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import util.Convert;

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
	private static final String LABEL_EXPIRY_DATE = "Expiry date (DD-MM-YYYY)";
	private static final String LABEL_MANUFACTURER = "Manufacturer";
	private static final String LABEL_WAREHOUSE = "Warehouse";
	private static final String LABEL_PRODUCT_ID = "Product ID";
	private static final String LABEL_CATEGORY = "Category";
	private static final String LABEL_AMOUNT = "Amount";
	private static final String LABEL_PRODUCT_NAME = "Product name";
	private static final String LABEL_SEARCH = "Search";

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
	private JComboBox<Warehouse> cboWarehouse;
	private JComboBox<Category> cboCategory;
	private JComboBox<Manufacturer> cboManufacturer;

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
	private List<Product> products = new ArrayList<>();

	// Application state
	private int selectedWarehouse = 1;

	public View() {
		setLayout(new BorderLayout());
		setTitle(APPLICATION_TITLE);
		setSize(850, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		// call to init data
		getCategories();
		getManufacturers();
		getWarehouses();
		getProducts();

		// add left panel
		leftPanel();
		add(pnlLeft, BorderLayout.WEST);

		// add content panel
		contentPanel();
		add(pnlContent, BorderLayout.CENTER);

		// disable form
		toggleForm(false);

		// Start program: default warehouse selected = 1;
		fillToProductTable(productDAOInit.filterProductByWarehouse(products, selectedWarehouse));

		pack();
	}

	private void leftPanel() {
		pnlLeft = new JPanel();

		String column[] = { "No.", "Warehouse" };

		// TODO: tblWarehouse data
		String data[][] = warehouseDAOInit.getWarehouseData();

		tblWarehouse = new JTable(new DefaultTableModel(data, column));
		tblWarehouse.setRowSelectionInterval(0, 0);

		JScrollPane spTonKho = new JScrollPane(tblWarehouse, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spTonKho.setPreferredSize(new Dimension(250, 600));
		getContentPane().add(splitPane);
		splitPane.setLeftComponent(pnlLeft);
		pnlLeft.add(spTonKho);

		tblWarehouse.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				tblWarehouseMouseClicked(evt);
			}
		});
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
				toggleForm(false);
			}
		});

		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// enable form
				toggleForm(true);

				// get text from ma hang hoa field
				String fProductId = txtProductId.getText();

				if (fProductId.equals("")) {
					JOptionPane.showMessageDialog(null, "Please insert product id.");
				} else {
					// get text from field
					String fProductName = txtProductName.getText();
					String fExpiryDate = txtExpiryDate.getText();
					String fPrice = txtPrice.getText();
					String fAmount = txtAmount.getText();
					String fCategoryId = cboCategory.getSelectedItem().toString();
					String fManufacturerId = cboManufacturer.getSelectedItem().toString();
					String fWarehouseId = cboWarehouse.getSelectedItem().toString();

					int productId = Integer.parseInt(fProductId);
					String productName = fProductName;
					Date expiryDate = Convert.stringToDate(fExpiryDate);
					int price = Integer.parseInt(fPrice);
					int amount = Integer.parseInt(fAmount);
					int categoryId = Integer.parseInt(fCategoryId);
					int manufacturerId = Integer.parseInt(fManufacturerId);
					int warehouseId = Integer.parseInt(fWarehouseId);

					// find product by id
					Product productToUpdate = productDAOInit.findById(products, productId);

					productToUpdate.setName(productName);
					productToUpdate.setExpiryDate(expiryDate);
					productToUpdate.setPrice(price);
					productToUpdate.setAmount(amount);
					productToUpdate.setCategoryId(categoryId);
					productToUpdate.setManufacturerId(manufacturerId);
					productToUpdate.setWarehouseId(warehouseId);

					productDAOInit.update(productToUpdate);

					for (Product product : products) {
						System.out.println(product);
					}
					fillToProductTable(products);
				}

			}
		});

		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// get text from ma hang hoa field
				String fProductId = txtProductId.getText();

				if (fProductId.equals("")) {
					JOptionPane.showMessageDialog(null, "Please insert product id.");
				} else {
					int productId = Integer.parseInt(fProductId);

					Product productToDelete = productDAOInit.findById(products, productId);

					productDAOInit.delete(productToDelete);

					// reload table
					fillToProductTable(products);
				}
			}
		});

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// enable form
				toggleForm(true);

				// clear form
				clearForm();
			}
		});

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// get product from form
				Product productToSave = formToProduct();
				System.out.println("In btnSave, productToSave: " + productToSave);

				// insert product
				boolean isInserted = productDAOInit.insert(productToSave);

				if (isInserted) {
					clearForm();
					List<Product> products = new ArrayList<>();
					try {
						products = productDAOInit.getAll();
					} catch (SQLException ex) {
						Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
					}

					// reload table
					fillToProductTable(products);

					// show dialog
					JOptionPane.showMessageDialog(null, "Insert successfully!");
				} else {
					JOptionPane.showMessageDialog(null, "Insert fail!");
				}
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
				int selected = cboWarehouse.getSelectedIndex();
				System.out.println("In warehouse combobox, selected item: " + selected);

				selectedWarehouse = selected + 1;
				System.out.println("In warehouse combobox, selected warehouse id: " + selectedWarehouse);

				// fill to table
				fillToProductTable(productDAOInit.filterProductByWarehouse(products, selectedWarehouse));
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
		lblSearch = new JLabel(LABEL_SEARCH, JLabel.LEFT);
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

		tblProduct.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				tblProductMouseClicked(evt);
			}
		});
	}

	private void getCategories() {
		try {
			categories = categoryDAOInit.getAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getManufacturers() {
		try {
			manufacturers = manufacturerDAOInit.getAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getWarehouses() {
		try {
			warehouses = warehouseDAOInit.getAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getProducts() {
		try {
			products = productDAOInit.getAll();
		} catch (SQLException ex) {
			Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private Product formToProduct() {
		// get text from field

		String fProductId = txtProductId.getText();
		String fProductName = txtProductName.getText();
		String fPrice = txtPrice.getText();
		String fExpiryDate = txtExpiryDate.getText();
		String fAmount = txtAmount.getText();

		String fCategoryId = cboCategory.getSelectedItem().toString();
		String fManufacturer = cboManufacturer.getSelectedItem().toString();
		String fWarehouseId = cboWarehouse.getSelectedItem().toString();

		System.out.println("fProductId: " + fProductId);
		System.out.println("fProductName: " + fProductName);
		System.out.println("fPrice: " + fPrice);
		System.out.println("fExpiryDate: " + fExpiryDate);
		System.out.println("fAmount: " + fAmount);
		System.out.println("fCategoryId: " + fCategoryId);
		System.out.println("fManufacturer: " + fManufacturer);
		System.out.println("fWarehouseId: " + fWarehouseId);

		int productId = Integer.parseInt(fProductId);
		String productName = fProductName;
		int price = Integer.parseInt(fPrice);

		// TODO convert string to date
		Date expiryDate = Convert.stringToDate(txtExpiryDate.getText());
		int amount = Integer.parseInt(txtAmount.getText());
		int categoryId = categoryDAOInit.findByName(categories, fCategoryId).getId();
		int manufacturerId = manufacturerDAOInit.findByName(manufacturers, fManufacturer).getId();
		int warehouseId = warehouseDAOInit.findByName(warehouses, fWarehouseId).getId();

		System.out.println("productId: " + productId);
		System.out.println("productName: " + productName);
		System.out.println("price: " + price);
		System.out.println("expiryDate: " + expiryDate);
		System.out.println("amount: " + amount);
		System.out.println("categoryId: " + categoryId);
		System.out.println("manufacturer: " + manufacturerId);
		System.out.println("warehouseId: " + warehouseId);

		// TODO validate data

		Product p = new Product();
		p.setId(productId);
		p.setName(productName);
		p.setPrice(price);
		p.setExpiryDate(expiryDate);
		p.setAmount(amount);
		p.setCategoryId(categoryId);
		p.setManufacturerId(manufacturerId);
		p.setWarehouseId(warehouseId);

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

	private void clearForm() {
		txtAmount.setText("");
		txtExpiryDate.setText("");
		txtPrice.setText("");
		txtProductId.setText("");
		txtProductName.setText("");
	}

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

	private void fillToProductTable(List<Product> list) {
		DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
		model.setRowCount(0);
		for (Product p : list) {
			Object[] row = new Object[] { p.getId(), p.getName(), p.getExpiryDate(), p.getAmount() };
			model.addRow(row);
		}

		if (list.size() > 0) {
			tblProduct.setRowSelectionInterval(0, 0);
		}
	}

	private void filProductToForm(Product p) {
		txtProductId.setText(String.valueOf(p.getId()));
		txtProductName.setText(p.getName().trim());
		txtPrice.setText(String.valueOf(p.getPrice()));
		txtExpiryDate.setText(p.getExpiryDate().toString());
		txtAmount.setText(String.valueOf(p.getAmount()));

		System.out.println(p);
		// TODO set 3 combobox
		cboCategory.setSelectedIndex(p.getCategoryId() - 1);
		cboManufacturer.setSelectedIndex(p.getManufacturerId() - 1);
		cboWarehouse.setSelectedIndex(p.getWarehouseId() - 1);
	}

	private void tblWarehouseMouseClicked(MouseEvent evt) {
		int selectedRow = tblWarehouse.getSelectedRow();
		System.out.println("In warehouse table, selected row: " + selectedRow);

		selectedWarehouse = selectedRow + 1;
		System.out.println("In warehouse table, selected warehouse id: " + selectedWarehouse);
		fillToProductTable(productDAOInit.filterProductByWarehouse(products, selectedWarehouse));
	}
	
	private void tblProductMouseClicked(MouseEvent evt) {
		int selectedRow = tblProduct.getSelectedRow();
		System.out.println("In product table, selected row: " + selectedRow);
		
		int productId = selectedRow + 1;
		System.out.println("In product table, selected product id: " + productId);

		Product p = productDAOInit.findById(products, productId);

		filProductToForm(p);
	}
}
