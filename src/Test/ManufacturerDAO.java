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
public class ManufacturerDAO extends DAO<Manufacturer> {

    private static final Connection CONN = jdbc.getconnection();

    @Override
    public List<Manufacturer> getAll() throws SQLException {
        String sql = "SELECT * FROM HANG_SAN_XUAT";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try {
            //su dung ket noi de tao cau lenh,statement: thi hanh cau lenh tai thoi diem goi
            Statement s = CONN.createStatement();

            //thi hanh cau lenh truy van, tra ket qua truy van qua doi tuong ressultset
            ResultSet r = s.executeQuery(sql);

            while (r.next()) {
                // rs.getString(2): GET column 2 in table HANG_SAN_XUAT
                Manufacturer m = new Manufacturer(r.getInt(1), r.getString(2));
                System.out.println(m.getId());
                manufacturers.add(m);
            }

            r.close();
            s.close();
        } catch (SQLException e) {
            System.out.println("Khong the lay du lieu ");
        }
        return manufacturers;
    }

    @Override
    public void update(Manufacturer entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Manufacturer find(Serializable id) {
        
        return null;
    }
    
    public Manufacturer find(List<Manufacturer> manufacturers, String id) {
        for (Manufacturer m : manufacturers) {
            if (id.trim().equals(m.getName().trim())) {
                return m;
            }
        }
        return null;
    }

    @Override
    public List<Manufacturer> findBy(Serializable by) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
