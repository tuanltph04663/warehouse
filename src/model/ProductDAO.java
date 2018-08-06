package model;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO extends DAO<Product> {

	private static final String SELECT_ALL = "SELECT * FROM PRODUCT";

	private static final String INSERT_INTO = "INSERT INTO PRODUCT VALUES(?,?,?,?,?,?,?,?)";

	public boolean insert(Product entity) {
		int id = entity.getId();
		String name = entity.getName();
		int price = entity.getPrice();
		// TODO: convert java.sql.Date to java.util.Date
		// Date expiryDate = entity.getExpiryDate();
		int amount = entity.getAmount();
		int categoryId = entity.getCategoryId();
		int manufacturerId = entity.getManufacturerId();
		int warehouseId = entity.getWarehouseId();

		try {
			PreparedStatement p = CONN.prepareStatement(INSERT_INTO);
			p.setInt(1, id);
			p.setString(2, name);
			p.setInt(3, price);
			// TODO: convert java.sql.Date to java.util.Date
			// p.setDate(4, expiryDate);
			p.setInt(5, amount);
			p.setInt(6, categoryId);
			p.setInt(7, manufacturerId);
			p.setInt(8, warehouseId);

			p.executeUpdate();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	@Override
	public List<Product> getAll() throws SQLException {
		List<Product> products = new ArrayList<>();
		try {
			Statement s = CONN.createStatement();
			ResultSet r = s.executeQuery(SELECT_ALL);
			while (r.next()) {
				int id = r.getInt("ID");
				String name = r.getString("NAME");
				int price = r.getInt("PRICE");
				Date expiryDate = r.getDate("EXPIRY_DATE");
				int amount = r.getInt("AMOUNT");
				int categoryId = r.getInt("CATEGORY_ID");
				int manufacturerId = r.getInt("MANUFACTURER_ID");
				int warehouseId = r.getInt("WAREHOUSE_ID");
				Product product = new Product(id, name, price, expiryDate, amount, categoryId, manufacturerId,
						warehouseId);
				products.add(product);
			}
			r.close();
			s.close();
		} catch (SQLException e) {
			System.out.println("Can't get data in PRODUCT.");
		}
		return products;
	}

	public Product find(List<Product> products, int id) {
		for (Product p : products) {
			if (id == p.getId()) {
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

	@Override
	public void update(Product entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Product find(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

}
