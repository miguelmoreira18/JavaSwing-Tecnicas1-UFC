public abstract class AbstractItem {
    protected String type;
    protected String color;
    protected String size;
    protected String origin_shop;
    protected String purchase_date;
    protected String conservation; // excelente, boa etc
    protected boolean lending_warning;
    protected boolean available;

    //region Getters and Setters
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getOrigin_shop() {
        return origin_shop;
    }

    public void setOrigin_shop(String origin_shop) {
        this.origin_shop = origin_shop;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getConservation() {
        return conservation;
    }

    public void setConservation(String conservation) {
        this.conservation = conservation;
    }

    public boolean isLending_warning() {
        return lending_warning;
    }

    public void setLending_warning(boolean lending_warning) {
        this.lending_warning = lending_warning;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    //endregion
}
