package model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO extends DAO<Category> {

	private static final String SELECT_ALL = "SELECT * FROM CATEGORY";

	@Override
	public List<Category> getAll() throws SQLException {
		// List<Category> categories = new ArrayList<>();
		// try {
		// Statement s = CONN.createStatement();
		// ResultSet r = s.executeQuery(SELECT_ALL);
		//
		// while (r.next()) {
		// Category c = new Category(r.getInt("ID"), r.getString("NAME"));
		// categories.add(c);
		// }
		//
		// r.close();
		// s.close();
		// } catch (SQLException e) {
		// System.out.println("Can't get data in CATEGORY.");
		// }
		// return categories;

		return this.data();
	}

	public String[] getCategoryName() {
		List<Category> categories = new ArrayList<>();
		try {
			categories = this.getAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String[] data = new String[categories.size()];

		int i = 0;
		for (Category c : categories) {
			data[i] = c.getName();
			i++;
		}
		return data;
	}

	public String[] getCategoryName(List<Category> categories) {

		String[] data = new String[categories.size()];

		int i = 0;
		for (Category c : categories) {
			data[i] = c.getName();
			i++;
		}
		return data;
	}

	public Category find(List<Category> categories, String name) {
		for (Category c : categories) {
			if (name.trim().equals(c.getName().trim())) {
				return c;
			}
		}
		return null;
	}

	@Override
	public List<Category> findBy(Serializable by) {
		// TODO findBy Category
		return null;
	}

	private List<Category> data() {
		List<Category> categories = new ArrayList<>();
		categories.add(new Category(1, "c1"));
		categories.add(new Category(2, "c2"));
		categories.add(new Category(3, "c3"));
		categories.add(new Category(4, "c4"));
		categories.add(new Category(5, "c5"));
		categories.add(new Category(6, "c6"));

		return categories;
	}

	@Override
	public Category findByName(List<Category> entities, String name) {
		for (Category c : entities) {
			if (name.trim().equals(c.getName().trim())) {
				return c;
			}
		}
		return null;
	}

	@Override
	public Category findById(List<Category> entities, int id) {
		for (Category c : entities) {
			if (id == c.getId()) {
				return c;
			}
		}
		return null;
	}

}
