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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.logging.*;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.table.*;

public class Giaodien extends JFrame {

    private JPanel panelall, panelNorth, panelWest, panelCenter, panelCenter1, panelCenter2, panelCenter3, panel1, panel2;
    private JLabel lblTieude, lblBookTheme, lblBookCode, lblPublishing, lblTitle, lblAuthor, lblYear, lblRecord, lblSearch, lblPageNumber, lblPrice, lblLanguage;
    private JTextField tfBookCode, tfTitle, tfAuthor, tfYear, tfSearch, tfRecord, tfPrice, tfPageNumber;
    private JCheckBox ckbOnlyShowThemeBook;
    private JRadioButton rdoVn, rdoEnglish, rdoOther;
    private ButtonGroup buttonGroup1;
    private JButton btnAdd, btnEdit, btnDelete, btnSave, btnIgnore, btnSearch, btnRender;
    private JComboBox cboBookTheme, cboPublishing;
    private final DefaultTableModel model1, model2;
    private final JTable tblTheme, tblBook;
    private final JScrollPane scrollPane1, scrollPane2;
    private Connection con;
    private int index;
    private String check = "checked";
    private DefaultComboBoxModel cboModel1, cboModel2;
    private int dk = 0;

    public Giaodien() {

        panelall = new JPanel(new BorderLayout(20, 20));
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc;

        // North
        panelNorth = new JPanel();
        lblTieude = new JLabel("QUẢN LÝ THƯ VIỆN");
        lblTieude.setForeground(Color.blue);
        lblTieude.setFont(new java.awt.Font("Tahoma", 1, 16));
        panelNorth.add(lblTieude);

        panelall.add(panelNorth, BorderLayout.NORTH);

        // West
        panelWest = new JPanel(new BorderLayout(0, 10));
        ckbOnlyShowThemeBook = new JCheckBox("Chỉ hiện thị chủ đề có sách");
        ckbOnlyShowThemeBook.setPreferredSize(new Dimension(0, 35));
        ckbOnlyShowThemeBook.setFocusPainted(false);
        String[] headers = {"STT", "Chủ đề", "Mã chủ đề"};
        String[][] data = {};
        model1 = new DefaultTableModel(data, headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblTheme = new JTable(model1);
        tblTheme.getColumnModel().getColumn(0).setMaxWidth(40);
        tblTheme.getColumnModel().getColumn(2).setWidth(0);
        tblTheme.getColumnModel().getColumn(2).setMinWidth(0);
        tblTheme.getColumnModel().getColumn(2).setMaxWidth(0);
        scrollPane1 = new JScrollPane(tblTheme);
        scrollPane1.setPreferredSize(new Dimension(220, 100));
        panelWest.add(ckbOnlyShowThemeBook, BorderLayout.NORTH);
        panelWest.add(scrollPane1, BorderLayout.CENTER);
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

        lblBookTheme = new JLabel("Chủ đề");
        lblBookCode = new JLabel("Mã sách");
        lblPublishing = new JLabel("Nhà xuất bản");
        lblTitle = new JLabel("Tiêu đề");
        lblAuthor = new JLabel("Tác giả");
        lblYear = new JLabel("Năm xuất bản");
        lblRecord = new JLabel("Số bản lưu");
        lblSearch = new JLabel("Tìm kiếm");
        lblPageNumber = new JLabel("Số trang");
        lblPrice = new JLabel("Giá");
        lblLanguage = new JLabel("Ngôn ngữ");

        tfBookCode = new JTextField();
        cboPublishing = new JComboBox<>();
        tfTitle = new JTextField();
        tfAuthor = new JTextField();
        tfYear = new JTextField();
        tfSearch = new JTextField();
        cboBookTheme = new JComboBox();
        tfPageNumber = new JTextField();
        tfPrice = new JTextField();
        rdoVn = new JRadioButton("Tiếng Việt");
        rdoEnglish = new JRadioButton("Tiếng Anh");
        rdoOther = new JRadioButton("Khác");
        buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(rdoVn);
        buttonGroup1.add(rdoEnglish);
        buttonGroup1.add(rdoOther);
        tfRecord = new JTextField();
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
        gbc.weightx = 10;
        gbc.insets = new Insets(10, 5, 0, 0);
        gbc.anchor = GridBagConstraints.LINE_START;
        panelCenter1.add(lblBookTheme, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.ipady = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCenter1.add(cboBookTheme, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 10;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelCenter1.add(lblBookCode, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 30;
        gbc.ipady = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCenter1.add(tfBookCode, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 10;
        gbc.anchor = GridBagConstraints.LINE_END;
        panelCenter1.add(lblPublishing, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.ipady = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCenter1.add(cboPublishing, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 10;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelCenter1.add(lblTitle, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        gbc.ipady = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCenter1.add(tfTitle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 10;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelCenter1.add(lblAuthor, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 5;
        gbc.ipady = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCenter1.add(tfAuthor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 10;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelCenter1.add(lblYear, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.ipady = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCenter1.add(tfYear, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 10;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelCenter1.add(lblPageNumber, gbc);

        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 30;
        gbc.ipady = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCenter1.add(tfPageNumber, gbc);

        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 10;
        gbc.fill = GridBagConstraints.LINE_END;
        panelCenter1.add(lblPrice, gbc);

        gbc.gridx = 5;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 30;
        gbc.ipady = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCenter1.add(tfPrice, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.weightx = 10;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelCenter1.add(lblRecord, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.weightx = 70;
        gbc.ipady = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCenter1.add(tfRecord, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.weightx = 10;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelCenter1.add(lblLanguage, gbc);

        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelCenter1.add(rdoVn, gbc);

        gbc.gridx = 4;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelCenter1.add(rdoEnglish, gbc);

        gbc.gridx = 5;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelCenter1.add(rdoOther, gbc);

        panel2 = new JPanel(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 5, 0, 0);
        panel2.add(lblSearch, gbc);

        gbc.gridx = 1;
        gbc.weightx = 2;
        gbc.ipady = 6;
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

        String[] headers1 = {"Mã sách", "Tiêu đề", "Số bản lưu"};
        String[][] data1 = {};
        model2 = new DefaultTableModel(data1, headers1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblBook = new JTable(model2);
        tblBook.getColumnModel().getColumn(1).setMinWidth(450);
        scrollPane2 = new JScrollPane(tblBook);
        scrollPane2.setMinimumSize(new Dimension(200, 350));

        panelCenter2.add(scrollPane2, BorderLayout.CENTER);
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

        ckbOnlyShowThemeBook.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                check = ie.getStateChange() == 1 ? "checked" : "unchecked";
                loadDatatoTableTopPic();
                if (tblTheme.getRowCount() > 0) {
                    String dieukien = " where TOPPICID=" + tblTheme.getValueAt(0, 2);
                    tblTheme.setRowSelectionInterval(0, 0);
                    loadDatatoTableSach(dieukien);
                    if (tblBook.getRowCount() > 0) {
                        showDetail(Integer.parseInt(tblBook.getValueAt(0, 0).toString()));
                        tblBook.setRowSelectionInterval(0, 0);
                        btnSave.setEnabled(false);
                        btnEdit.setEnabled(true);
                        btnAdd.setEnabled(true);
                        btnIgnore.setEnabled(false);
                        btnDelete.setEnabled(true);
                        tfAuthor.setEditable(false);
                        tfPageNumber.setEditable(false);
                        tfBookCode.setEditable(false);
                        tfRecord.setEditable(false);
                        tfPrice.setEditable(false);
                        tfSearch.setEditable(true);
                        tfTitle.setEditable(false);
                        tfYear.setEditable(false);
                        cboBookTheme.setEnabled(false);
                        cboPublishing.setEnabled(false);
                        rdoEnglish.setEnabled(false);
                        rdoOther.setEnabled(false);
                        rdoVn.setEnabled(false);
                        btnSearch.setEnabled(false);
                        btnRender.setEnabled(true);
                        tfSearch.setText("");
                    } else {
                        ClearForm();
                        btnSave.setEnabled(false);
                        btnEdit.setEnabled(false);
                        btnAdd.setEnabled(true);
                        btnIgnore.setEnabled(false);
                        btnDelete.setEnabled(false);
                        tfAuthor.setEditable(false);
                        tfPageNumber.setEditable(false);
                        tfBookCode.setEditable(false);
                        tfRecord.setEditable(false);
                        tfPrice.setEditable(false);
                        tfSearch.setEditable(false);
                        tfTitle.setEditable(false);
                        tfYear.setEditable(false);
                        cboBookTheme.setEnabled(false);
                        cboPublishing.setEnabled(false);
                        rdoEnglish.setEnabled(false);
                        rdoOther.setEnabled(false);
                        rdoVn.setEnabled(false);
                        btnSearch.setEnabled(false);
                        btnRender.setEnabled(false);
                        tfSearch.setText("");
                    }
                }
            }
        });

        tblBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                index = tblBook.getSelectedRow();
                showDetail(Integer.parseInt(tblBook.getValueAt(index, 0).toString()));
                btnSave.setEnabled(false);
                btnEdit.setEnabled(true);
                btnAdd.setEnabled(true);
                btnIgnore.setEnabled(false);
                btnDelete.setEnabled(true);
                tfAuthor.setEditable(false);
                tfPageNumber.setEditable(false);
                tfBookCode.setEditable(false);
                tfRecord.setEditable(false);
                tfPrice.setEditable(false);
                tfSearch.setEditable(true);
                tfTitle.setEditable(false);
                tfYear.setEditable(false);
                cboBookTheme.setEnabled(false);
                cboPublishing.setEnabled(false);
                rdoEnglish.setEnabled(false);
                rdoOther.setEnabled(false);
                rdoVn.setEnabled(false);
                btnSearch.setEnabled(false);
                btnRender.setEnabled(true);
                tfSearch.setText("");
            }
        });

        tblBook.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                index = tblBook.getSelectedRow();
                showDetail(Integer.parseInt(tblBook.getValueAt(index, 0).toString()));
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
                btnSave.setEnabled(false);
            }
        });

        tblTheme.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int k = tblTheme.getSelectedRow();
                if (k >= 0) {
                    String dieukien = " where TOPPICID=" + tblTheme.getValueAt(k, 2);
                    loadDatatoTableSach(dieukien);
                    tblTheme.setRowSelectionInterval(k, k);
                    if (tblBook.getRowCount() > 0) {
                        showDetail(Integer.parseInt(tblBook.getValueAt(0, 0).toString()));
                        tblBook.setRowSelectionInterval(0, 0);
                        btnSave.setEnabled(false);
                        btnEdit.setEnabled(true);
                        btnAdd.setEnabled(true);
                        btnIgnore.setEnabled(false);
                        btnDelete.setEnabled(true);
                        tfAuthor.setEditable(false);
                        tfPageNumber.setEditable(false);
                        tfBookCode.setEditable(false);
                        tfRecord.setEditable(false);
                        tfPrice.setEditable(false);
                        tfSearch.setEditable(true);
                        tfTitle.setEditable(false);
                        tfYear.setEditable(false);
                        cboBookTheme.setEnabled(false);
                        cboPublishing.setEnabled(false);
                        rdoEnglish.setEnabled(false);
                        rdoOther.setEnabled(false);
                        rdoVn.setEnabled(false);
                        btnSearch.setEnabled(false);
                        btnRender.setEnabled(true);
                        tfSearch.setText("");
                    } else {
                        ClearForm();
                        btnSave.setEnabled(false);
                        btnEdit.setEnabled(false);
                        btnAdd.setEnabled(true);
                        btnIgnore.setEnabled(false);
                        btnDelete.setEnabled(false);
                        tfAuthor.setEditable(false);
                        tfPageNumber.setEditable(false);
                        tfBookCode.setEditable(false);
                        tfRecord.setEditable(false);
                        tfPrice.setEditable(false);
                        tfSearch.setEditable(false);
                        tfTitle.setEditable(false);
                        tfYear.setEditable(false);
                        cboBookTheme.setEnabled(false);
                        cboPublishing.setEnabled(false);
                        rdoEnglish.setEnabled(false);
                        rdoOther.setEnabled(false);
                        rdoVn.setEnabled(false);
                        btnSearch.setEnabled(false);
                        btnRender.setEnabled(false);
                        tfSearch.setText("");
                    }
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
                tfAuthor.setEditable(true);
                tfPageNumber.setEditable(true);
                tfBookCode.setEditable(false);
                tfRecord.setEditable(true);
                tfPrice.setEditable(true);
                tfSearch.setEditable(false);
                tfTitle.setEditable(true);
                tfYear.setEditable(true);
                cboBookTheme.setEnabled(true);
                cboPublishing.setEnabled(true);
                rdoEnglish.setEnabled(true);
                rdoOther.setEnabled(true);
                rdoVn.setEnabled(true);
                btnSearch.setEnabled(false);
                btnRender.setEnabled(false);
                tblBook.clearSelection();
                tblTheme.clearSelection();
                tfSearch.setText("");
                dk = 1;
            }
        });

        btnIgnore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                startState();
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
                tfAuthor.setEditable(true);
                tfPageNumber.setEditable(true);
                tfBookCode.setEditable(false);
                tfRecord.setEditable(true);
                tfPrice.setEditable(true);
                tfSearch.setEditable(true);
                tfTitle.setEditable(true);
                tfYear.setEditable(true);
                cboBookTheme.setEnabled(true);
                cboPublishing.setEnabled(true);
                rdoEnglish.setEnabled(true);
                rdoOther.setEnabled(true);
                rdoVn.setEnabled(true);
                btnSearch.setEnabled(false);
                btnRender.setEnabled(true);
                tfSearch.setText("");
                dk = 2;
                tfTitle.requestFocus();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int n = tblBook.getSelectedRow();
                String dieukien = " where TOPPICID=" + tblTheme.getValueAt(tblTheme.getSelectedRow(), 2);
                if (n >= 0) {
                    int choice = JOptionPane.showConfirmDialog(null, "Do you want to remove book: " + tfTitle.getText() + "?", "Confirm?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (choice == JOptionPane.YES_OPTION) {
                        boolean kq = deleteBook(Integer.parseInt(tfBookCode.getText()));
                        if (kq) {
                            tfSearch.setText("");
                            loadDatatoTableSach(dieukien);
                            if (tblBook.getRowCount() == 0) {
                                ClearForm();
                                btnRender.setEnabled(false);
                                btnDelete.setEnabled(false);
                                tfSearch.setEditable(false);
                                btnEdit.setEnabled(false);
                            } else if (n == 0) {
                                tblBook.setRowSelectionInterval(0, 0);
                                showDetail(Integer.parseInt(tblBook.getValueAt(tblBook.getSelectedRow(), 0).toString()));
                            } else if (n == tblBook.getRowCount()) {
                                tblBook.setRowSelectionInterval(n - 1, n - 1);
                                showDetail(Integer.parseInt(tblBook.getValueAt(tblBook.getSelectedRow(), 0).toString()));
                            } else {
                                tblBook.setRowSelectionInterval(n, n);
                                showDetail(Integer.parseInt(tblBook.getValueAt(tblBook.getSelectedRow(), 0).toString()));
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Error!!!");
                        }
                    }
                }
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int n = tblBook.getSelectedRow();
                if (dk == 1) {
                    if (!validateForm()) {
                        return;
                    }
                    boolean kq = saveBook();
                    if (kq) {
                        loadDatatoTableTopPic();
                        for (int i = 0; i < tblTheme.getRowCount(); i++) {
                            if (Integer.parseInt(tblTheme.getValueAt(i, 2).toString()) == cboBookTheme.getSelectedIndex()) {
                                tblTheme.setRowSelectionInterval(i, i);
                                String dieukien = " where TOPPICID=" + tblTheme.getValueAt(i, 2);
                                loadDatatoTableSach(dieukien);
                                showDetail(Integer.parseInt(tblBook.getValueAt(tblBook.getRowCount() - 1, 0).toString()));
                                tblBook.setRowSelectionInterval(tblBook.getRowCount() - 1, tblBook.getRowCount() - 1);
                                break;
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error!!!");
                    }
                } else if (dk == 2) {
                    if (!validateForm()) {
                        return;
                    }
                    boolean kq = updateBook();
                    int choice = JOptionPane.showConfirmDialog(null, "Do you want to update information book?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (choice == JOptionPane.YES_OPTION) {
                        if (kq) {
                            String dieukien = " where TOPPICID=" + tblTheme.getValueAt(tblTheme.getSelectedRow(), 2);
                            loadDatatoTableSach(dieukien);
                            tblBook.setRowSelectionInterval(n, n);
                            showDetail(Integer.parseInt( tblBook.getValueAt(tblBook.getSelectedRow(), 0).toString()));
                        } else {
                            JOptionPane.showMessageDialog(null, "Error!!!");
                        }
                    }
                }
                btnSave.setEnabled(false);
                btnEdit.setEnabled(true);
                btnAdd.setEnabled(true);
                btnIgnore.setEnabled(false);
                btnDelete.setEnabled(true);
                tfAuthor.setEditable(false);
                tfPageNumber.setEditable(false);
                tfBookCode.setEditable(false);
                tfRecord.setEditable(false);
                tfPrice.setEditable(false);
                tfSearch.setEditable(true);
                tfTitle.setEditable(false);
                tfYear.setEditable(false);
                cboBookTheme.setEnabled(false);
                cboPublishing.setEnabled(false);
                rdoEnglish.setEnabled(false);
                rdoOther.setEnabled(false);
                rdoVn.setEnabled(false);
                btnSearch.setEnabled(false);
                btnRender.setEnabled(true);
            }
        });

        btnRender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int c = JOptionPane.showConfirmDialog(null, "Do you want to render to file excel?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (c == JOptionPane.YES_OPTION) {
                    JFileChooser chooser = new JFileChooser();
                    int choi = chooser.showSaveDialog(chooser);
                    if (choi == JFileChooser.APPROVE_OPTION) {
                        try {
                            File file = chooser.getSelectedFile();
                            String excelFilePath = file + ".xlsx";
                            ExcelWriter excelWriter = new ExcelWriter();
                            List<Book> listBook = getListBook();
                            excelWriter.writeExcelBook(listBook, excelFilePath);
                        } catch (IOException ex) {
                            Logger.getLogger(Giaodien.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Error!!!");
                        }
                    }
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
                    tblBook.setRowSelectionInterval(kq, kq);
                    showDetail(Integer.parseInt(tblBook.getValueAt(tblBook.getSelectedRow(), 0).toString()));
                }
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

    }

    private void OpenConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:sqlserver://localhost;databaseName=QLTHUVIEN";
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String username = "sa";
        String password = "123";
        Class.forName(driver);
        con = DriverManager.getConnection(url, username, password);
    }

    private void ClearForm() {
        tfAuthor.setText("");
        tfBookCode.setText("");
        tfPageNumber.setText("");
        tfPrice.setText("");
        tfRecord.setText("");
        tfTitle.setText("");
        tfYear.setText("");
        cboBookTheme.setSelectedIndex(0);
        cboPublishing.setSelectedIndex(0);
        buttonGroup1.clearSelection();
        tfTitle.requestFocus();
    }

    private int AutoID() {
        int id = 1;
        try {
            OpenConnection();
            String sql = "select top 1 BOOKID from BOOK order by BOOKID desc ";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Giaodien.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    private boolean validateForm() {
        if (cboBookTheme.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Chưa chọn chủ đề");
            return false;
        }
        if (cboPublishing.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Chưa chọn nhà xuất bản");
            return false;
        }
        if (tfTitle.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Tiêu đề không được trống");
            tfTitle.requestFocus();
            return false;
        }
        if (!testTittle()) {
            JOptionPane.showMessageDialog(null, "Tiêu đề không được trùng");
            tfTitle.requestFocus();
            return false;
        }
        if (tfAuthor.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Tác giả không được trống");
            tfAuthor.requestFocus();
            return false;
        }
        if (tfYear.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ngày xuất bản không được trống");
            tfYear.requestFocus();
            return false;
        }
        if (!validateDate(tfYear.getText())) {
            JOptionPane.showMessageDialog(null, "Điền sai ngày tháng. Lưu ý định dạng dd-mm-yyyy");
            tfYear.requestFocus();
            return false;
        }
        if (!testDate()) {
            JOptionPane.showMessageDialog(null, "Ngày xuất bản phải nhỏ hơn ngày hiện tại");
            tfYear.requestFocus();
            return false;
        }
        if (tfPageNumber.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Số trang không được để trống");
            tfPageNumber.requestFocus();
            return false;
        }
        if (!tfPageNumber.getText().matches("\\d*")) {
            JOptionPane.showMessageDialog(null, "Yêu cầu điền số nguyên");
            tfPageNumber.requestFocus();
            return false;
        }
        if (Integer.parseInt(tfPageNumber.getText()) < 5) {
            JOptionPane.showMessageDialog(null, "Số trang lớn hơn 4");
            tfPageNumber.requestFocus();
            return false;
        }
        if (tfPrice.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Giá không được để trống");
            tfPrice.requestFocus();
            return false;
        }
        if (!tfPrice.getText().matches("\\d*")) {
            JOptionPane.showMessageDialog(null, "Yêu cầu điền số nguyên");
            tfPrice.requestFocus();
            return false;
        }
        if (Integer.parseInt(tfPrice.getText()) < 1000) {
            JOptionPane.showMessageDialog(null, "Giá bìa tối thiểu là 1000");
            tfPrice.requestFocus();
            return false;
        }
        if (tfRecord.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Số bản lưu không được để trống");
            tfRecord.requestFocus();
            return false;
        }
        if (!tfRecord.getText().matches("\\d*")) {
            JOptionPane.showMessageDialog(null, "Yêu cầu điền số nguyên");
            tfRecord.requestFocus();
            return false;
        }
        if (Integer.parseInt(tfRecord.getText()) < 1) {
            JOptionPane.showMessageDialog(null, "Số bản lưu tối thiểu là 1");
            tfRecord.requestFocus();
            return false;
        }
        return true;
    }

    private static Pattern dateRegexPattern = Pattern.compile("(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-((19|20)\\d\\d)");

    private static boolean validateDate(String dateString) {
        Matcher dateMatcher = dateRegexPattern.matcher(dateString);
        if (dateMatcher.matches()) {
            dateMatcher.reset();
            if (dateMatcher.find()) {
                String day = dateMatcher.group(1);
                String month = dateMatcher.group(2);
                int year = Integer.parseInt(dateMatcher.group(3));
                if ("31".equals(day)
                        && ("4".equals(month) || "6".equals(month) || "9".equals(month)
                        || "11".equals(month) || "04".equals(month) || "06".equals(month)
                        || "09".equals(month))) {
                    return false;
                } else if ("2".equals(month) || "02".equals(month)) {
                    if (year % 4 == 0) {
                        return !"30".equals(day) && !"31".equals(day);
                    } else {
                        return !"29".equals(day) && !"30".equals(day) && !"31".equals(day);
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean testDate() {
        LocalDate nowLocalDate = LocalDate.now();
        LocalDate oldLocalDate = LocalDate.of(Integer.parseInt(tfYear.getText().substring(6, 10)),
                Integer.parseInt(tfYear.getText().substring(3, 5)), Integer.parseInt(tfYear.getText().substring(0, 2)));
        if (oldLocalDate.isBefore(nowLocalDate)) {
            return true;
        }
        return false;
    }
    
    private boolean testTittle(){
        if (dk==1) {
            try {
                OpenConnection();
                String sql="select*from BOOK";
                Statement st= con.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while(rs.next()){
                    if (tfTitle.getText().equalsIgnoreCase(rs.getString(2))) {
                        return false;
                    }
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Giaodien.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }else{
            try {
                OpenConnection();
                String sql="select*from BOOK where BOOKID !="+tfBookCode.getText();
                Statement st= con.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while(rs.next()){
                    if (tfTitle.getText().equalsIgnoreCase(rs.getString(2))) {
                        return false;
                    }
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Giaodien.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
    }

    private int fildTable() {
        for (int i = 0; i < tblBook.getRowCount(); i++) {
            if (tblBook.getValueAt(i, 1).toString().equalsIgnoreCase(tfSearch.getText())) {
                return i;
            }
        }
        return -1;
    }

    private void showDetail(int id) {
        try {
            OpenConnection();
            String sql = "select * from Book where BOOKID=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                tfBookCode.setText(rs.getInt(1) + "");
                tfTitle.setText(rs.getString(2));
                tfAuthor.setText(rs.getString(5));
                tfPageNumber.setText(rs.getInt(7) + "");
                tfPrice.setText(rs.getInt(8) + "");
                tfRecord.setText(rs.getInt(9) + "");
                tfYear.setText(new SimpleDateFormat("dd-MM-yyyy").format(rs.getDate(6)));
                cboBookTheme.setSelectedIndex(rs.getInt(4));
                cboPublishing.setSelectedIndex(rs.getInt(3));
                switch (rs.getInt(10)) {
                    case 1:
                        rdoVn.setSelected(true);
                        break;
                    case 2:
                        rdoEnglish.setSelected(true);
                        break;
                    default:
                        rdoOther.setSelected(true);
                        break;
                }
            }
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Giaodien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Vector<Object> Loaddata(String tablename) {
        Vector<Object> re = new Vector<>();
        re.add(new Object(0, ""));
        try {
            OpenConnection();
            Statement st = con.createStatement();
            String sql = "select*from " + tablename;
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                re.add(new Object(rs.getInt(1), rs.getString(2)));
            }
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Giaodien.class.getName()).log(Level.SEVERE, null, ex);
        }
        return re;
    }

    private void loadCombobox() {
        cboModel1 = new DefaultComboBoxModel<>(Loaddata("TOPPIC"));
        cboBookTheme.setModel(cboModel1);
        cboBookTheme.setRenderer(
                new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> jlist, java.lang.Object o,
                    int i, boolean bln, boolean bln1) {
                JLabel lb;
                lb = (JLabel) super.getListCellRendererComponent(jlist, o, i, bln, bln1);
                lb.setText(((Object) o).getName());
                return lb;
            }
        });
        cboModel2 = new DefaultComboBoxModel<>(Loaddata("PUBLISHING_HOUSE"));
        cboPublishing.setModel(cboModel2);
        cboPublishing.setRenderer(
                new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> jlist, java.lang.Object o,
                    int i, boolean bln, boolean bln1) {
                JLabel lb;
                lb = (JLabel) super.getListCellRendererComponent(jlist, o, i, bln, bln1);
                lb.setText(((Object) o).getName());
                return lb;
            }
        });
    }

    private void loadDatatoTableTopPic() {
        try {
            model1.setRowCount(0);
            OpenConnection();
            String sql = "select * from TOPPIC";
            if (check.equals("checked")) {
                sql = sql + " where TOPPICID in (select TOPPICID from Book)";
            }
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            int stt = 0;
            while (rs.next()) {
                Vector v = new Vector();
                stt++;
                v.add(stt);
                v.add(rs.getString(2));
                v.add(rs.getInt(1));
                model1.addRow(v);
            }
            tblTheme.setModel(model1);
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
    }

    private void loadDatatoTableSach(String dieukien) {
        try {
            model2.setRowCount(0);
            OpenConnection();
            String sql = "select * from BOOK";
            if (!dieukien.equals("")) {
                sql = sql + dieukien;
            }
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(9));
                model2.addRow(v);
            }
            tblBook.setModel(model2);
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
    }

    private boolean deleteBook(int id) {
        try {
            OpenConnection();
            String sql = "delete from BOOK where BOOKID=?";
            PreparedStatement pst2 = con.prepareStatement(sql);
            pst2.setInt(1, id);
            pst2.executeUpdate();
            con.close();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Giaodien.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private boolean saveBook() {
        try {
            OpenConnection();
            String sql = "insert into BOOK values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, AutoID());
            st.setString(2, tfTitle.getText());
            st.setInt(3, cboPublishing.getSelectedIndex());
            st.setInt(4, cboBookTheme.getSelectedIndex());
            st.setString(5, tfAuthor.getText());
            st.setString(6, tfYear.getText().substring(3, 5) + "-" + tfYear.getText().substring(0, 2) + "-" + tfYear.getText().substring(6, 10));
            st.setInt(7, Integer.parseInt(tfPageNumber.getText()));
            st.setInt(8, Integer.parseInt(tfPrice.getText()));
            st.setInt(9, Integer.parseInt(tfRecord.getText()));
            int a;
            if (rdoVn.isSelected()) {
                a = 1;
            } else if (rdoEnglish.isSelected()) {
                a = 2;
            } else {
                a = 3;
            }
            st.setInt(10, a);
            st.executeUpdate();
            con.close();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Giaodien.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private boolean updateBook() {
        try {
            OpenConnection();
            String sql = "update BOOK set NAME=?, PHID=?, TOPPICID=?, AUTHOR=?, DATES=?, PAGENUMBER=?, PRICE=?, KEPTNUMBER=?, LANGUAGES=? where BOOKID=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(10, Integer.parseInt(tfBookCode.getText()));
            st.setString(1, tfTitle.getText());
            st.setInt(2, cboPublishing.getSelectedIndex());
            st.setInt(3, cboBookTheme.getSelectedIndex());
            st.setString(4, tfAuthor.getText());
            System.out.println();
            st.setString(5, tfYear.getText().substring(3, 5) + "-" + tfYear.getText().substring(0, 2) + "-" + tfYear.getText().substring(6, 10));
            st.setInt(6, Integer.parseInt(tfPageNumber.getText()));
            st.setInt(7, Integer.parseInt(tfPrice.getText()));
            st.setInt(8, Integer.parseInt(tfRecord.getText()));
            int a;
            if (rdoVn.isSelected()) {
                a = 1;
            } else if (rdoEnglish.isSelected()) {
                a = 2;
            } else {
                a = 3;
            }
            st.setInt(9, a);
            st.executeUpdate();
            con.close();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Giaodien.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private List<Book> getListBook() {
        ArrayList<Book> re = new ArrayList<>();
        for (int i = 0; i < tblBook.getRowCount(); i++) {
            re.add(new Book(Integer.parseInt(tblBook.getValueAt(i, 0).toString()), tblBook.getValueAt(i, 1).toString(), Integer.parseInt(tblBook.getValueAt(i, 2).toString())));
        }
        return re;
    }

    private void startState() {
        ckbOnlyShowThemeBook.setSelected(true);
        btnSave.setEnabled(false);
        btnEdit.setEnabled(false);
        btnAdd.setEnabled(true);
        btnIgnore.setEnabled(false);
        btnDelete.setEnabled(false);
        tfAuthor.setEditable(false);
        tfPageNumber.setEditable(false);
        tfBookCode.setEditable(false);
        tfRecord.setEditable(false);
        tfPrice.setEditable(false);
        tfSearch.setEditable(false);
        tfTitle.setEditable(false);
        tfYear.setEditable(false);
        cboBookTheme.setEnabled(false);
        cboPublishing.setEnabled(false);
        rdoEnglish.setEnabled(false);
        rdoOther.setEnabled(false);
        rdoVn.setEnabled(false);
        btnSearch.setEnabled(false);
        btnRender.setEnabled(false);
        loadDatatoTableSach("");
        loadDatatoTableTopPic();
        loadCombobox();
        if (checkCoutnData(" TOPPIC") && checkCoutnData(" PUBLISHING_HOUSE")) {
            if (tblTheme.getRowCount() > 0) {
                String dieukien = " where TOPPICID=" + tblTheme.getValueAt(0, 2);
                loadDatatoTableSach(dieukien);
                tblTheme.setRowSelectionInterval(0, 0);
                if (tblBook.getRowCount() > 0) {
                    showDetail(Integer.parseInt(tblBook.getValueAt(0, 0).toString()));
                    tblBook.setRowSelectionInterval(0, 0);
                    btnEdit.setEnabled(true);
                    btnDelete.setEnabled(true);
                    btnSave.setEnabled(false);
                    tfSearch.setEditable(true);
                    btnRender.setEnabled(true);
                } else {
                    ClearForm();
                    btnEdit.setEnabled(false);
                    btnDelete.setEnabled(false);
                    btnSave.setEnabled(true);
                }
            }
        } else {
            btnEdit.setEnabled(false);
            btnAdd.setEnabled(false);
            btnDelete.setEnabled(false);
            btnRender.setEnabled(false);
        }
    }

    private boolean checkCoutnData(String tableName) {
        try {
            OpenConnection();
            String sql = "select*from " + tableName;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.isBeforeFirst() == false) {
                return false;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Giaodien.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Giaodien gd = new Giaodien();
            gd.setTitle("QLTHUVIEN");
            gd.setSize(900, 680);
            gd.setVisible(true);
            gd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gd.setLocationRelativeTo(null);

        });
    }
}
