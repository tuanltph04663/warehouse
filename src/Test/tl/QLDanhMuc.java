/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test.tl;

/**
 *
 * @author Admin
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.table.*;

public class QLDanhMuc extends JFrame {

    private JPanel panelall, panelNorth, panelWest, panelWest1, panelWest11, panelCenter, panelCenter1, panelCenter2, panelCenter3, panel1, panel2;
    private JLabel lblTieude, lblToppicID, lblToppicName, lblPublishingID, lblPublishingName, lblSearch, lblDanhMuc;
    private JTextField tfToppicID, tfToppicName, tfPublishingID, tfPublishingName, tfSearch;
    private JButton btnAdd, btnEdit, btnDelete, btnSave, btnIgnore, btnSearch, btnRender, btnToppic, btnPublishing;
    private Connection con;
    private final DefaultTableModel model1;
    private final JTable tblTable;
    private final JScrollPane scrollPane1;
    private int index;
    private int dk = 0, dk2 = 0;

    public QLDanhMuc() {

        panelall = new JPanel(new BorderLayout(20, 20));
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc;

        // North
        panelNorth = new JPanel();
        lblTieude = new JLabel("QUẢN LÝ DANH MỤC");
        lblTieude.setForeground(Color.blue);
        lblTieude.setFont(new Font("Tahoma", 1, 16));
        panelNorth.add(lblTieude);

        panelall.add(panelNorth, BorderLayout.NORTH);

        // West
        panelWest = new JPanel(new BorderLayout());
        panelWest.setBorder(BorderFactory.createEtchedBorder());
        lblDanhMuc = new JLabel("Danh mục");
        lblDanhMuc.setHorizontalAlignment(SwingConstants.CENTER);
        lblDanhMuc.setFont(new java.awt.Font("Tahoma", 1, 14));
        lblDanhMuc.setPreferredSize(new Dimension(0, 35));
        lblDanhMuc.setBorder(BorderFactory.createEtchedBorder());
        btnPublishing = new JButton("Nhà xuất bản");
        btnToppic = new JButton("Chủ đề");
        panelWest1 = new JPanel();
        panelWest11 = new JPanel();
        panelWest1.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 10;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelWest1.add(btnToppic, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipady = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelWest1.add(btnPublishing, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipady = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelWest1.add(panelWest11, gbc);

        panelWest1.setPreferredSize(new Dimension(120, 100));
        panelWest11.setPreferredSize(new Dimension(120, 330));
        panelWest.add(lblDanhMuc, BorderLayout.NORTH);
        panelWest.add(panelWest1, BorderLayout.CENTER);
        panelall.add(panelWest, BorderLayout.WEST);

        // Center
        panelCenter = new JPanel(new BorderLayout());
        panelCenter1 = new JPanel(gridBagLayout);

        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnSave = new JButton("Lưu");
        btnIgnore = new JButton("Bỏ qua");
        btnSearch = new JButton("Tìm");
        btnRender = new JButton("Kết xuất");
        btnAdd.setFocusPainted(false);
        btnEdit.setFocusPainted(false);
        btnDelete.setFocusPainted(false);
        btnSave.setFocusPainted(false);
        btnIgnore.setFocusPainted(false);
        btnSearch.setFocusPainted(false);
        btnRender.setFocusPainted(false);

        lblToppicID = new JLabel("Mã chủ đề");
        lblToppicName = new JLabel("Tên chủ đề");
        lblPublishingID = new JLabel("Mã nhà xuất bản");
        lblPublishingName = new JLabel("Tên nhà xuất bản");

        lblSearch = new JLabel("Tìm kiếm");

        tfToppicID = new JTextField();
        tfToppicName = new JTextField();
        tfPublishingID = new JTextField();
        tfPublishingName = new JTextField();
        tfSearch = new JTextField();
        btnIgnore.setEnabled(false);
        btnSave.setEnabled(false);

        panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel1.add(btnAdd);
        panel1.add(btnEdit);
        panel1.add(btnDelete);
        panel1.add(btnSave);
        panel1.add(btnIgnore);

        panelCenter3 = new JPanel(new BorderLayout());
        panelCenter3.add(panel1, BorderLayout.NORTH);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.anchor = GridBagConstraints.LINE_START;
        panelCenter1.add(lblToppicID, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipady = 6;
        gbc.weightx = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCenter1.add(tfToppicID, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelCenter1.add(lblToppicName, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.ipady = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCenter1.add(tfToppicName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.ipadx = 20;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelCenter1.add(lblPublishingID, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.ipady = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCenter1.add(tfPublishingID, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelCenter1.add(lblPublishingName, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.ipady = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCenter1.add(tfPublishingName, gbc);

        panel2 = new JPanel(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 5, 0, 0);
        panel2.add(lblSearch, gbc);

        gbc.gridx = 1;
        gbc.weightx = 2;
        gbc.ipady = 6;
        gbc.weightx = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(tfSearch, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.ipady = 0;
        panel2.add(btnSearch, gbc);

        gbc.gridx = 3;
        gbc.weightx = 4;
        panel2.add(new JLabel(), gbc);

        gbc.gridx = 4;
        gbc.weightx = 0;
        panel2.add(btnRender, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 25, 0, 0);
        panelCenter3.add(panelCenter1, BorderLayout.CENTER);

        panelCenter2 = new JPanel();
        panelCenter2.setLayout(new BorderLayout(10, 20));

        String[] headers1 = {"Mã chủ đề", "Tên chủ đề"};
        String[][] data1 = {};
        model1 = new DefaultTableModel(data1, headers1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblTable = new JTable(model1);
        tblTable.getColumnModel().getColumn(1).setMinWidth(450);
        scrollPane1 = new JScrollPane(tblTable);
        scrollPane1.setMinimumSize(new Dimension(200, 350));

        panelCenter2.add(scrollPane1, BorderLayout.CENTER);
        panelCenter2.add(panel2, BorderLayout.NORTH);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(15, 25, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;

        panelCenter.add(panelCenter3, BorderLayout.NORTH);
        panelCenter.add(panelCenter2, BorderLayout.CENTER);
        panelall.add(panelCenter, BorderLayout.CENTER);

        JLabel lbl1 = new JLabel();
        JLabel lbl2 = new JLabel();
        JLabel lbl3 = new JLabel();
        JLabel lbl4 = new JLabel();
        lbl1.setPreferredSize(new Dimension(20, 20));
        lbl2.setPreferredSize(new Dimension(20, 20));
        lbl3.setPreferredSize(new Dimension(20, 20));
        lbl4.setPreferredSize(new Dimension(20, 20));
        this.add(lbl1, BorderLayout.NORTH);
        this.add(lbl2, BorderLayout.WEST);
        this.add(lbl3, BorderLayout.SOUTH);
        this.add(lbl4, BorderLayout.EAST);
        this.add(panelall, BorderLayout.CENTER);

        startState();

        btnToppic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnToppic.setBackground(Color.ORANGE);
                btnPublishing.setBackground(Color.WHITE);
                tfPublishingID.setText("");
                tfPublishingName.setText("");
                dk = 1;
                loadDatatoTable(" TOPPIC");
                if (tblTable.getRowCount() > 0) {
                    showDetail(Integer.parseInt(tblTable.getValueAt(0, 0).toString()));
                    tblTable.setRowSelectionInterval(0, 0);
                    btnAdd.setEnabled(true);
                    btnDelete.setEnabled(true);
                    btnEdit.setEnabled(true);
                    tfSearch.setEditable(true);
                    tfPublishingName.setEditable(false);
                    tfToppicName.setEditable(false);
                    btnSave.setEnabled(false);
                    btnIgnore.setEnabled(false);
                } else {
                    btnDelete.setEnabled(false);
                    btnEdit.setEnabled(false);
                    tfSearch.setEditable(false);
                }
            }
        });

        btnPublishing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnToppic.setBackground(Color.WHITE);
                btnPublishing.setBackground(Color.ORANGE);
                tfToppicID.setText("");
                tfToppicName.setText("");
                dk = 2;
                loadDatatoTable(" PUBLISHING_HOUSE");
                if (tblTable.getRowCount() > 0) {
                    showDetail(Integer.parseInt(tblTable.getValueAt(0, 0).toString()));
                    tblTable.setRowSelectionInterval(0, 0);
                    btnAdd.setEnabled(true);
                    btnDelete.setEnabled(true);
                    btnEdit.setEnabled(true);
                    tfSearch.setEditable(true);
                    tfPublishingName.setEditable(false);
                    tfToppicName.setEditable(false);
                    btnSave.setEnabled(false);
                    btnIgnore.setEnabled(false);
                } else {
                    btnDelete.setEnabled(false);
                    btnEdit.setEnabled(false);
                    tfSearch.setEditable(false);
                }
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ClearForm();
                btnSave.setEnabled(true);
                btnEdit.setEnabled(false);
                btnAdd.setEnabled(false);
                btnIgnore.setEnabled(true);
                btnDelete.setEnabled(false);
                btnSearch.setEnabled(false);
                btnRender.setEnabled(false);
                tblTable.clearSelection();
                tfSearch.setText("");
                if (dk == 1) {
                    tfToppicName.setEditable(true);
                    tfPublishingName.setEditable(false);
                } else {
                    tfPublishingName.setEditable(true);
                    tfToppicName.setEditable(false);
                }
                dk2 = 1;
            }
        });

        btnIgnore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                startState();
            }
        });

        tblTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                index = tblTable.getSelectedRow();
                showDetail(Integer.parseInt(tblTable.getValueAt(index, 0).toString()));
                btnSave.setEnabled(false);
                btnEdit.setEnabled(true);
                btnAdd.setEnabled(true);
                btnIgnore.setEnabled(false);
                btnDelete.setEnabled(true);
                tfSearch.setEditable(true);
                tfPublishingID.setEditable(false);
                tfPublishingName.setEditable(false);
                tfToppicID.setEditable(false);
                tfToppicName.setEditable(false);
                btnSearch.setEnabled(false);
                btnRender.setEnabled(true);
                tfSearch.setText("");
            }
        });

        tfSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (tfSearch.getText().length() < 1) {
                    btnSearch.setEnabled(false);
                } else {
                    btnSearch.setEnabled(true);
                }
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int kq = fildTable();
                if (kq == -1) {
                    JOptionPane.showMessageDialog(null, "Không có sách cần tìm!");
                } else {
                    tblTable.setRowSelectionInterval(kq, kq);
                    showDetail(Integer.parseInt(tblTable.getValueAt(tblTable.getSelectedRow(), 0).toString()));
                }
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnSave.setEnabled(true);
                btnEdit.setEnabled(false);
                btnAdd.setEnabled(false);
                btnIgnore.setEnabled(true);
                btnDelete.setEnabled(false);
                tfSearch.setEditable(true);
                btnSearch.setEnabled(false);
                btnRender.setEnabled(true);
                tfSearch.setText("");
                dk2 = 2;
                if (dk == 1) {
                    tfToppicName.setEditable(true);
                    tfPublishingName.setEditable(false);
                } else {
                    tfToppicName.setEditable(false);
                    tfPublishingName.setEditable(true);
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int n = tblTable.getSelectedRow();
                if (dk == 1) {
                    if (n >= 0) {
                        int choice = JOptionPane.showConfirmDialog(null, "Do you want to remove toppic: "
                                + tfToppicName.getText() + "? You will lose all data related to this topic !!!",
                                "Confirm?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (choice == JOptionPane.YES_OPTION) {
                            if (delete(" BOOK", " TOPPICID", Integer.parseInt(tfToppicID.getText()))) {
                                if (delete(" TOPPIC", " TOPPICID", Integer.parseInt(tfToppicID.getText()))) {
                                    loadDatatoTable(" TOPPIC");
                                    tfSearch.setText("");
                                    if (tblTable.getRowCount() == 0) {
                                        ClearForm();
                                        btnRender.setEnabled(false);
                                        btnDelete.setEnabled(false);
                                        tfSearch.setEditable(false);
                                        btnEdit.setEnabled(false);
                                    } else if (n == 0) {
                                        tblTable.setRowSelectionInterval(0, 0);
                                        showDetail(Integer.parseInt(tblTable.getValueAt(tblTable.getSelectedRow(), 0).toString()));
                                    } else if (n == tblTable.getRowCount()) {
                                        tblTable.setRowSelectionInterval(n - 1, n - 1);
                                        showDetail(Integer.parseInt(tblTable.getValueAt(tblTable.getSelectedRow(), 0).toString()));
                                    } else {
                                        tblTable.setRowSelectionInterval(n, n);
                                        showDetail(Integer.parseInt(tblTable.getValueAt(tblTable.getSelectedRow(), 0).toString()));
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Error!!!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Error!!!");
                            }
                        }
                    }
                } else {
                    if (n >= 0) {
                        int choice = JOptionPane.showConfirmDialog(null, "Do you want to remove publising house: "
                                + tfPublishingName.getText() + "? You will lose all data related to this publising house !!!",
                                "Confirm?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (choice == JOptionPane.YES_OPTION) {
                            if (delete(" BOOK", " PHID", Integer.parseInt(tfPublishingID.getText()))) {
                                if (delete(" PUBLISHING_HOUSE", " PHID", Integer.parseInt(tfPublishingID.getText()))) {
                                    loadDatatoTable(" PUBLISHING_HOUSE");
                                    tfSearch.setText("");
                                    if (tblTable.getRowCount() == 0) {
                                        ClearForm();
                                        btnRender.setEnabled(false);
                                        btnDelete.setEnabled(false);
                                        tfSearch.setEditable(false);
                                        btnEdit.setEnabled(false);
                                    } else if (n == 0) {
                                        tblTable.setRowSelectionInterval(0, 0);
                                        showDetail(Integer.parseInt(tblTable.getValueAt(tblTable.getSelectedRow(), 0).toString()));
                                    } else if (n == tblTable.getRowCount()) {
                                        tblTable.setRowSelectionInterval(n - 1, n - 1);
                                        showDetail(Integer.parseInt(tblTable.getValueAt(tblTable.getSelectedRow(), 0).toString()));
                                    } else {
                                        tblTable.setRowSelectionInterval(n, n);
                                        showDetail(Integer.parseInt(tblTable.getValueAt(tblTable.getSelectedRow(), 0).toString()));
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Error!!!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Error!!!");
                            }
                        }
                    }
                }
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int n = tblTable.getSelectedRow();
                if (dk == 1) {
                    if (dk2 == 1) {
                        if (!validateForm()) {
                            return;
                        }
                        boolean kq = saveToppic();
                        if (kq) {
                            loadDatatoTable(" TOPPIC");
                            showDetail(Integer.parseInt(tblTable.getValueAt(tblTable.getRowCount() - 1, 0).toString()));
                            tblTable.setRowSelectionInterval(tblTable.getRowCount() - 1, tblTable.getRowCount() - 1);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error!!!");
                        }
                    } else {
                        if (!validateForm()) {
                            return;
                        }
                        boolean kq = updateToppic();
                        int choice = JOptionPane.showConfirmDialog(null, "Do you want to update information toppic?",
                                "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (choice == JOptionPane.YES_OPTION) {
                            if (kq) {
                                loadDatatoTable(" TOPPIC");
                                tblTable.setRowSelectionInterval(n, n);
                                showDetail(Integer.parseInt(tblTable.getValueAt(tblTable.getSelectedRow(), 0).toString()));
                            } else {
                                JOptionPane.showMessageDialog(null, "Error!!!");
                            }
                        }
                    }
                } else {
                    if (dk2 == 1) {
                        if (!validateForm()) {
                            return;
                        }
                        boolean kq = saveNXB();
                        if (kq) {
                            loadDatatoTable(" PUBLISHING_HOUSE");
                            showDetail(Integer.parseInt(tblTable.getValueAt(tblTable.getRowCount() - 1, 0).toString()));
                            tblTable.setRowSelectionInterval(tblTable.getRowCount() - 1, tblTable.getRowCount() - 1);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error!!!");
                        }
                    } else {
                        if (!validateForm()) {
                            return;
                        }
                        boolean kq = updateNXB();
                        int choice = JOptionPane.showConfirmDialog(null, "Do you want to update information publising house?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (choice == JOptionPane.YES_OPTION) {
                            if (kq) {
                                loadDatatoTable(" PUBLISHING_HOUSE");
                                tblTable.setRowSelectionInterval(n, n);
                                showDetail(Integer.parseInt(tblTable.getValueAt(tblTable.getSelectedRow(), 0).toString()));
                            } else {
                                JOptionPane.showMessageDialog(null, "Error!!!");
                            }
                        }
                    }
                }
                btnSave.setEnabled(false);
                btnEdit.setEnabled(true);
                btnAdd.setEnabled(true);
                btnIgnore.setEnabled(false);
                btnDelete.setEnabled(true);
                tfPublishingName.setEditable(false);
                tfToppicName.setEditable(false);
                tfSearch.setEditable(true);
                btnSearch.setEnabled(false);
                btnRender.setEnabled(true);
            }
        });

        btnRender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int c = JOptionPane.showConfirmDialog(null, "Do you want to render to file excel?",
                        "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (c == JOptionPane.YES_OPTION) {
                    JFileChooser chooser = new JFileChooser();
                    int choi = chooser.showSaveDialog(chooser);
                    if (choi == JFileChooser.APPROVE_OPTION) {
                        try {
                            File file = chooser.getSelectedFile();
                            String excelFilePath = file + ".xlsx";
                            ExcelWriter excelWriter = new ExcelWriter();
                            List<Object> list = getList();
                            excelWriter.writeExcelTOPPIC(list, excelFilePath);
                        } catch (IOException ex) {
                            Logger.getLogger(Giaodien.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Error!!!");
                        }
                    }
                }
            }
        });
    }

    private void OpenConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:sqlserver://localhost;databaseName=QLTHUVIEN";
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String username = "sa";
        String password = "123";
        Class.forName(driver);
        con = DriverManager.getConnection(url, username, password);
    }

    private void startState() {
        dk = 1;
        btnSave.setEnabled(false);
        btnEdit.setEnabled(false);
        btnAdd.setEnabled(true);
        btnIgnore.setEnabled(false);
        btnDelete.setEnabled(false);
        tfPublishingID.setEditable(false);
        tfPublishingName.setEditable(false);
        tfToppicID.setEditable(false);
        tfToppicName.setEditable(false);
        tfSearch.setEditable(false);
        btnSearch.setEnabled(false);
        btnRender.setEnabled(false);
        btnToppic.setBackground(Color.ORANGE);
        btnPublishing.setBackground(Color.WHITE);
        loadDatatoTable(" TOPPIC");
        if (tblTable.getRowCount() > 0) {
            showDetail(Integer.parseInt(tblTable.getValueAt(0, 0).toString()));
            tblTable.setRowSelectionInterval(0, 0);
            btnEdit.setEnabled(true);
            btnDelete.setEnabled(true);
            btnSave.setEnabled(false);
            tfSearch.setEditable(true);
            btnRender.setEnabled(true);
            tfPublishingID.setText("");
            tfPublishingName.setText("");
        } else {
            btnEdit.setEnabled(false);
            btnAdd.setEnabled(true);
            btnDelete.setEnabled(false);
            btnRender.setEnabled(false);
        }

    }

    private int fildTable() {
        for (int i = 0; i < tblTable.getRowCount(); i++) {
            if (tblTable.getValueAt(i, 1).toString().equalsIgnoreCase(tfSearch.getText())) {
                return i;
            }
        }
        return -1;
    }

    private boolean delete(String table, String role, int id) {
        try {
            OpenConnection();
            String sql = "delete from " + table + " where " + role + "=?";
            PreparedStatement pst2 = con.prepareStatement(sql);
            pst2.setInt(1, id);
            pst2.executeUpdate();
            con.close();
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(QLDanhMuc.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private void loadDatatoTable(String table) {
        try {
            model1.setRowCount(0);
            OpenConnection();
            String sql = "select * from" + table;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getInt(1));
                v.add(rs.getString(2));
                model1.addRow(v);
            }
            tblTable.setModel(model1);
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
    }

    private int AutoID(String tablecolum, String table) {
        int id = 1;
        try {
            OpenConnection();
            String sql = "select top 1 " + tablecolum + " from " + table + " order by " + tablecolum + " desc ";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                id = rs.getInt(1) + 1;

            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(QLDanhMuc.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    private boolean saveToppic() {
        try {
            OpenConnection();
            String sql = "insert into TOPPIC values(?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, AutoID(" TOPPICID", " TOPPIC"));
            st.setString(2, tfToppicName.getText());
            st.executeUpdate();
            con.close();
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(QLDanhMuc.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private boolean saveNXB() {
        try {
            OpenConnection();
            String sql = "insert into PUBLISHING_HOUSE values(?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, AutoID(" PHID", " PUBLISHING_HOUSE"));
            st.setString(2, tfPublishingName.getText());
            st.executeUpdate();
            con.close();
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(QLDanhMuc.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private boolean updateToppic() {
        try {
            OpenConnection();
            String sql = "update TOPPIC set TOPPICNAME=? where TOPPICID=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(2, Integer.parseInt(tfToppicID.getText()));
            st.setString(1, tfToppicName.getText());
            st.executeUpdate();
            con.close();
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(QLDanhMuc.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private boolean updateNXB() {
        try {
            OpenConnection();
            String sql = "update PUBLISHING_HOUSE set PHNAME=? where PHID=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(2, Integer.parseInt(tfPublishingID.getText()));
            st.setString(1, tfPublishingName.getText());
            st.executeUpdate();
            con.close();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(QLDanhMuc.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private boolean validateForm() {
        if (dk == 1) {
            if (tfToppicName.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Tên chủ đề không được trống");
                tfToppicName.requestFocus();
                return false;
            }
            if (!testName()) {
                JOptionPane.showMessageDialog(null, "Tên chủ đề không được trùng");
                tfToppicName.requestFocus();
                return false;
            }
            return true;
        } else {
            if (tfPublishingName.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Tên nhà xuất bản không được trống");
                tfPublishingName.requestFocus();
                return false;
            }
            if (!testName()) {
                JOptionPane.showMessageDialog(null, "Tên nhà xuất bản không được trùng");
                tfPublishingName.requestFocus();
                return false;
            }
            return true;
        }
    }

    private boolean testName() {
        if (dk == 1) {
            if (dk2 == 1) {
                try {
                    OpenConnection();
                    String sql = "select*from TOPPIC";
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while(rs.next()){
                        if (tfToppicName.getText().equalsIgnoreCase(rs.getString(2))) {
                            return false;
                        }
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(QLDanhMuc.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }else{
                try {
                OpenConnection();
                    String sql = "select*from TOPPIC where TOPPICID !="+tfToppicID.getText();
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while(rs.next()){
                        if (tfToppicName.getText().equalsIgnoreCase(rs.getString(2))) {
                            return false;
                        }
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(QLDanhMuc.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }
        }else{
            if (dk2 == 1) {
                try {
                    OpenConnection();
                    String sql = "select*from PUBLISHING_HOUSE";
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while(rs.next()){
                        if (tfPublishingName.getText().equalsIgnoreCase(rs.getString(2))) {
                            return false;
                        }
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(QLDanhMuc.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }else{
                try {
                OpenConnection();
                    String sql = "select*from PUBLISHING_HOUSE where PHID !="+tfPublishingID.getText();
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while(rs.next()){
                        if (tfPublishingName.getText().equalsIgnoreCase(rs.getString(2))) {
                            return false;
                        }
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(QLDanhMuc.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }
        }
    }

    private void ClearForm() {
        tfPublishingID.setText("");
        tfPublishingName.setText("");
        tfToppicID.setText("");
        tfToppicName.setText("");
    }

    private void showDetail(int id) {
        if (dk == 1) {
            try {
                OpenConnection();
                String sql = "select * from TOPPIC where TOPPICID=?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    tfToppicID.setText(rs.getInt(1) + "");
                    tfToppicName.setText(rs.getString(2));
                }
                con.close();

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(QLDanhMuc.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                OpenConnection();
                String sql = "select * from PUBLISHING_HOUSE where PHID=?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    tfPublishingID.setText(rs.getInt(1) + "");
                    tfPublishingName.setText(rs.getString(2));
                }
                con.close();

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(QLDanhMuc.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private List<Object> getList() {
        ArrayList<Object> re = new ArrayList<>();
        for (int i = 0; i < tblTable.getRowCount(); i++) {
            re.add(new Object(Integer.parseInt(tblTable.getValueAt(i, 0).toString()), tblTable.getValueAt(i, 1).toString()));
        }
        return re;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QLDanhMuc qldm = new QLDanhMuc();
            qldm.setTitle("QLTHUVIEN");
            qldm.setSize(720, 600);
            qldm.setVisible(true);
            qldm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            qldm.setLocationRelativeTo(null);

        });
    }
}
