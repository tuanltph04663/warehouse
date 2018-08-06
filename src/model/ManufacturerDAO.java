package model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ManufacturerDAO extends DAO<Manufacturer> {

	private static final String SELECT_ALL = "SELECT * FROM MANUFACTURER";

	@Override
	public List<Manufacturer> getAll() throws SQLException {
//		List<Manufacturer> manufacturers = new ArrayList<>();
//		try {
//			Statement s = CONN.createStatement();
//			ResultSet r = s.executeQuery(SELECT_ALL);
//
//			while (r.next()) {
//				Manufacturer m = new Manufacturer(r.getInt("ID"), r.getString("NAME"));
//				manufacturers.add(m);
//			}
//
//			r.close();
//			s.close();
//		} catch (SQLException e) {
//			System.out.println("Can't get data in MANUFACTURER.");
//		}
//		return manufacturers;
		
		return this.data();
	}

	public String[] getManufacturerName() {
		List<Manufacturer> manufacturers = new ArrayList<>();

		try {
			manufacturers = this.getAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String[] data = new String[manufacturers.size()];
		int i = 0;
		for (Manufacturer m : manufacturers) {
			data[i] = m.getName();
			i++;
		}
		return data;
	}

	public String[] getManufacturerName(List<Manufacturer> manufacturers) {
		String[] data = new String[manufacturers.size()];
		int i = 0;
		for (Manufacturer m : manufacturers) {
			data[i] = m.getName();
			i++;
		}
		return data;
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
		throw new UnsupportedOperationException("Not supported yet.");
	}

	
	private List<Manufacturer> data() {
		List<Manufacturer> manufacturers = new ArrayList<>();
		manufacturers.add(new Manufacturer(1, "m1"));
		manufacturers.add(new Manufacturer(2, "m2"));
		manufacturers.add(new Manufacturer(3, "m3"));
		manufacturers.add(new Manufacturer(4, "m4"));
		manufacturers.add(new Manufacturer(5, "m5"));
		
		return manufacturers;
	}

	@Override
	public Manufacturer findByName(List<Manufacturer> entities, String name) {
		for (Manufacturer m : entities) {
			if (name.trim().equals(m.getName().trim())) {
				return m;
			}
		}
		return null;
	}

	@Override
	public Manufacturer findById(List<Manufacturer> entities, int id) {
		for (Manufacturer m : entities) {
			if (id == m.getId()) {
				return m;
			}
		}
		return null;
	}

}
