package entity;

public class Item {
    private String itemID;
    private String itemName;
    private double qty;
    private double price;

    public Item() {
    }

    public Item(String itemID, String itemName, double qty, double price) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.qty = qty;
        this.price = price;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemID='" + itemID + '\'' +
                ", itemName='" + itemName + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
