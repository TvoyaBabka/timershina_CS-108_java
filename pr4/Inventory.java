package pr4;

import java.util.ArrayList;
import java.util.Comparator;

public class Inventory {
    private ArrayList<Item> items = new ArrayList<>();
    private int maxWeight = 100; // максимум по весу
    private int currentWeight = 0;

    public void addItem(Item item) {
        if (currentWeight + item.getWeight() > maxWeight) {
            System.out.println("Рюкзак переполнен! Текущий вес: " + currentWeight + "/" + maxWeight);
            return;
        }
        items.add(item);
        currentWeight += item.getWeight();
        System.out.println("Предмет добавлен!");
    }

    public void showItems() {
        if (items.isEmpty()) {
            System.out.println("Инвентарь пуст.");
        } else {
            System.out.println("Ваш инвентарь:");
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i));
            }
            System.out.println("Общая ценность: " + getTotalValue() + ", общий вес: " + currentWeight);
        }
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            currentWeight -= items.get(index).getWeight();
            items.remove(index);
            System.out.println("Предмет удалён!");
        } else {
            System.out.println("Неверный номер предмета.");
        }
    }

    public void sortByValue() {
        items.sort(Comparator.comparingInt(Item::getValue).reversed());
    }

    private int getTotalValue() {
        return items.stream().mapToInt(Item::getValue).sum();
    }
}
