package model.entity;

import java.util.Date;

public class Product {

	private int id;
	private String name;
	private int price;
	private Date expiryDate;
	private int amount;
	private int categoryId;
	private int manufacturerId;
	private int warehouseId;

	public Product() {
		super();
	}

	public Product(int id, String name, int price, Date expiryDate, int amount, int categoryId, int manufacturerId,
			int warehouseId) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.expiryDate = expiryDate;
		this.amount = amount;
		this.categoryId = categoryId;
		this.manufacturerId = manufacturerId;
		this.warehouseId = warehouseId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(int manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", expiryDate=" + expiryDate + ", amount="
				+ amount + ", categoryId=" + categoryId + ", manufacturerId=" + manufacturerId + ", warehouseId="
				+ warehouseId + "]";
	}

}
