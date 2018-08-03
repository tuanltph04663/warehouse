package Test;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO extends DAO<Product> {

    private static final Connection CONN = jdbc.getconnection();

    public boolean insert(Product entity) {
        String sql = "INSERT INTO HANG_HOA VALUES(?,?,?,?,?,?,?,?)";
        System.out.println("Prepare insert product...");
        String name = entity.getName();
        String code = entity.getCode();
        String expiryDate = entity.getExpiryDate();
        String phanloai = entity.getPhanloai();
        String hang = entity.getHang();
        String kho = entity.getKho();
        String price = entity.getPrice();
        String tonKho = entity.getTonKho();
        
        try {
            PreparedStatement p = CONN.prepareStatement(sql);
            p.setString(1, code);
            p.setString(2, name);
            p.setInt(3, Integer.parseInt(price));
            p.setString(4, expiryDate);
            p.setInt(5, Integer.parseInt(tonKho));
            p.setInt(6, Integer.parseInt(hang));
            p.setInt(7, Integer.parseInt(phanloai));
            p.setInt(8, Integer.parseInt(kho));
            
            p.executeUpdate();
            System.out.println("Insert successfully!");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Product> getAll() throws SQLException {
        String sql = "SELECT * FROM HANG_HOA";
        List<Product> products = new ArrayList<>();
        try {
            //su dung ket noi de tao cau lenh,statement: thi hanh cau lenh tai thoi diem goi
            Statement s = CONN.createStatement();

            //thi hanh cau lenh truy van, tra ket qua truy van qua doi tuong ressultset
            ResultSet r = s.executeQuery(sql);

            while (r.next()) {
                String name = r.getString("TENHANGHOA");
                String code = r.getString("MAHANGHOA");
                String expiryDate = r.getString("HANSUDUNG");
                String phanloai = r.getString("PHANLOAI");
                String hang = r.getString("HANGSANXUAT");
                String kho = r.getString("TEN_KHO");
                String price = r.getString("GIANHAP");
                String tonKho = r.getString("SOLUONGOTNKHO");
                Product product = new Product(name, code, expiryDate, phanloai, hang, kho, price, tonKho);
                products.add(product);
            }

            r.close();
            s.close();
        } catch (SQLException e) {
            System.out.println("Khong the lay du lieu ");
        }
        return products;
    }

    /**
     * update Product by code
     */
    @Override
    public void update(Product entity) {

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCode().equalsIgnoreCase(entity.getCode())) {
                list.set(i, entity);
            }
        }

    }

    /**
     * find Product by code or name
     */
    @Override
    public Product find(Serializable id) {

        for (Product p : list) {
            if (p.getName().equals(id) || p.getCode().equals(id)) {
                return p;
            }
        }

        return null;
    }

    public Product find(List<Product> products, String code) {
        for (Product p : products) {
            if (p.getName().equals(code) || p.getCode().equals(code)) {
                return p;
            }
        }
        return null;
    }

    /**
     * find Product by Warehouse
     */
    @Override
    public List<Product> findBy(Serializable by) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Product> findBy(List<Product> products, String code) {
        List<Product> searched = new ArrayList<>();
        for (Product p : products) {
            if (code.equals(p.getCode().trim()) || code.equals(p.getName())) {
                searched.add(p);
            }
        }
        return searched;
    }

    public List<Product> filterProductByWarehouse(List<Product> productsToFilter, String w) {
        List<Product> filtered = new ArrayList<>();

        for (Product p : productsToFilter) {
            if (w.equals(p.getKho())) {
                filtered.add(p);
            }
        }
        return filtered;
    }

    public static void main(String[] args) {
        Product pro1 = new Product("ten product 1", "code1", "01-01-2012", "1", "1", "1", "393", "898");
        ProductDAO productDAO = new ProductDAO();
        productDAO.insert(pro1);
//        try {
//            
//            productDAO.filterProductByWarehouse(productDAO.getAll(), "2");
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}
