
public class Main {
    public static void main(String[] args) {
        Hero hero = new Hero("Хиро", 10, 8);
        System.out.println("Герой создан:");
        hero.showStats();

        Inventory inventory = new Inventory();

        for (int i = 0; i < 4; i++) {
            Potion potion = PotionFactory.createRandomPotion();
            inventory.addPotion(potion);
        }

        inventory.showInventory();

        for (Potion potion : inventory.getPotions()) {
            potion.apply(hero);
        }

        System.out.println("\nИтоговые характеристики:");
        hero.showStats();
    }
}
