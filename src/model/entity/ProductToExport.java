package model.entity;

import java.util.Date;

public class ProductToExport {

	private int id;
	private String name;
	private int price;
	private Date expiryDate;
	private int amount;
	private String category;
	private String manufacturer;
	private String warehouse;

	public ProductToExport() {
		super();
	}
	
	public ProductToExport productToExport(Product product) {
		
		
		return null;
	}

	public ProductToExport(int id, String name, int price, Date expiryDate, int amount, String category,
			String manufacturer, String warehouse) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.expiryDate = expiryDate;
		this.amount = amount;
		this.category = category;
		this.manufacturer = manufacturer;
		this.warehouse = warehouse;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	@Override
	public String toString() {
		return "ProductToExport [id=" + id + ", name=" + name + ", price=" + price + ", expiryDate=" + expiryDate
				+ ", amount=" + amount + ", category=" + category + ", manufacturer=" + manufacturer + ", warehouse="
				+ warehouse + "]";
	}

}
