/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author THANGTONY
 */
public class View extends JFrame {

    // JPanel
    private JPanel pnlLeft;
    private JPanel pnlContent;
    private JPanel pnlContent_Top;
    private JPanel pnlContent_Center;
    private JPanel pnlContent_Bot;
    private JPanel pnlContent_Center_Center;
    private JPanel pnlContent_Center_Bot;
    private JPanel pnlSearch;
    private JPanel pnlExport;

    // JTextField
    private JTextField txtMaHangHoa;
    private JTextField txtTenHangHoa;
    private JTextField txtHanSuDung;
    private JTextField txtGiaNhap;
    private JTextField txtSoLuongTonKho;
    private JTextField txtTimKiem;

    // JComboBox
    private JComboBox cboKho;
    private JComboBox cboPhanLoai;
    private JComboBox cboHangsanxuat;

    // JLabel
    private JLabel lblKho;
    private JLabel lblMaHangHoa;
    private JLabel lblPhanLoai;
    private JLabel lblTenHangHoa;
    private JLabel lblHangSanXuat;
    private JLabel lblHanSuDung;
    private JLabel lblGiaNhap;
    private JLabel lblSoLuongTonKho;
    private JLabel lblTimKiem;

    // JButton
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLuu;
    private JButton btnBoQua;
    private JButton btnTim;
    private JButton btnKetXuat;

    // JTable
    private JTable tblTonKho;
    private JTable tblKetXuat;

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    // DATA initial
    private ProductDAO productDAOInit = new ProductDAO();
    private WarehouseDAO warehouseDAOInit = new WarehouseDAO();
    private ManufacturerDAO manufacturerDAOInit = new ManufacturerDAO();
    private CategoryDAO categoryDAOInit = new CategoryDAO();

    private List<Category> categories = new ArrayList<>();
    private List<Manufacturer> manufacturers = new ArrayList<>();
    private List<Warehouse> warehouses = new ArrayList<>();
    private List<Product> productsInit = productDAOInit.getList();
    
    // Application state
    private int selectedWarehouse = 1;
    private int selectedCategory = 1;
    private int selectedManufacturer = 1;

    public View() {

        // Call to get init data
        getWarehouse();
        getProducts();

        // TODO Auto-generated constructor stub
        setTitle("Quản lý kho hàng");
        setSize(850, 600);
        setLocationRelativeTo(null);// can giua
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);// k keo duoc
        setLayout(new BorderLayout());

        left();
        content();

        // disable form  
        toggleForm(false);
        
