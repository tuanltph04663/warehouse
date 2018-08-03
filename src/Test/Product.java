package Test;

public class Product {

    private String name;

    private String code;

    private String expiryDate;

    private String phanloai;

    private String hang;

    private String kho;

    private String price;

    private String tonKho;

    public Product() {
    }

    public Product(String name, String code, String expiryDate, String phanloai, String hang, String kho, String price, String tonKho) {
        this.name = name;
        this.code = code;
        this.expiryDate = expiryDate;
        this.phanloai = phanloai;
        this.hang = hang;
        this.kho = kho;
        this.price = price;
        this.tonKho = tonKho;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPhanloai() {
        return phanloai;
    }

    public void setPhanloai(String phanloai) {
        this.phanloai = phanloai;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public String getKho() {
        return kho;
    }

    public void setKho(String kho) {
        this.kho = kho;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTonKho() {
        return tonKho;
    }

    public void setTonKho(String tonKho) {
        this.tonKho = tonKho;
    }

    @Override
    public String toString() {
        return "Product{" + "name=" + name + ", code=" + code + ", expiryDate=" + expiryDate + ", phanloai=" + phanloai + ", hang=" + hang + ", kho=" + kho + ", price=" + price + ", tonKho=" + tonKho + '}';
    }

}
