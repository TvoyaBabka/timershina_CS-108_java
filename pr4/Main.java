package pr4;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Inventory inventory = new Inventory();

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1 - Добавить предмет");
            System.out.println("2 - Показать инвентарь");
            System.out.println("3 - Удалить предмет");
            System.out.println("4 - Сортировать по ценности");
            System.out.println("0 - Выйти");
            System.out.print("Ваш выбор: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Введите название предмета: ");
                    String name = sc.nextLine();

                    System.out.print("Введите тип предмета (оружие, зелье, броня, артефакт, инструмент): ");
                    String type = sc.nextLine();

                    System.out.print("Введите ценность предмета: ");
                    int value = sc.nextInt();

                    System.out.print("Введите вес предмета: ");
                    int weight = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Введите редкость предмета (Обычный, Редкий, Легендарный): ");
                    String rarity = sc.nextLine();

                    Item item = new Item(name, type, value, weight, rarity);
                    inventory.addItem(item);
                }

                case 2 -> inventory.showItems();

                case 3 -> {
                    inventory.showItems();
                    System.out.print("Введите номер предмета для удаления: ");
                    int index = sc.nextInt() - 1;
                    inventory.removeItem(index);
                }

                case 4 -> {
                    inventory.sortByValue();
                    System.out.println("Инвентарь отсортирован по ценности.");
                }

                case 0 -> {
                    System.out.println("Вы вышли из программы.");
                    return;
                }

                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }
}
