/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RZ09
 */
public class CategoryDAO extends DAO<Category> {

    private static final Connection CONN = jdbc.getconnection();

    @Override
    public List<Category> getAll() throws SQLException {
        String sql = "SELECT * FROM PHAN_LOAI";
        List<Category> categories = new ArrayList<>();
        try {
            //su dung ket noi de tao cau lenh,statement: thi hanh cau lenh tai thoi diem goi
            Statement s = CONN.createStatement();

            //thi hanh cau lenh truy van, tra ket qua truy van qua doi tuong ressultset
            ResultSet r = s.executeQuery(sql);

            while (r.next()) {
                // rs.getString(2): GET column 2 in table PHAN_LOAI
                Category c = new Category(r.getInt(1), r.getString(2));
                categories.add(c);
            }

            r.close();
            s.close();
        } catch (SQLException e) {
            System.out.println("Khong the lay du lieu in PHAN_LOAI");
        }
        return categories;
    }

    @Override
    public void update(Category entity) {
        //TODO update Category
    }

    @Override
    public Category find(Serializable id) {
        //TODO find Category
        return null;
    }
    
    public Category find(List<Category> categories, String name){
        for (Category c : categories) {
            if(name.trim().equals(c.getName().trim())){
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Category> findBy(Serializable by) {
        //TODO findBy Category
        return null;
    }

    public static void main(String[] args) throws SQLException {
        // Run test
        CategoryDAO categoryDAO = new CategoryDAO();
        for (Category c : categoryDAO.getAll()) {
            System.out.println(c);
        }
    }
}
