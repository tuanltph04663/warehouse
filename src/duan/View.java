/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author THANGTONY
 */
public class View extends JFrame {

    private JPanel pnlLeft, pnlContent, pnlContent_Top, pnlContent_Center, pnlContent_Bot, pnlContent_Center_Center,
			pnlContent_Center_Bot, pnlSearch, pnlExport;
	private JTextField txtMaHangHoa, txtTenHangHoa, txtHanSuDung, txtGiaNhap, txtSoLuongTonKho, txtTimKiem;
	private JComboBox cboKho, cboPhanLoai, cboHangsanxuat;
	private JLabel lblKho, lblMaHangHoa, lblPhanLoai, lblTenHangHoa, lblHangSanXuat, lblHanSuDung, lblGiaNhap,
			lblSoLuongTonKho, lblTimKiem;
	private JButton btnThem, btnSua, btnXoa, btnLuu, btnBoQua, btnTim, btnKetXuat;
	private JTable tblTonKho, tblKetXuat;
	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	public View() {
		// TODO Auto-generated constructor stub
		setTitle("Du An 1");
		setSize(850, 600);
		setLocationRelativeTo(null);// can giua
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		 setResizable(false);//k keo duoc
		setLayout(new BorderLayout());
		
		left();
		content();
		pack();
	}

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
		
		String[] dataKho = { "Kho Vật Tư", "Kho Nguyên Liệu", "Kho Văn Phòng Phẩm" };
		String[] dataHangsanxuat = { "Hãng 1", "Hãng 2", "Hãng 3" };
		String[] dataPhanloai = { "Loại 1", "Loại 2", "Loại 3" };

		cboKho = new JComboBox(dataKho);
		cboHangsanxuat = new JComboBox(dataHangsanxuat);
		cboPhanLoai = new JComboBox(dataPhanloai);
		
		//end compoinent
		
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
	
		String data2[][] = { { "", "", "", "" }, { "", "", "", "" }, { "", "", "", "" }, { "", "", "", "" } };

		String column2[] = { "Mã Hàng Hóa", "Tên Hàng Hóa", "Hạn Sử Dụng", "Số Lượng Tồn Kho" };
		tblKetXuat = new JTable(new DefaultTableModel(data2, column2));
		tblKetXuat.setSize(600, 300);
		JScrollPane spKetXuat = new JScrollPane(tblKetXuat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		int colunm = tblKetXuat.getColumnCount();
		int rows = tblKetXuat.getRowCount();
		DefaultTableModel model = (DefaultTableModel) tblKetXuat.getModel();
		model = (DefaultTableModel) tblKetXuat.getModel();
		spKetXuat.setPreferredSize(new Dimension(600, 300));
		pnlContent_Bot.add(spKetXuat);
		
		getContentPane().add(splitPane);
		splitPane.setRightComponent(pnlContent);
		
		
		pnlContent.add(pnlContent_Center, BorderLayout.CENTER);
		pnlContent.add(pnlContent_Bot, BorderLayout.SOUTH);
	}

	private void left() {
		pnlLeft = new JPanel();
		String column[] = { "STT", "Tồn Kho" };
		String data[][] = { { "1", "Kho vật tư" }, { "2", "Kho nguyên liệu" }, { "3", "Kho vật phẩm" } };
		tblTonKho = new JTable(new DefaultTableModel(data, column));
		int colunm = tblTonKho.getColumnCount();
		int rows = tblTonKho.getRowCount();
		DefaultTableModel model = (DefaultTableModel) tblTonKho.getModel();

		JScrollPane spTonKho = new JScrollPane(tblTonKho, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spTonKho.setPreferredSize(new Dimension(250, 600));
		getContentPane().add(splitPane);
		splitPane.setLeftComponent(pnlLeft);
		pnlLeft.add(spTonKho);
		add(pnlLeft, BorderLayout.WEST);
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
}
