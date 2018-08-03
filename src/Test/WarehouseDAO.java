package Test;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WarehouseDAO extends DAO<Warehouse> {

    private static final Connection CONN = jdbc.getconnection();

    @Override
    public List<Warehouse> getAll() throws SQLException {
        String sql = "SELECT * FROM KHO";
        List<Warehouse> warehouses = new ArrayList<>();
        try {
            //su dung ket noi de tao cau lenh,statement: thi hanh cau lenh tai thoi diem goi
            Statement s = CONN.createStatement();

            //thi hanh cau lenh truy van, tra ket qua truy van qua doi tuong ressultset
            ResultSet r = s.executeQuery(sql);

            while (r.next()) {
                // rs.getString(1): GET column 1 in table KHO
                // rs.getString(2): GET column 2 in table KHO

                // convert string to int
                int id = Integer.parseInt(r.getString(1));

                Warehouse w = new Warehouse(id, r.getString(2));
                warehouses.add(w);
            }

            r.close();
            s.close();
        } catch (SQLException e) {
            System.out.println("Khong the lay du lieu ");
        }
        return warehouses;
    }

    @Override
    public void update(Warehouse entity) {
        // TODO update Warehouse

    }

    @Override
    public Warehouse find(Serializable id) {
        // TODO find Warehouse
        return null;
    }

    public Warehouse find(List<Warehouse> warehouses, String name) {
        for (Warehouse w : warehouses) {
            if (name.trim().equals(w.getName().trim().trim())) {
                return w;
            }
        }
        return null;
    }

    @Override
    public List<Warehouse> findBy(Serializable by) {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(String[] args) {
        WarehouseDAO warehouseDAO = new WarehouseDAO();
        List<Warehouse> warehouses = new ArrayList<>();
        try {
            warehouses = warehouseDAO.getAll();
        } catch (SQLException ex) {
            Logger.getLogger(WarehouseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Warehouse w : warehouses) {
            System.out.println(w);
        }

    }

}
