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
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.cells.Worksheet;

import model.dao.CategoryDAO;
import model.dao.ManufacturerDAO;
import model.dao.ProductDAO;
import model.dao.WarehouseDAO;
import model.entity.Category;
import model.entity.Manufacturer;
import model.entity.Product;
import model.entity.ProductToExport;
import model.entity.Warehouse;
import util.CompareDate;
import util.Convert;

public class View extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String APPLICATION_TITLE = "Quản Lý Kho Hàng";

	private static final String BUTTON_SKIP = "Bỏ Qua";
	private static final String BUTTON_EXPORT = "Kết Xuất";
	private static final String BUTTON_EDIT = "Chỉnh Sửa";
	private static final String BUTTON_ADD = "Thêm";
	private static final String BUTTON_SEARCH = "Tìm Kiếm";
	private static final String BUTTON_DELETE = "Xóa";
	private static final String BUTTON_SAVE = "Lưu";

	private static final String LABEL_PRICE = "Giá";
	private static final String LABEL_EXPIRY_DATE = "Hạn Sử Dụng (Ngày-Tháng-Năm)";
	private static final String LABEL_MANUFACTURER = "Hãng Sản Xuất";
	private static final String LABEL_WAREHOUSE = "Kho";
	private static final String LABEL_PRODUCT_ID = "Mã Sản Phẩm";
	private static final String LABEL_CATEGORY = "Phân Loại";
	private static final String LABEL_AMOUNT = "Tồn Kho";
	private static final String LABEL_PRODUCT_NAME = "Tên Sản Phẩm";
	private static final String LABEL_SEARCH = "Tìm Kiếm";

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
	Status applicationStatus;

	private enum Status {
		ADDING, EDITING, DEFAULT;
	}

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

		// application status
		applicationStatus = Status.DEFAULT;

		// disable form
		toggleForm(false);

		// Start program: default warehouse selected = 1;
		fillProductsToTable(productDAOInit.filterProductByWarehouse(products, selectedWarehouse));

		pack();
	}

	private void leftPanel() {
		pnlLeft = new JPanel();

		String column[] = { "STT.", "Tên Kho" };

		// TODO: tblWarehouse data
		String data[][] = warehouseDAOInit.getWarehouseData();

		tblWarehouse = new JTable(new DefaultTableModel(data, column));
		tblWarehouse.setRowSelectionInterval(0, 0);
		tblWarehouse.setDefaultEditor(Object.class, null);
		tblWarehouse.setRowSelectionAllowed(true);
		tblWarehouse.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
				// change status
				applicationStatus = Status.DEFAULT;

				toggleButton(false);
				toggleForm(false);
			}
		});

		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// change status
				applicationStatus = Status.EDITING;

				// enable form
				toggleForm(true);

				toggleButton(true);

				// disable product id
				txtProductId.setEnabled(false);
			}
		});

		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// get text from ma hang hoa field
				String fProductId = txtProductId.getText();

				if (fProductId.equals("")) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm muốn xóa.");
				} else {
					int productId = Integer.parseInt(fProductId);

					Product productToDelete = productDAOInit.findById(products, productId);

					int confirm = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa?", "Thông báo",
							JOptionPane.YES_NO_OPTION);

					System.out.println(confirm);
					// yes: confirn == 0
					// no: confirm == 1

					if (confirm == 0) {
						boolean isDeleted = productDAOInit.delete(productToDelete);

						if (isDeleted) {
							JOptionPane.showMessageDialog(null, "Đã xóa!", "Notification", 1);

							// reload database
							getProducts();

							// reload table
							fillProductsToTable(products);
						} else {
							JOptionPane.showMessageDialog(null, "Xóa không thành công!", "Notification", 1);
						}
					}

				}
			}
		});

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// change status
				applicationStatus = Status.ADDING;

				// enable form
				toggleForm(true);

				toggleButton(true);

				// clear form
				clearForm();
			}
		});

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (applicationStatus == Status.ADDING) {
					saveProduct();
				} else if (applicationStatus == Status.EDITING) {
					updateProduct();
				}

				applicationStatus = Status.DEFAULT;
				toggleForm(false);
				toggleButton(false);
			}
		});

	}

	private void saveProduct() {
		// get product from form
		Product productToSave = formToProduct();
		System.out.println("In btnSave, productToSave: " + productToSave);

		// validation date
		Date dateToCompare = productToSave.getExpiryDate();
		Date now = new Date();

		if (!CompareDate.commpareDate(Convert.dateToString(dateToCompare), Convert.dateToString(now))) {
			JOptionPane.showMessageDialog(null, "Ngày nhập vào không hợp lệ.", "Thông báo", JOptionPane.ERROR_MESSAGE);
			return;
		}

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

			// reload database
			getProducts();

			// reload table
			fillProductsToTable(products);

			// show dialog
			JOptionPane.showMessageDialog(null, "Thêm thành công!");
		} else {
			JOptionPane.showMessageDialog(null, "Thêm không thành công!");
		}
	}

	private void updateProduct() {
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
			int price = Integer.parseInt(fPrice);

			// TODO convert string to date
			Date expiryDate = Convert.stringToDate(fExpiryDate);
			int amount = Integer.parseInt(fAmount);
			int categoryId = categoryDAOInit.findByName(categories, fCategoryId).getId();
			int manufacturerId = manufacturerDAOInit.findByName(manufacturers, fManufacturerId).getId();
			int warehouseId = warehouseDAOInit.findByName(warehouses, fWarehouseId).getId();

			// find product by id
			Product productToUpdate = productDAOInit.findById(products, productId);

			productToUpdate.setName(productName);
			productToUpdate.setExpiryDate(expiryDate);
			productToUpdate.setPrice(price);
			productToUpdate.setAmount(amount);
			productToUpdate.setCategoryId(categoryId);
			productToUpdate.setManufacturerId(manufacturerId);
			productToUpdate.setWarehouseId(warehouseId);

			// validation date
			Date dateToCompare = productToUpdate.getExpiryDate();
			Date now = new Date();

			if (!CompareDate.commpareDate(Convert.dateToString(dateToCompare), Convert.dateToString(now))) {
				JOptionPane.showMessageDialog(null, "Ngày nhập vào không hợp lệ.", "Thông báo",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			boolean isUpdated = productDAOInit.update(productToUpdate);
			if (isUpdated) {
				JOptionPane.showMessageDialog(null, "Update successfully!", "Notification", 1);
				for (Product product : products) {
					System.out.println(product);
				}
				fillProductsToTable(products);
			} else {
				JOptionPane.showMessageDialog(null, "Update fail!", "Notification", 1);
			}

		}
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

//		cboWarehouse.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent ae) {
//				int selected = cboWarehouse.getSelectedIndex();
//				System.out.println("In warehouse combobox, selected item: " + selected);
//
//				selectedWarehouse = selected + 1;
//				System.out.println("In warehouse combobox, selected warehouse id: " + selectedWarehouse);
//
//				// fill to table
//				fillProductsToTable(productDAOInit.filterProductByWarehouse(products, selectedWarehouse));
//
//				if (rootPaneCheckingEnabled) {
//
//				}
//				
//			}
//		});

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
				String search = txtSearch.getText();
				if (search.equals("")) {
					// TODO: fill all product
					fillProductsToTable(productDAOInit.filterProductByWarehouse(products, selectedWarehouse));
				}

				int id;
				try {
					id = Integer.parseInt(search);
					fillProductsToTable(productDAOInit.find(products, id));
				} catch (Exception e2) {
					fillProductsToTable(productDAOInit.findBy(products, search));
					System.out.println(e2);
				}

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
				exportData();
			}
		});
	}

	private void contentBottomPanel() {
		pnlContent_Bottom = new JPanel();

		String data[][] = { { "", "", "", "" }, { "", "", "", "" }, { "", "", "", "" }, { "", "", "", "" } };
		String column[] = { LABEL_PRODUCT_ID, LABEL_PRODUCT_NAME, LABEL_EXPIRY_DATE, LABEL_AMOUNT };

		tblProduct = new JTable(new DefaultTableModel(data, column));
		tblProduct.setSize(600, 300);
		tblProduct.setDefaultEditor(Object.class, null);
		tblProduct.setRowSelectionAllowed(true);
		tblProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
		String fManufacturerId = cboManufacturer.getSelectedItem().toString();
		String fWarehouseId = cboWarehouse.getSelectedItem().toString();

		int productId = Integer.parseInt(fProductId);
		String productName = fProductName;
		int price = Integer.parseInt(fPrice);

		// TODO convert string to date
		Date expiryDate = Convert.stringToDate(fExpiryDate);
		int amount = Integer.parseInt(fAmount);
		int categoryId = categoryDAOInit.findByName(categories, fCategoryId).getId();
		int manufacturerId = manufacturerDAOInit.findByName(manufacturers, fManufacturerId).getId();
		int warehouseId = warehouseDAOInit.findByName(warehouses, fWarehouseId).getId();

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

	/**
	 * Toggle buttons
	 * 
	 * if true, enable skip and save. Else enable add, delete, edit
	 * 
	 * @param isEnable
	 */
	private void toggleButton(boolean isEnable) {
		btnSkip.setEnabled(isEnable);
		btnSave.setEnabled(isEnable);

		btnAdd.setEnabled(!isEnable);
		btnDelete.setEnabled(!isEnable);
		btnEdit.setEnabled(!isEnable);
	}

	private void toggleForm(boolean isEnable) {
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

	private void fillProductsToTable(List<Product> list) {
		DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
		model.setRowCount(0);
		for (Product p : list) {
			System.out.println(p.getExpiryDate());
			String date = Convert.dateToString(p.getExpiryDate());
			Object[] row = new Object[] { p.getId(), p.getName(), date, p.getAmount() };
			model.addRow(row);
		}

	}

	private void filProductToForm(Product p) {
		// convert date format
		String date = Convert.dateToString(p.getExpiryDate());

		txtProductId.setText(String.valueOf(p.getId()));
		txtProductName.setText(p.getName().trim());
		txtPrice.setText(String.valueOf(p.getPrice()));
		txtExpiryDate.setText(date);
		txtAmount.setText(String.valueOf(p.getAmount()));

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

		List<Product> productsFiltered = productDAOInit.filterProductByWarehouse(products, selectedWarehouse);

		fillProductsToTable(productsFiltered);

		if (productsFiltered.size() > 0) {
			// focus first row
			tblProduct.setRowSelectionInterval(0, 0);
		}
	}

	private void tblProductMouseClicked(MouseEvent evt) {

		int selectedRow = tblProduct.getSelectedRow();
		System.out.println("In product table, selected row: " + selectedRow);

		String tProductId = tblProduct.getValueAt(selectedRow, 0).toString();

		int selectedProductId = Integer.parseInt(tProductId);

		Product p = productDAOInit.findById(products, selectedProductId);
		System.out.println("In product table, product warehouse id: " + p.getWarehouseId());

		filProductToForm(p);
	}

	private List<ProductToExport> productsToExport() {
		List<ProductToExport> productsToExport = new ArrayList<>();

		for (Product p : products) {
			String categoryName = categoryDAOInit.findById(categories, p.getCategoryId()).getName();
			String manufacturerName = manufacturerDAOInit.findById(manufacturers, p.getManufacturerId()).getName();
			String warehouseName = warehouseDAOInit.findById(warehouses, p.getWarehouseId()).getName();

			ProductToExport pToExport = new ProductToExport();
			pToExport.setId(p.getId());
			pToExport.setName(p.getName());
			pToExport.setPrice(p.getPrice());
			pToExport.setExpiryDate(p.getExpiryDate());
			pToExport.setAmount(p.getAmount());
			pToExport.setCategory(categoryName);
			pToExport.setManufacturer(manufacturerName);
			pToExport.setWarehouse(warehouseName);

			productsToExport.add(pToExport);
		}

		return productsToExport;
	}

	private void exportData() {
		// Instantiating a WorkbookDesigner object
		WorkbookDesigner designer = new WorkbookDesigner();

		// Create empty workbook
		Workbook wb = new Workbook();

		String[] excelTitle = new String[] { "Id", "Name", "Price", "Expiry date", "Amount", "Category id",
				"Manuafacturer id", "Warehouse id" };

		// Access first worksheet and add smart marker in cell A1
		Worksheet ws = wb.getWorksheets().get(0);
		ws.getCells().get("A1").putValue("&=$Title(horizontal,noadd)");
		ws.getCells().get("A2").putValue("&=Product.Id");
		ws.getCells().get("B2").putValue("&=Product.Name");
		ws.getCells().get("C2").putValue("&=Product.Price");
		ws.getCells().get("D2").putValue("&=Product.ExpiryDate");
		ws.getCells().get("E2").putValue("&=Product.Amount");
		ws.getCells().get("F2").putValue("&=Product.Category");
		ws.getCells().get("G2").putValue("&=Product.Manufacturer");
		ws.getCells().get("H2").putValue("&=Product.Warehouse");

		designer.setWorkbook(wb);

		// Set the data source for the designer spreadsheet
		designer.setDataSource("Title", excelTitle);
		designer.setDataSource("Product", productsToExport());

		try {
			// Process
			designer.process();

			ws.autoFitColumns();

			// Save the workbook
			wb.save("test.xlsx");

			JOptionPane.showMessageDialog(null, "Export successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Export fail!");
		}
	}
}
