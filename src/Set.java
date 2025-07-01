import java.util.ArrayList;

public class Set {
    private ArrayList<Clothing> pieces;
    private ArrayList<String> usages;

    public ArrayList<Clothing> getPieces() {
        return pieces;
    }

    public void setPieces(Clothing clothing) {
        pieces.add(clothing);
    }

    public ArrayList<String> getUsages() {
        return usages;
    }

    public void setUsages(String date){
        usages.add(date);
    }
}
