public class Clothing extends AbstractItem {
    public Clothing(String type, String color, String size, String origin_shop, String purchase_date, String conservation, boolean lending_warning, boolean available){
        this.type = type;
        this.color = color;
        this.size = size;
        this.origin_shop = origin_shop;
        this.purchase_date = purchase_date;
        this.conservation = conservation;
        this.lending_warning = lending_warning;
        this.available = available;
    }
}
