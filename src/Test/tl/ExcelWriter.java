/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test.tl;

import java.io.*;
import java.sql.*;
import java.util.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Admin
 */
public class ExcelWriter {
    Connection con;
    Giaodien gd= new Giaodien();
    
    private Workbook getWorkbook(String excelFilePath)
            throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
        return workbook;
    }
    
    public void writeExcelTOPPIC(List<Object> listObject,String excelFilePath) throws IOException {
        Workbook workbook = getWorkbook(excelFilePath);
        Sheet sheet = workbook.createSheet("SHEET1");
        createHeaderRowObject(sheet);
        int rowCount = 0;
        for (Object aObject : listObject) {
            Row row = sheet.createRow(++rowCount);
            writeObject(aObject, row);
        }
        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        }
    }
    
    public void writeExcelNXB(List<Object> listObject,String excelFilePath) throws IOException {
        Workbook workbook = getWorkbook(excelFilePath);
        Sheet sheet = workbook.createSheet("NHÀ XUẤT BẢN");
        createHeaderRowObject(sheet);
        int rowCount = 0;
        for (Object aObject : listObject) {
            Row row = sheet.createRow(++rowCount);
            writeObject(aObject, row);
        }
        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        }
    }

    private void writeObject(Object aObject, Row row) {
        Cell cell = row.createCell(1);
        cell.setCellValue(aObject.getId());
        cell = row.createCell(2);
        cell.setCellValue(aObject.getName());
    }

    private void createHeaderRowObject(Sheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);
        Row row = sheet.createRow(0);
        Cell cellID = row.createCell(1);
        cellID.setCellStyle(cellStyle);
        cellID.setCellValue("ID");
        Cell cellName = row.createCell(2);
        cellName.setCellStyle(cellStyle);
        cellName.setCellValue("Name");
    }

    public void writeExcelBook(List<Book> listBook, String excelFilePath) throws IOException {
        Workbook workbook = getWorkbook(excelFilePath);
        Sheet sheet = workbook.createSheet("SÁCH");
        createHeaderRowBook(sheet);
        int rowCount = 0;
        for (Book aBook : listBook) {
            Row row = sheet.createRow(++rowCount);
            writeBook(aBook, row);
        }
        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        }
    }
    
    private void writeBook(Book aBook, Row row) {
        Cell cell = row.createCell(1);
        cell.setCellValue(aBook.getId());
        cell = row.createCell(2);
        cell.setCellValue(aBook.getName());
        cell = row.createCell(10);
        cell.setCellValue(aBook.getKeptnumber());
    }

    private void createHeaderRowBook(Sheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);
        Row row = sheet.createRow(0);
        Cell cellID = row.createCell(1);
        cellID.setCellStyle(cellStyle);
        cellID.setCellValue("ID");
        Cell cellName = row.createCell(2);
        cellName.setCellStyle(cellStyle);
        cellName.setCellValue("Name");
        Cell K = row.createCell(3);
        K.setCellStyle(cellStyle);       
        K.setCellValue("Số bản lưu");;
    }
    
    public void writeExcelFull(List<Object> listTOPPIC,List<Object> listNXB,List<Book> listBook,String excelFilePath) throws IOException {
        Workbook workbook = getWorkbook(excelFilePath);
        Sheet sheet1 = workbook.createSheet("TOPPIC");
        createHeaderRowObject(sheet1);
        int rowCount1 = 0;
        for (Object aObject : listTOPPIC) {
            Row row = sheet1.createRow(++rowCount1);
            writeObject(aObject, row);
        }
        Sheet sheet2 = workbook.createSheet("NHÀ XUẤT BẢN");
        createHeaderRowObject(sheet2);
        int rowCount2 = 0;
        for (Object aObject : listNXB) {
            Row row = sheet2.createRow(++rowCount2);
            writeObject(aObject, row);
        }
        Sheet sheet3 = workbook.createSheet("SÁCH");
        createHeaderRowBook(sheet3);
        int rowCount3 = 0;
        for (Book aBook : listBook) {
            Row row = sheet3.createRow(++rowCount3);
            writeBook(aBook, row);
        }
        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        }
    }
}
