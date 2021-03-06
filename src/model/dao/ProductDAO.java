package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.entity.Product;
import util.Convert;

public class ProductDAO extends DAO<Product> {

	private static final String SELECT_ALL = "SELECT * FROM PRODUCT";

	private static final String INSERT_INTO = "INSERT INTO PRODUCT VALUES(?,?,?,?,?,?,?,?)";

	private static final String UPDATE = "UPDATE PRODUCT SET NAME=?, PRICE=?, EXPIRY_DATE=?, AMOUNT=?, CATEGORY_ID=?, MANUFACTURER_ID=?, WAREHOUSE_ID=? WHERE ID=?";

	private static final String DELETE = "DELETE PRODUCT WHERE ID=?";
	
	public boolean insert(Product entity) {

		int id = entity.getId();
		String name = entity.getName();
		int price = entity.getPrice();
		// TODO: convert java.sql.Date to java.util.Date
		Date expiryDate = entity.getExpiryDate();
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

			p.setDate(4, Convert.utilDateToSqlDate(expiryDate));
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

	public boolean update(Product productToUpdate) {
		int id = productToUpdate.getId();
		String name = productToUpdate.getName();
		int price = productToUpdate.getPrice();
		// TODO: convert java.sql.Date to java.util.Date
		Date expiryDate = productToUpdate.getExpiryDate();
		int amount = productToUpdate.getAmount();
		int categoryId = productToUpdate.getCategoryId();
		int manufacturerId = productToUpdate.getManufacturerId();
		int warehouseId = productToUpdate.getWarehouseId();

		try {
			PreparedStatement p = CONN.prepareStatement(UPDATE);
			p.setString(1, name);
			p.setInt(2, price);
			// TODO: convert java.sql.Date to java.util.Date
			p.setDate(3, Convert.utilDateToSqlDate(expiryDate));
			p.setInt(4, amount);
			p.setInt(5, categoryId);
			p.setInt(6, manufacturerId);
			p.setInt(7, warehouseId);
			p.setInt(8, id);

			p.executeUpdate();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}

	}

	public boolean delete(Product productToDelete) {
		int id = productToDelete.getId();
		try {
			PreparedStatement p = CONN.prepareStatement(DELETE);
			p.setInt(1, id);
			
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

//		return this.data();
	}

	public List<Product> find(List<Product> products, int id) {
		List<Product> searched = new ArrayList<>();
		for (Product p : products) {
			if (id == p.getId()) {
				searched.add(p);
			}
		}
		return searched;
	}

	public List<Product> findBy(List<Product> products, String search) {
		List<Product> searched = new ArrayList<>();

		for (Product p : products) {
			if (search.trim().equals(p.getName().trim())) {
				searched.add(p);
			}
		}
		return searched;
	}

	public List<Product> filterProductByWarehouse(List<Product> productsToFilter, int w) {
		List<Product> filtered = new ArrayList<>();

		for (Product p : productsToFilter) {
			if (w == p.getWarehouseId()) {
				filtered.add(p);
			}
		}

		return filtered;
	}

	private List<Product> data() {
		List<Product> products = new ArrayList<>();
		products.add(new Product(1, "p1", 200, new Date(), 50, 1, 1, 5));
		products.add(new Product(2, "p2", 200, new Date(), 50, 1, 1, 4));
		products.add(new Product(3, "p3", 200, new Date(), 50, 1, 1, 4));
		products.add(new Product(4, "p4", 200, new Date(), 50, 2, 1, 3));
		products.add(new Product(5, "p5", 200, new Date(), 50, 2, 1, 3));
		products.add(new Product(6, "p6", 200, new Date(), 50, 3, 1, 3));
		products.add(new Product(7, "p7", 200, new Date(), 50, 3, 1, 2));
		products.add(new Product(8, "p8", 200, new Date(), 50, 3, 1, 2));
		products.add(new Product(9, "p9", 200, new Date(), 50, 4, 1, 1));
		products.add(new Product(10, "p10", 200, new Date(), 50, 4, 1, 1));
		products.add(new Product(11, "p11", 200, new Date(), 50, 5, 1, 1));

		return products;
	}

	@Override
	public Product findByName(List<Product> entities, String name) {
		for (Product p : entities) {
			if (name.trim().equals(p.getName().trim())) {
				return p;
			}
		}
		return null;
	}

	@Override
	public Product findById(List<Product> entities, int id) {
		for (Product p : entities) {
			if (id == p.getId()) {
				return p;
			}
		}
		return null;
	}

	

}
