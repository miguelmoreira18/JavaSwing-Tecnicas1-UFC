import java.util.ArrayList;

public class Main implements ItemCreationListener {
    private ArrayList<Item> itemList = new ArrayList<>();

    public static void main(String[] args) {
        // criando uma instancia do Main pra passar pro setter do listener
        Main appController = new Main();
        appController.start();
    }

    public void start() {
        MyWindow window = new MyWindow("Gestor de vestuário");
        window.setItemCreationListener(this);
    }

    // 3. Implement the contract method. This is where Items are created!
    public Item onItemCreate(String name, String color, String size, String origin, String purchase, String conservation, String imagePath, boolean lending, boolean available) {
        Item newItem = new Item(name, color, size, origin, purchase, conservation, imagePath, lending, available);
        itemList.add(newItem);

        System.out.println("New item created and added to the list in Main!");
        System.out.println("Current item list size: " + itemList.size());
        System.out.println(newItem.getImage_path());

        return newItem;
    }
}