        // Start program: default warehouse selected = 1;
        fillToTable(productDAOInit.filterProductByWarehouse(productsInit, String.valueOf(selectedWarehouse)));
        pack();
    }

    private void getProducts() {
        try {
            productsInit = productDAOInit.getAll();
        } catch (SQLException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Content.
     */
    private void content() {

        pnlContent = new JPanel();
        add(pnlContent, BorderLayout.CENTER);
        pnlContent.setLayout(new BorderLayout());

        // pnlContent_Top
        pnlContent_Top = new JPanel();
        pnlContent_Top.setLayout(new FlowLayout(FlowLayout.LEFT));
        btnBoQua = new JButton("Bỏ qua");
        btnKetXuat = new JButton("Kết xuất");
        btnSua = new JButton("Sửa");
        btnThem = new JButton("Thêm");
        btnTim = new JButton("Tìm");
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu");
        pnlContent_Top.add(btnThem);
        pnlContent_Top.add(btnSua);
        pnlContent_Top.add(btnXoa);
        pnlContent_Top.add(btnLuu);
        pnlContent_Top.add(btnBoQua);
        pnlContent.add(pnlContent_Top, BorderLayout.NORTH);

        // pnlContent_Center
        pnlContent_Center = new JPanel();
        pnlContent_Center.setLayout(new BorderLayout());

        pnlContent_Center_Center = new JPanel(new GridBagLayout());
        pnlContent_Center.add(pnlContent_Center_Center, BorderLayout.CENTER);

        // compoinent Center
        txtGiaNhap = new JTextField(7);
        txtHanSuDung = new JTextField(7);
        txtMaHangHoa = new JTextField(7);
        txtSoLuongTonKho = new JTextField(7);
        txtTenHangHoa = new JTextField(30);
        txtTimKiem = new JTextField(10);

        lblGiaNhap = new JLabel("Giá nhập", JLabel.LEFT);
        lblHanSuDung = new JLabel("Hạn sử dụng", JLabel.LEFT);
        lblHangSanXuat = new JLabel("Hãng sản xuất", JLabel.LEFT);
        lblKho = new JLabel("Kho");
        lblMaHangHoa = new JLabel("Mã Hàng Hóa", JLabel.LEFT);
        lblPhanLoai = new JLabel("Phân loại", JLabel.RIGHT);
        lblSoLuongTonKho = new JLabel("Số lượng tồn kho", JLabel.LEFT);
        lblTenHangHoa = new JLabel("Tên Hàng Hóa", JLabel.LEFT);
        lblTimKiem = new JLabel("Tìm kiếm", JLabel.LEFT);

        String[] dataKho = getWarehouseName();
        String[] dataHangsanxuat = getManufacturer();
        String[] dataPhanloai = getCategories();

        cboKho = new JComboBox(dataKho);
        cboHangsanxuat = new JComboBox(dataHangsanxuat);
        cboPhanLoai = new JComboBox(dataPhanloai);

        // end compoinent
        addItem(pnlContent_Center_Center, lblKho, 0, 0, 1, 1, GridBagConstraints.WEST);
        addItem(pnlContent_Center_Center, cboKho, 1, 0, 5, 1, GridBagConstraints.WEST);
        addItem(pnlContent_Center_Center, lblMaHangHoa, 0, 1, 1, 1, GridBagConstraints.WEST);
        addItem(pnlContent_Center_Center, txtMaHangHoa, 1, 1, 1, 1, GridBagConstraints.WEST);
        addItem(pnlContent_Center_Center, lblPhanLoai, 2, 1, 1, 1, GridBagConstraints.WEST);
        addItem(pnlContent_Center_Center, cboPhanLoai, 3, 1, 3, 1, GridBagConstraints.WEST);
        addItem(pnlContent_Center_Center, lblTenHangHoa, 0, 2, 1, 1, GridBagConstraints.WEST);
        addItem(pnlContent_Center_Center, txtTenHangHoa, 1, 2, 5, 1, GridBagConstraints.EAST);
        addItem(pnlContent_Center_Center, lblHangSanXuat, 0, 3, 1, 1, GridBagConstraints.WEST);
        addItem(pnlContent_Center_Center, cboHangsanxuat, 1, 3, 5, 1, GridBagConstraints.WEST);
        addItem(pnlContent_Center_Center, lblHanSuDung, 0, 4, 1, 1, GridBagConstraints.WEST);
        addItem(pnlContent_Center_Center, txtHanSuDung, 1, 4, 1, 1, GridBagConstraints.WEST);
        addItem(pnlContent_Center_Center, lblGiaNhap, 2, 4, 1, 1, GridBagConstraints.WEST);
        addItem(pnlContent_Center_Center, txtGiaNhap, 3, 4, 1, 1, GridBagConstraints.WEST);
        addItem(pnlContent_Center_Center, lblSoLuongTonKho, 4, 4, 1, 1, GridBagConstraints.WEST);
        addItem(pnlContent_Center_Center, txtSoLuongTonKho, 5, 4, 1, 1, GridBagConstraints.EAST);

        pnlContent_Center_Bot = new JPanel(new BorderLayout());
        pnlContent_Center.add(pnlContent_Center_Bot, BorderLayout.SOUTH);

        pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlExport = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlContent_Center_Bot.add(pnlSearch, BorderLayout.WEST);
        pnlContent_Center_Bot.add(pnlExport, BorderLayout.CENTER);

        pnlSearch.add(lblTimKiem);
        pnlSearch.add(txtTimKiem);
        pnlSearch.add(btnTim);
        // pnlExport.add(btnKetXuat);
        pnlExport.add(btnKetXuat);

        pnlContent_Bot = new JPanel();

        String data2[][] = {{"", "", "", ""}, {"", "", "", ""}, {"", "", "", ""}, {"", "", "", ""}};

        String column2[] = {"Mã Hàng Hóa", "Tên Hàng Hóa", "Hạn Sử Dụng", "Số Lượng Tồn Kho"};
        tblKetXuat = new JTable(new DefaultTableModel(data2, column2));
        tblKetXuat.setSize(600, 300);
        JScrollPane spKetXuat = new JScrollPane(tblKetXuat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        spKetXuat.setPreferredSize(new Dimension(600, 300));
        pnlContent_Bot.add(spKetXuat);

        getContentPane().add(splitPane);
        splitPane.setRightComponent(pnlContent);

        pnlContent.add(pnlContent_Center, BorderLayout.CENTER);
        pnlContent.add(pnlContent_Bot, BorderLayout.SOUTH);

        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO btnThem
                
                // enable form
                toggleForm(true);

                // clear form
                clearForm();
                
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO btnSua
                
                // enable form
                toggleForm(true);

                // get text from ma hang hoa field
                String maHangHoa = txtMaHangHoa.getText();

                if (maHangHoa.equals("")) {
                    JOptionPane.showMessageDialog(null, "Vui long nhap ma hang hoa");
                } else {
                    // tim kiem hang hoa theo ma lay duoc trong list
                    Product pFound = productDAOInit.find(maHangHoa);

                    // get text from field
                    String kho = cboKho.getSelectedItem().toString();
                    String phanLoai = cboPhanLoai.getSelectedItem().toString();
                    String ten = txtTenHangHoa.getText();
                    String hang = cboHangsanxuat.getSelectedItem().toString();
                    String han = txtHanSuDung.getText();
                    String gia = txtGiaNhap.getText();
                    String soLuong = txtSoLuongTonKho.getText();

//                    pFound.setCode(gia);
                    pFound.setKho(kho);
                    pFound.setPhanloai(phanLoai);
                    pFound.setName(ten);
                    pFound.setHang(hang);
                    pFound.setExpiryDate(han);
                    pFound.setPrice(gia);
                    pFound.setTonKho(soLuong);
                    productDAOInit.update(pFound);
                    for (Product product : productsInit) {
                        System.out.println(product);
                    }
                    fillToTable(productsInit);
                }

            }
        });

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO btnXoa
                System.out.println("btnXoa");

                // get text from ma hang hoa field
                String maHangHoa = txtMaHangHoa.getText();

                if (maHangHoa.equals("")) {
                    JOptionPane.showMessageDialog(null, "Vui long nhap ma hang hoa");
                } else {
                    // tim kiem hang hoa theo ma lay duoc trong list
                    Product pFound = productDAOInit.find(maHangHoa);

                    productDAOInit.remove(pFound);

                    // reload table
                    fillToTable(productsInit);
                }
            }
        });

        btnTim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO btnTim
                String search = txtTimKiem.getText();
                if (search.equals("")) {
                    
                }
                System.out.println(search);
                fillToTable(productDAOInit.findBy(productsInit, search));
            }
        });

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO btnLuu

                // get product from form
                Product productToSave = formToProduct();
                
                // change product
                Warehouse w = warehouseDAOInit.find(warehouses, productToSave.getKho());
                Manufacturer m = manufacturerDAOInit.find(manufacturers, productToSave.getHang());
                Category c = categoryDAOInit.find(categories, productToSave.getPhanloai());

                // set product
                productToSave.setKho(String.valueOf(w.getId()));
                productToSave.setHang(String.valueOf(m.getId()));
                productToSave.setPhanloai(String.valueOf(c.getId()));

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
                    fillToTable(products);

                    // show dialog
                    JOptionPane.showMessageDialog(null, "Insert successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Insert fail!");
                }
            }
        });

        btnKetXuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO btnKetXuat
                System.out.println("btnKetXuat");
            }
        });

        btnBoQua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO btnBoQua
                cboKho.setEnabled(false);
                cboHangsanxuat.setEnabled(false);
                cboPhanLoai.setEnabled(false);
                txtGiaNhap.setEnabled(false);
                txtHanSuDung.setEnabled(false);
                txtMaHangHoa.setEnabled(false);
                txtSoLuongTonKho.setEnabled(false);
                txtTenHangHoa.setEnabled(false);
                txtTimKiem.setEnabled(false);

            }
        });

        tblKetXuat.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tblKetXuatMouseClicked(evt);
            }
        });
        
        cboKho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int selectedW = cboKho.getSelectedIndex() + 1;
                
                // fill to table 
                fillToTable(productDAOInit.filterProductByWarehouse(productsInit, String.valueOf(selectedW)));
            }
        });
    }

    /**
     * get data from screen, convert to product object
     * @return 
     */
    private Product formToProduct(){
        // get text from field
        String maHangHoa = txtMaHangHoa.getText();
        String kho = cboKho.getSelectedItem().toString();
        String phanLoai = cboPhanLoai.getSelectedItem().toString();
        String ten = txtTenHangHoa.getText();
        String hang = cboHangsanxuat.getSelectedItem().toString();
        String han = txtHanSuDung.getText();
        String gia = txtGiaNhap.getText();
        String soLuong = txtSoLuongTonKho.getText();
        
        //TODO validate data
        
        Product p = new Product();
        p.setCode(maHangHoa);
        p.setKho(kho);
        p.setPhanloai(phanLoai);
        p.setName(ten);
        p.setHang(hang);
        p.setExpiryDate(han);
        p.setPrice(gia);
        p.setTonKho(soLuong);
        
        return p;
    }
    
    private void left() {
        pnlLeft = new JPanel();
        String column[] = {"STT", "Tên Kho"};

        // getWarehouseData
        String data[][] = getWarehouseData();
        tblTonKho = new JTable(new DefaultTableModel(data, column));

        JScrollPane spTonKho = new JScrollPane(tblTonKho, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        spTonKho.setPreferredSize(new Dimension(250, 600));
        getContentPane().add(splitPane);
        splitPane.setLeftComponent(pnlLeft);
        pnlLeft.add(spTonKho);
        add(pnlLeft, BorderLayout.WEST);

        tblTonKho.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tblTonKhoMouseClicked(evt);
            }
        });

    }

    
    private void toggleForm(boolean isEnable){
        btnLuu.setEnabled(isEnable);
        btnBoQua.setEnabled(isEnable);
        btnLuu.setEnabled(isEnable);
        txtGiaNhap.setEnabled(isEnable);
        txtHanSuDung.setEnabled(isEnable);
        txtMaHangHoa.setEnabled(isEnable);
        txtSoLuongTonKho.setEnabled(isEnable);
        txtTenHangHoa.setEnabled(isEnable);
        cboHangsanxuat.setEnabled(isEnable);
        cboPhanLoai.setEnabled(isEnable);
        cboKho.setEnabled(isEnable);
                
    }
    
    private void tblTonKhoMouseClicked(MouseEvent evt) {
        int selectedRow = tblTonKho.getSelectedRow();
        String category = (String) tblTonKho.getValueAt(selectedRow, 0);
        fillToTable(productDAOInit.filterProductByWarehouse(productsInit, category));
    }

    private void tblKetXuatMouseClicked(MouseEvent evt) {
        
        // get selected product
        int selectedRow = tblKetXuat.getSelectedRow();
        String code = (String) tblKetXuat.getValueAt(selectedRow, 0);

        // Tim product by code on click
        Product p = productDAOInit.find(productsInit, code);

        System.out.println(p);
        // Fill product to form
        filProductToForm(p);
    }

    private void clearForm() {
        txtGiaNhap.setText("");
        txtHanSuDung.setText("");
        txtMaHangHoa.setText("");
        txtSoLuongTonKho.setText("");
        txtTenHangHoa.setText("");
    }
    
    private void filProductToForm(Product p){
        txtMaHangHoa.setText(p.getCode().trim());
        txtGiaNhap.setText(p.getPrice().trim());
        txtHanSuDung.setText(p.getExpiryDate().trim());
        txtSoLuongTonKho.setText(p.getTonKho().trim());
        txtTenHangHoa.setText(p.getName().trim());
        
        //TODO set 3 combobox
        cboKho.setSelectedIndex(Integer.parseInt(p.getKho()) - 1);
        cboHangsanxuat.setSelectedIndex(Integer.parseInt(p.getHang()) - 1);
        cboPhanLoai.setSelectedIndex(Integer.parseInt(p.getPhanloai())- 1);
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

    private void fillToTable(List<Product> list) {
        DefaultTableModel model = (DefaultTableModel) tblKetXuat.getModel();
        model.setRowCount(0);
        for (Product p : list) {
            Object[] row = new Object[]{p.getCode(), p.getName(), p.getExpiryDate(), p.getTonKho()};
            model.addRow(row);
        }
    }

    private void getWarehouse() {
        System.out.println("getWarehouse");
        try {
            warehouses = warehouseDAOInit.getAll();
        } catch (SQLException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String[] getWarehouseName() {
        System.out.println("getWarehouseName");
        String[] data = new String[warehouses.size()];
        int i = 0;
        for (Warehouse w : warehouses) {
            data[i] = w.getName();
            i++;
        }

        return data;
    }

    private String[][] getWarehouseData() {
        System.out.println("getWarehouseData");
        String[][] data = new String[warehouses.size()][2];
        int i = 0;
        for (Warehouse w : warehouses) {
            data[i][0] = Integer.toString(w.getId());
            data[i][1] = w.getName();
            i++;
        }

        return data;
    }

    private String[] getManufacturer() {
        try {
            manufacturers = manufacturerDAOInit.getAll();
        } catch (SQLException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }

        String[] datas = new String[manufacturers.size()];
        int i = 0;
        for (Manufacturer m : manufacturers) {
            datas[i] = m.getName();
            i++;
        }
        return datas;
    }

    private String[] getCategories() {
        try {
            categories = categoryDAOInit.getAll();
        } catch (SQLException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }

        String[] data = new String[categories.size()];
        int i = 0;
        for (Category c : categories) {
            data[i] = c.getName();
            i++;
        }

        return data;
    }
}
