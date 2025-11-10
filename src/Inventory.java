import java.util.ArrayList;

public class Inventory {
    private ArrayList<Potion> potions = new ArrayList<>();

    public void addPotion(Potion potion) {
        potions.add(potion);
    }

    public void showInventory() {
        System.out.println("\nИнвентарь:");
        for (Potion potion : potions) {
            System.out.println("- " + potion.getName());
        }
    }

    public ArrayList<Potion> getPotions() {
        return potions;
    }
}
