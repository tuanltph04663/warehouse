package model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WarehouseDAO extends DAO<Warehouse> {

	private static final String SELECT_ALL = "SELECT * FROM WAREHOUSE";

	@Override
	public List<Warehouse> getAll() throws SQLException {
		List<Warehouse> warehouses = new ArrayList<>();
		try {
			Statement s = CONN.createStatement();
			ResultSet r = s.executeQuery(SELECT_ALL);

			while (r.next()) {
				Warehouse w = new Warehouse(r.getInt("ID"), r.getString("NAME"));
				warehouses.add(w);
			}

			r.close();
			s.close();
		} catch (SQLException e) {
			System.out.println("Can't get data in WAREHOUSE");
		}
		return warehouses;
	}

	public String[] getWarehouseName() {
		List<Warehouse> warehouses = new ArrayList<>();

		try {
			warehouses = this.getAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String[] data = new String[warehouses.size()];
		int i = 0;
		for (Warehouse w : warehouses) {
			data[i] = w.getName();
			i++;
		}

		return data;
	}

	public String[] getWarehouseName(List<Warehouse> warehouses) {
		String[] data = new String[warehouses.size()];
		int i = 0;
		for (Warehouse w : warehouses) {
			data[i] = w.getName();
			i++;
		}

		return data;
	}

	public String[][] getWarehouseData() {
		List<Warehouse> warehouses = new ArrayList<>();

		try {
			warehouses = this.getAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String[][] data = new String[warehouses.size()][2];
		int i = 0;
		for (Warehouse w : warehouses) {
			data[i][0] = Integer.toString(w.getId());
			data[i][1] = w.getName();
			i++;
		}

		return data;
	}

	public String[][] getWarehouseData(List<Warehouse> warehouses) {
		String[][] data = new String[warehouses.size()][2];
		int i = 0;
		for (Warehouse w : warehouses) {
			data[i][0] = Integer.toString(w.getId());
			data[i][1] = w.getName();
			i++;
		}

		return data;
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
